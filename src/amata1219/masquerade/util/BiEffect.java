package amata1219.masquerade.util;

public interface BiEffect<T, U> {

	default U apply(T t, U u){
		runFor(t, u);
		return u;
	}

	void runFor(T t, U u);

}
