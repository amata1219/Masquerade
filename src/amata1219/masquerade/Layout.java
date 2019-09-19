package amata1219.masquerade;

import java.util.HashMap;
import java.util.function.Consumer;

public class Layout implements InventoryUI {

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
	 * def(Effect<~>)
	 * wrap: supplier(effect)
	 *
	 */

	private final HashMap<Integer, Slot> slots = new HashMap<>();
	private Consumer<? extends Slot> defaultSlot;

	public void defineDefaultSlot(Consumer<AnimatedSlot> slot){

	}

}
