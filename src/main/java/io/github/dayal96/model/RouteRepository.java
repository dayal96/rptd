package io.github.dayal96.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<RouteEntry, String> {
  List<RouteEntry> findByType(RequestType type);

  Optional<RouteEntry> findByTemplateAndType(String template, RequestType type);
}
