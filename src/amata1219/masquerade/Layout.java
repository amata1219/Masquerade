package amata1219.masquerade;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.inventory.ItemStack;

import amata1219.masquerade.Async.AsyncTask;
import amata1219.masquerade.event.ClickEvent;
import amata1219.masquerade.event.CloseEvent;
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
	private Effect<OpenEvent> actionOnOpen;
	private Effect<ClickEvent> actionOnClick;
	private Effect<CloseEvent> actionOnClose;

	public Slot slotAt(int index){
		return slots.containsKey(index) ? slots.get(index) : defaultSlot.get();
	}

	public void defaultSlot(Effect<UnanimatedSlot> effect){
		defaultSlot = () -> effect.apply(new UnanimatedSlot());
	}

	public void defaultSlot(int interval, Effect<AnimatedSlot> effect){
		defaultSlot = () -> effect.apply(new AnimatedSlot(interval));
	}

	public void onOpen(Effect<OpenEvent> action){
		actionOnOpen = action;
	}

	public void fire(OpenEvent event){
		slots.entrySet().stream()
		.filter(entry -> entry.getValue() instanceof AnimatedSlot)
		.forEach(entry -> {
			AnimatedSlot slot = (AnimatedSlot) entry.getValue();
			AsyncTask task = Async.define(self -> {
				int index = entry.getKey();
				Icon icon = slot.build();
				ItemStack item = slot.apply(icon, (int) self.count() % slot.frames()).toItemStack();
				event.inventory.setItem(index, item);
			});
			task.executeTimer(slot.interval);
			event.ui.activeTasks.add(task);
		});
		actionOnOpen.runFor(event);
	}

	public void onClick(Effect<ClickEvent> action){
		actionOnClick = action;
	}

	public void fire(ClickEvent event){
		actionOnClick.runFor(event);
	}

	public void onClose(Effect<CloseEvent> action){
		actionOnClose = action;
	}

	public void fire(CloseEvent event){
		actionOnClose.runFor(event);
		event.ui.activeTasks.forEach(AsyncTask::cancel);
	}

}
