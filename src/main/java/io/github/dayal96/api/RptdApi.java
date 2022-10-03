package io.github.dayal96.api;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.model.RouteEntry;
import io.github.dayal96.service.RouteService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rptd")
public class RptdApi {

  @Autowired
  private RouteService routeService;

  @GetMapping("/route/{id}")
  public Optional<RouteEntry> getRouteById(@PathVariable("id") final String id) {
    return routeService.getRoute(id);
  }

  @PostMapping("/route")
  public RouteEntry addRouteEntry(@RequestBody final String template) {
    return routeService.addNewTestRoute(template, RequestType.GET);
  }

  @DeleteMapping("/route")
  public void removeRouteEntry(@RequestBody final String templateId) {
    routeService.deleteRoute(templateId);
  }
}
