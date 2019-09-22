package amata1219.masquerade.monad;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Maybe<T> {

	public static <T> Maybe<T> unit(T value){
		return value != null ? new Just<>(value) : Nothing.empty();
	}

	<U> Maybe<U> bind(Function<T, U> binder);

	<U> Maybe<U> flatBind(Function<T, Maybe<U>> binder);

	Maybe<T> filter(Predicate<T> filter);

	default Maybe<T> ifJust(Consumer<T> action){
		return this;
	}

	void ifJustOrElse(Consumer<T> action, Supplier<T> other);

	default Maybe<T> ifNothing(Runnable action){
		return this;
	}

	public static class Just<T> implements Maybe<T> {

		private final T value;

		public Just(T value){
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public <U> Maybe<U> bind(Function<T, U> binder) {
			return unit(binder.apply(value));
		}

		@Override
		public <U> Maybe<U> flatBind(Function<T, Maybe<U>> binder) {
			return binder.apply(value);
		}

		@Override
		public Maybe<T> filter(Predicate<T> filter){
			return unit(filter.test(value) ? value : null);
		}

		@Override
		public Maybe<T> ifJust(Consumer<T> action) {
			action.accept(value);
			return this;
		}

		@Override
		public void ifJustOrElse(Consumer<T> action, Supplier<T> other){
			ifJust(action);
		}

	}

	public static class Nothing<T> implements Maybe<T> {

		private static final Nothing<?> EMPTY = new Nothing<>();

		@SuppressWarnings("unchecked")
		private static <T> Nothing<T> empty(){
			return (Nothing<T>) EMPTY;
		}

		@Override
		public <U> Maybe<U> bind(Function<T, U> binder) {
			return empty();
		}

		@Override
		public <U> Maybe<U> flatBind(Function<T, Maybe<U>> binder) {
			return empty();
		}

		@Override
		public Maybe<T> filter(Predicate<T> filter){
			return this;
		}

		@Override
		public void ifJustOrElse(Consumer<T> action, Supplier<T> other){
			action.accept(other.get());
		}

		@Override
		public Maybe<T> ifNothing(Runnable action) {
			action.run();
			return this;
		}

	}

}
