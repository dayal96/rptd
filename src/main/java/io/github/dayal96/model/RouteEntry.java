package io.github.dayal96.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "route")
public class RouteEntry {

  @Id private String id;
  private String template;
  private RequestType type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public RequestType getType() {
    return type;
  }

  public void setType(RequestType type) {
    this.type = type;
  }

  @PrePersist
  public void prepersist() {
    if (id == null || id.length() <= 0) {
      id = UUID.randomUUID().toString();
    }
  }
}
