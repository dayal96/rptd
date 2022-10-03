package io.github.dayal96.api;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.model.RouteEntry;
import io.github.dayal96.service.RouteService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
public class PrototypeApi {

  @Autowired
  private RouteService routeService;

  @GetMapping("/**")
  public String routeGetRequests(HttpServletRequest request) {
    Optional<RouteEntry> route = routeService.getMatchingRoute(request.getRequestURI(),
        RequestType.GET);

    if (route.isPresent()) {
      return route.get().getTemplate();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }
  }

  @PostMapping("/**")
  public String routePostRequests(HttpServletRequest request) {
    Optional<RouteEntry> route = routeService.getMatchingRoute(request.getRequestURI(),
        RequestType.GET);

    if (route.isPresent()) {
      return route.get().getTemplate();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }
  }

  @PutMapping("/**")
  public String routePutRequests(HttpServletRequest request) {
    Optional<RouteEntry> route = routeService.getMatchingRoute(request.getRequestURI(),
        RequestType.GET);

    if (route.isPresent()) {
      return route.get().getTemplate();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }
  }
}
