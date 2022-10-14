package io.github.dayal96.api;

import io.github.dayal96.service.BnlUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {
  @GetMapping
  public String testJsonification() {
    return BnlUtil.testBnlObject();
  }
}
