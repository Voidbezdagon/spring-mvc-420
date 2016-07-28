package com.cm.util;

import java.lang.reflect.ParameterizedType;

public class ReflectionHelper {

	@SuppressWarnings("unchecked")
	public static <T> T CreateInstance(Class<?> genericClass, int genericParamIndex) throws InstantiationException, IllegalAccessException {
		ParameterizedType p = (ParameterizedType) genericClass.getGenericSuperclass();
		return (T) ((Class<?>) p.getActualTypeArguments()[0]).newInstance();
	}
}
