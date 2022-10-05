package io.github.dayal96.service;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.model.RouteEntry;
import io.github.dayal96.model.RouteRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RouteService {

  @Autowired
  private RouteRepository routeRepository;

  public Optional<RouteEntry> getRoute(String id) {
    return routeRepository.findById(id);
  }

  public RouteEntry addNewTestRoute(String template, RequestType type) {
    RouteEntry newRoute = new RouteEntry();
    newRoute.setTemplate(template);
    newRoute.setType(type);
    return routeRepository.save(newRoute);
  }

  public void deleteRoute(String routeId) {
    routeRepository.deleteById(routeId);
  }

  public Map<String, String> routeRequests(String uri, RequestType type) {
    Optional<RouteEntry> route = getMatchingRoute(uri, type);

    if (route.isPresent()) {
      return route.get().extractParameters(uri);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }
  }

  private Optional<RouteEntry> getMatchingRoute(String url, RequestType type) {
    List<RouteEntry> eligibleRoutes = routeRepository.findByType(type);
    return bestMatchingRoute(eligibleRoutes, url);
  }

  private static Optional<RouteEntry> bestMatchingRoute(List<RouteEntry> eligibleRoutes,
      String toMatch) {
    eligibleRoutes.sort((route1, route2) -> matchRoute(route2, toMatch)
        - matchRoute(route1, toMatch));
    Optional<RouteEntry> bestOption = eligibleRoutes.stream().findFirst();

    if (bestOption.isPresent() && matchRoute(bestOption.get(), toMatch) < 0) {
      bestOption = Optional.empty();
    }
    return bestOption;
  }

  private static int matchRoute(RouteEntry routeEntry, String toMatch) {
    int match = -1;

    List<String> template = List.of(routeEntry.getTemplate().split("/"));
    List<String> route = List.of(toMatch.split("/"));

    if (template.size() == route.size()) {
      match = 0;
      int fuzzyMatch = 0;

      for (int i = 0; i < template.size(); i++) {
        if (template.get(i).contains("{") && template.get(i).contains("}")) {
          match += 1;
          fuzzyMatch += 1;
        } else if (template.get(i).equals(route.get(i))) {
          match += 1;
        } else {
          match = -1;
          break;
        }
      }

      match -= fuzzyMatch;
    }

    return match;
  }
}
