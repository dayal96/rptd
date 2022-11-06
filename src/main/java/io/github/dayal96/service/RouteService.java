package io.github.dayal96.service;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.model.RouteEntry;
import io.github.dayal96.model.RouteRepository;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

  public List<RouteEntry> listRoutes() {
    return routeRepository.findAll();
  }

  public RouteEntry addRoute(String rawTemplate, String id) {
    RouteEntry route = getRouteEntry(id);
    List<String> parts = List.of(rawTemplate.trim().split("\n"));
    RouteTemplate template = RouteTemplate.parseRouteTemplate(parts.get(0));
    route.setType(template.type);
    route.setTemplate(template.uri);
    route.setScript(String.join("\n", parts.subList(1, parts.size())));
    return routeRepository.save(route);
  }

  private RouteEntry getRouteEntry(String id) {
    if (Objects.nonNull(id) && id.trim().length() > 0) {
       RouteEntry toReturn = routeRepository.findById(id).orElse(new RouteEntry());
       toReturn.setId(id);
       return toReturn;
    }

    return new RouteEntry();
  }

  public void deleteRoute(String routeId) {
    routeRepository.deleteById(routeId);
  }

  public String routeRequests(String uri, RequestType type, Optional<String> requestBody) {
    Optional<RouteEntry> route = getMatchingRoute(new RouteTemplate(type, uri));

    if (route.isPresent()) {
      Map<String, String> urlParameters = route.get().extractParameters(uri);
      return BnlUtil.processBnl(urlParameters, route.get().getScript(), uri, requestBody);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }
  }

  public Optional<RouteEntry> getMatchingRoute(RouteTemplate template) {
    List<RouteEntry> eligibleRoutes = routeRepository.findByType(template.type);
    return bestMatchingRoute(eligibleRoutes, template.uri);
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

  public static class RouteTemplate {
    public final String uri;
    public final RequestType type;

    public RouteTemplate(RequestType type, String uri) {
      this.type = type;
      this.uri = uri;
    }

    public static RouteTemplate parseRouteTemplate(String route) {
      String[] routePart = route.trim().split(" ");
      RequestType type = RequestType.valueOf(routePart[0].toUpperCase(Locale.ROOT));
      String uri = routePart[1];
      return new RouteTemplate(type, uri);
    }
  }
}
