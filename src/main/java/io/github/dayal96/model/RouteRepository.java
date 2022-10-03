package io.github.dayal96.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<RouteEntry, String> {
  List<RouteEntry> findByType(RequestType type);
}
