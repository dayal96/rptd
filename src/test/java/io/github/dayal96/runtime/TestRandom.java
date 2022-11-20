package io.github.dayal96.runtime;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import io.github.dayal96.builders.RouteEntryBuilder;
import io.github.dayal96.environment.SymbolTable;
import io.github.dayal96.model.RequestType;
import io.github.dayal96.model.RouteEntry;
import io.github.dayal96.primitive.number.Rational;
import io.github.dayal96.runtime.lib.RandomFunction;
import io.github.dayal96.util.CryptoUtil;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.util.RouteMatcher.Route;

public class TestRandom {
  @Test
  public void testPseudoRandom() throws Exception {
    byte[] uri = "/test/endpoint".getBytes(StandardCharsets.UTF_8);
    RandomFunction rand1 = new RandomFunction(CryptoUtil.sha256(uri));
    RandomFunction rand2 = new RandomFunction(CryptoUtil.sha256(uri));

    for (int i = 100; i < 200; i++) {
      assertEquals("PseudoRandom(" + i + ") mismatch for seed " + Arrays.toString(uri),
          rand1.evaluate(List.of(new Rational(i)), new SymbolTable()),
          rand2.evaluate(List.of(new Rational(i)), new SymbolTable()));
    }
  }
}
