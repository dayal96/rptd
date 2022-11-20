package io.github.dayal96.builders;

import io.github.dayal96.model.RequestType;
import io.github.dayal96.model.RouteEntry;

public class RouteEntryBuilder {

  private RouteEntry routeEntry;

  public RouteEntryBuilder() {
    routeEntry = new RouteEntry();
  }

  public RouteEntryBuilder(RouteEntry entry) {
    this.routeEntry = copy(entry);
  }

  public RouteEntryBuilder(RouteEntryBuilder builder) {
    this.routeEntry = copy(builder.routeEntry);
  }

  public RouteEntryBuilder from(RouteEntry route) {
    this.routeEntry = copy(route);
    return this;
  }

  public RouteEntryBuilder from(RouteEntryBuilder builder) {
    this.routeEntry = copy(builder.routeEntry);
    return this;
  }

  public RouteEntryBuilder withTemplate(String template) {
    routeEntry.setTemplate(template);
    return this;
  }

  public RouteEntryBuilder withType(RequestType type) {
    routeEntry.setType(type);
    return this;
  }

  public RouteEntryBuilder withScript(String script) {
    routeEntry.setScript(script);
    return this;
  }

  public RouteEntry build() {
    return copy(this.routeEntry);
  }

  private static RouteEntry copy(RouteEntry entry) {
    RouteEntry newRoute = new RouteEntry();
    newRoute.setId(entry.getId());
    newRoute.setTemplate(entry.getTemplate());
    newRoute.setType(entry.getType());
    newRoute.setScript(entry.getScript());
    return newRoute;
  }
}
