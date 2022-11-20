package io.github.dayal96.api;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.service.RouteService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/",
    consumes = MediaType.ALL_VALUE,
    produces = "application/json")
public class PrototypeApi {

  @Autowired
  private RouteService routeService;

  @GetMapping("/**")
  public String routeGetRequests(HttpServletRequest request) {
    return routeService.routeRequests(request.getRequestURI(), RequestType.GET, Optional.empty());
  }

  @PostMapping("/**")
  public String routePostRequests(HttpServletRequest request, @RequestBody(required = false) final String rawbody) {
    String body = Optional.ofNullable(rawbody).orElse("").strip();
    Optional<String> requestBody = body.length() > 0 ? Optional.of(body) : Optional.empty();
    return routeService.routeRequests(request.getRequestURI(), RequestType.POST, requestBody);
  }

  @PutMapping("/**")
  public String routePutRequests(HttpServletRequest request, @RequestBody(required = false) final String rawbody) {
    String body = Optional.ofNullable(rawbody).orElse("").strip();
    Optional<String> requestBody = body.length() > 0 ? Optional.of(body) : Optional.empty();
    return routeService.routeRequests(request.getRequestURI(), RequestType.PUT, requestBody);
  }
}
