package com.vmichael.springbootapp.utils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Набор статических методов для работы с объектами.
 */
public abstract class ObjectsUtils {
  /**
   * Находит {@code null} среди аргуметов.
   * @param args произвольные объекты, в том числе {@code null}
   * @throws NullPointerException если нашелся хотя бы 1 {@code null}
   */
  public static void throwNPEIfNull(final Object ...args) {
    if (Arrays.stream(args).anyMatch(Objects::isNull)) {
      throw new NullPointerException();
    }
  }
}
