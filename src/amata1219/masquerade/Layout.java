package amata1219.masquerade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import amata1219.masquerade.Async.AsyncTask;
import amata1219.masquerade.event.ClickEvent;
import amata1219.masquerade.event.CloseEvent;
import amata1219.masquerade.event.OpenEvent;

public class Layout implements InventoryUI {

	private final HashMap<Integer, Slot> slots = new HashMap<>();
	private Supplier<Slot> defaultSlot;
	private Consumer<OpenEvent> actionOnOpen;
	private Consumer<ClickEvent> actionOnClick;
	private Consumer<CloseEvent> actionOnClose;
	
	public Inventory buildInventory(){
		
	}

	public Slot slotAt(int index){
		return slots.containsKey(index) ? slots.get(index) : defaultSlot.get();
	}

	public void put(Effect<UnanimatedSlot> effect, IntStream indexes){
		put(effect, indexes.toArray());
	}

	public void put(Effect<UnanimatedSlot> effect, int... indexes){
		Arrays.stream(indexes).forEach(index -> slots.put(index, effect.apply(new UnanimatedSlot())));
	}

	public void put(int interval, Effect<AnimatedSlot> effect, IntStream indexes){
		put(interval, effect, indexes.toArray());
	}

	public void put(int interval, Effect<AnimatedSlot> effect, int... indexes){
		Arrays.stream(indexes).forEach(index -> slots.put(index, effect.apply(new AnimatedSlot(interval))));
	}

	public void defaultSlot(Effect<UnanimatedSlot> effect){
		defaultSlot = () -> effect.apply(new UnanimatedSlot());
	}

	public void defaultSlot(int interval, Effect<AnimatedSlot> effect){
		defaultSlot = () -> effect.apply(new AnimatedSlot(interval));
	}

	public void onOpen(Consumer<OpenEvent> action){
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
		actionOnOpen.accept(event);
	}

	public void onClick(Consumer<ClickEvent> action){
		actionOnClick = action;
	}

	public void fire(ClickEvent event){
		actionOnClick.accept(event);
	}

	public void onClose(Consumer<CloseEvent> action){
		actionOnClose = action;
	}

	public void fire(CloseEvent event){
		actionOnClose.accept(event);
		event.ui.activeTasks.forEach(AsyncTask::cancel);
	}
	
	

}
