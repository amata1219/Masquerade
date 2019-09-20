package amata1219.masquerade;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import amata1219.masquerade.event.OpenEvent;

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
	 * on
	 *
	 */

	private final HashMap<Integer, Slot> slots = new HashMap<>();
	private Supplier<Slot> defaultSlot;
	private Effect<OpenEvent> actionOnOpen, actionOnClick, actionOnClose;

	public Slot slotAt(int index){
		return slots.containsKey(index) ? slots.get(index) : defaultSlot.get();
	}

	public void defaultUnanimatedSlot(Effect<UnanimatedSlot> effect){
		defaultSlot = () -> effect.apply(new UnanimatedSlot());
	}

	public void defaultAnimatedSlot(Effect<AnimatedSlot> effect){
		defaultSlot = () -> effect.apply(new AnimatedSlot());
	}

	public void fire(OpenEvent event){
		slots.entrySet().stream()
		.filter(entry -> entry.getValue() instanceof AnimatedSlot)
		.forEach(entry -> {
			Async.define(() -> {
				AnimatedSlot slot = (AnimatedSlot) entry.getValue();
				event.inventory.setItem(entry.getKey(), slot.build(0).toItemStack());
			});
		});
		event.ui.activeTasks.add(null);
	}

}
