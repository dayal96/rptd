package io.github.dayal96.runtime.lib;

import java.util.List;

public class RptdStdLib {

  private static List<String> funcs = List.of(
        """
        (define lookup
          (lambda (obj field)
            (if (= false obj)
                false
                (if (= (first (first obj)) field)
                    (rest (first obj))
                    (lookup (rest obj) field)))))
        """
  );

  public static String getStdLib() {
    return String.join("\n", funcs);
  }
}
