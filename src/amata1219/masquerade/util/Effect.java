package amata1219.masquerade.util;

public interface Effect<T> {

	default T apply(T t){
		runFor(t);
		return t;
	}

	void runFor(T t);

}
