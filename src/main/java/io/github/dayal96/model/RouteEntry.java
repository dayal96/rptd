package io.github.dayal96.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "route")
public class RouteEntry {

  @Id private String id;
  @Column private String template;
  @Column private String script;
  @Column private RequestType type;

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

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public RequestType getType() {
    return type;
  }

  public void setType(RequestType type) {
    this.type = type;
  }

  @JsonIgnore
  @PrePersist
  public void prepersist() {
    if (id == null || id.length() <= 0) {
      id = UUID.randomUUID().toString();
    }
  }

  @JsonIgnore
  public Map<String, String> extractParameters(String url) {
    Map<String, String> params = new HashMap<>();

    List<String> templateParts = List.of(template.split("/"));
    List<String> urlParts = List.of(url.split("/"));

    if (templateParts.size() == urlParts.size()) {
      for (int i = 0; i < templateParts.size(); i++) {
        String part = templateParts.get(i);
        String urlParam = urlParts.get(i);

        if (part.length() <= 3) {
          continue;
        }

        if (part.charAt(0) == '{' && part.charAt(part.length() - 1) == '}') {
          params.put(part.substring(1, part.length() - 1), urlParam);
        }
      }
    }

    return params;
  }
}
