package amata1219.masquerade.monad;

import java.util.function.Function;

public interface Monad<T> {

	/* T - map
	 * η - unit
	 * μ - flat
	 */

	<U> Monad<U> unit(T value);

	<U> Monad<U> map(T value, Function<T, U> mapper);

	<U> Monad<U> flat(T value, Function<T, Monad<U>> mapper);

}
