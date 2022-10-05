package io.github.dayal96.api;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.service.RouteService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PrototypeApi {

  @Autowired
  private RouteService routeService;

  @GetMapping("/**")
  public Map<String, String> routeGetRequests(HttpServletRequest request) {
    return routeService.routeRequests(request.getRequestURI(), RequestType.GET);
  }

  @PostMapping("/**")
  public Map<String, String> routePostRequests(HttpServletRequest request) {
    return routeService.routeRequests(request.getRequestURI(), RequestType.POST);
  }

  @PutMapping("/**")
  public Map<String, String> routePutRequests(HttpServletRequest request) {
    return routeService.routeRequests(request.getRequestURI(), RequestType.PUT);
  }
}
