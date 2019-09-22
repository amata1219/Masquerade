package amata1219.masquerade.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

public class Reflection {

	public final static String VERSION = Bukkit.getServer().getClass().getPackage().getName().replaceFirst(".*(\\d+_\\d+_R\\d+).*", "$1");
	public final static String NMS_PACKAGE_NAME = "net.minecraft.server.v" + VERSION;
	public final static String OBC_PACKAGE_NAME = "org.bukkit.craftbukkit.v" + VERSION;

	public static Class<?> getClass(String className){
		Class<?> clazz = null;
		try{
			clazz = Class.forName(className);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return clazz;
	}

	public static Class<?> getNMSClass(String className){
		return getClass(NMS_PACKAGE_NAME + "." + className);
	}

	public static Class<?> getOBCClass(String className){
		return getClass(OBC_PACKAGE_NAME + "." + className);
	}

	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes){
		Constructor<?> constructor = null;
		try {
			constructor = clazz.getConstructor(parameterTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return constructor;
	}

	public static Object newInstance(Constructor<?> constructor, Object... initargs){
		Object instance = null;
		try {
			instance = constructor.newInstance(initargs);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return instance;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes){
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}

	public static Field getField(Class<?> clazz, String fieldName){
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return field;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Object instance){
		T value = null;
		try {
			value = (T) field.get(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void setFieldValue(Field field, Object instance, Object value){
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Method method, Object instance, Object... parameters){
		T value = null;
		try {
			value = (T) method.invoke(instance, parameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return value;
	}

}
