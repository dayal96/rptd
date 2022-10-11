package io.github.dayal96.api;

import io.github.dayal96.service.BnlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

  @Autowired
  private BnlService bnlService;

  @GetMapping
  public String testJsonification() {
    return bnlService.testBnlObject();
  }
}
