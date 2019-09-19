package amata1219.masquerade;

public interface Effect<T> {

	default T apply(T object){
		runFor(object);
		return object;
	}

	void runFor(T object);

}
