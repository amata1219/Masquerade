package amata1219.masquerade;

import java.util.HashMap;
import java.util.function.Consumer;

public class Layout implements InventoryUI {

	public static void main(String[] $){
		Consumer<? extends A> a = null;
		a = aa -> aa.getClass();
		a = new Consumer<B>(){

			@Override
			public void accept(B t) {
			}

		};

		Supplier<A> m = () -> new B();
	}

	public static class A { }
	public static class B extends A { void a() { } }

	public static <T extends A> Consumer<A> define(Consumer<T> run){
		return run;
	}

	/*
	 * Map<Integer, Slot>
	 *
	 * Slot -> Animated/Unanimated
	 *
	 * Icon -> Pseudo ItemStack
	 *
	 *
	 *
	 *
	 * Layout
	 * - slots
	 * - action(click, open, close)
	 * - defaultSlot
	 *
	 */

	private final HashMap<Integer, Slot> slots = new HashMap<>();
	private Consumer<? extends Slot> defaultSlot;

	public void defineDefaultSlot(Consumer<AnimatedSlot> slot){

	}

}
