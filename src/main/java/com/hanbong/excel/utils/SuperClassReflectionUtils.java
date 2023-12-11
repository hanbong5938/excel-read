package com.hanbong.excel.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.IntStream;

public final class SuperClassReflectionUtils {

  private SuperClassReflectionUtils() {}

  public static List<Field> getAllFields(Class<?> clazz) {
    final List<Field> fields = new ArrayList<>();
    for (Class<?> clazzInClasses : getAllClassesIncludingSuperClasses(clazz, true)) {
      fields.addAll(Arrays.asList(clazzInClasses.getDeclaredFields()));
    }
    return fields;
  }

  public static Annotation getAnnotation(
      final Class<?> clazz, final Class<? extends Annotation> targetAnnotation) {
    for (Class<?> clazzInClasses : getAllClassesIncludingSuperClasses(clazz, false)) {
      if (clazzInClasses.isAnnotationPresent(targetAnnotation)) {
        return clazzInClasses.getAnnotation(targetAnnotation);
      }
    }
    return null;
  }

  public static Field getField(final Class<?> clazz, final String name)
      throws NoSuchFieldException, SecurityException {
    for (Class<?> clazzInClasses : getAllClassesIncludingSuperClasses(clazz, false)) {
      for (Field field : clazzInClasses.getDeclaredFields()) {
        if (field.getName().equals(name)) {
          return clazzInClasses.getDeclaredField(name);
        }
      }
    }
    throw new NoSuchFieldException();
  }

  private static List<Class<?>> getAllClassesIncludingSuperClasses(
      final Class<?> clazz, final boolean fromSuper) {
    final List<Class<?>> classes = new ArrayList<>();
    Class<?> superClass = clazz;
    while (superClass != null) {
      classes.add(superClass);
      superClass = superClass.getSuperclass();
    }
    if (fromSuper) {
      Collections.reverse(classes);
    }
    return classes;
  }

  public static <T> Constructor<T> getConstructor(final Class<T> clazz) {
    final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    Constructor<T> selectedConstructor = null;
    for (Constructor<?> constructor : constructors) {
      Class<?>[] parameterTypes = constructor.getParameterTypes();
      final List<Field> fields = getAllFields(clazz);
      if (checkConstructor(parameterTypes, fields)) {
        selectedConstructor = (Constructor<T>) constructor;
      }
    }
    if (selectedConstructor == null) {
      throw new IllegalArgumentException("Constructor is not accessible from the specified class.");
    }
    return selectedConstructor;
  }

  private static boolean checkConstructor(
      final Class<?>[] parameterTypes, final List<Field> fields) {
    if (parameterTypes.length != fields.size()) {
      return false;
    }
    return IntStream.range(0, parameterTypes.length)
        .allMatch(
            i -> {
              final Class<?> fieldType = fields.get(i).getType();
              return Objects.equals(parameterTypes[i], fieldType)
                  || Objects.equals(Double.class, fieldType);
            });
  }
}
