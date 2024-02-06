package com.questionpro.assesment.util;

import java.util.Collection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Util {

  public static boolean isBlank(String data) {
    return data == null || data.isEmpty();
  }

  public static boolean isNotBlank(String data) {
    return !isBlank(data);
  }

  public static <T> boolean isBlank(Collection<T> data) {
    return data == null || data.isEmpty();
  }

  public static <T> boolean isNotBlank(Collection<T> data) {
    return !isBlank(data);
  }

}
