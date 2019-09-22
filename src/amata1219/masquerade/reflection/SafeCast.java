package amata1219.masquerade.reflection;

public class SafeCast {

	public static <S, T extends S> T down(S value, Class<T> target){
		return target.isInstance(value) ? target.cast(value) : null;
	}

	public static <T, S extends T> T up(S value){
		return (T) value;
	}

	public static <T, S extends T> T up(S value, Class<T> target){
		return (T) value;
	}

}
