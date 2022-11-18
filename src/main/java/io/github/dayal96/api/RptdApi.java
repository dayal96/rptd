package io.github.dayal96.api;

import io.github.dayal96.model.RouteEntry;
import io.github.dayal96.service.RouteService;
import io.github.dayal96.service.RouteService.RouteTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rptd",
    consumes = MediaType.TEXT_PLAIN_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class RptdApi {

  @Autowired
  private RouteService routeService;

  @GetMapping("/route/{id}")
  public Optional<RouteEntry> getRouteById(@PathVariable("id") final String id) {
    return routeService.getRoute(id);
  }

  @GetMapping("/routes")
  public List<RouteEntry> listRoutes() {
    return routeService.listRoutes();
  }

  @GetMapping(value = "/export", produces = MediaType.TEXT_PLAIN_VALUE)
  public String exportRoutes() {
    return routeService.exportRoutes();
  }

  @PostMapping("/match")
  public RouteEntry findMatchingRoute(@RequestBody final String route) {
    return routeService.getMatchingRoute(RouteTemplate.parseRouteTemplate(route)).orElse(null);
  }

  @PostMapping("/route")
  public RouteEntry addRouteEntry(@RequestBody final String template) {
    return routeService.addRoute(template, null);
  }

  @PutMapping("/route/{id}")
  public RouteEntry updateRouteEntry(@RequestBody final String template) {
    return routeService.addRoute(template, null);
  }

  @DeleteMapping("/route/{id}")
  public void removeRouteEntry(@PathVariable("id") final String templateId) {
    routeService.deleteRoute(templateId);
  }
}
