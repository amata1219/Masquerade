package amata1219.masquerade.dsl.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import amata1219.masquerade.event.ClickEvent;
import amata1219.masquerade.event.CloseEvent;
import amata1219.masquerade.event.OpenEvent;
import amata1219.masquerade.util.Async;
import amata1219.masquerade.util.Effect;
import amata1219.masquerade.util.Async.AsyncTask;

public class Layout {

	public final Option option;
	public String title;
	private final HashMap<Integer, Slot> slots = new HashMap<>();
	private Supplier<Slot> defaultSlot;
	private Consumer<OpenEvent> actionOnOpen;
	private Consumer<ClickEvent> actionOnClick;
	private Consumer<CloseEvent> actionOnClose;
	public final ArrayList<AsyncTask> activeTasks = new ArrayList<>();

	public Layout(Option option){
		this.option = option;
	}

	public Inventory buildInventory(){
		Inventory inventory = createInventory();
		IntStream.range(0, inventory.getSize())
		.forEach(index -> inventory.setItem(index, slotAt(index).build().toItemStack()));
		return inventory;
	}

	private Inventory createInventory(){
		if(option.type != null){
			if(title != null) return Bukkit.createInventory(ui, option.type, title);
			else return Bukkit.createInventory(ui, option.type);
		}else{
			if(title != null) return Bukkit.createInventory(ui, option.size, title);
			else return Bukkit.createInventory(ui, option.size);
		}
	}

	public Slot slotAt(int index){
		return slots.containsKey(index) ? slots.get(index) : defaultSlot.get();
	}

	public void put(Effect<Slot> effect, IntStream indexes){
		put(effect, indexes.toArray());
	}

	public void put(Effect<Slot> effect, int... indexes){
		Arrays.stream(indexes).forEach(index -> slots.put(index, effect.apply(new Slot())));
	}

	public void put(int interval, Effect<AnimatedSlot> effect, IntStream indexes){
		put(interval, effect, indexes.toArray());
	}

	public void put(int interval, Effect<AnimatedSlot> effect, int... indexes){
		Arrays.stream(indexes).forEach(index -> slots.put(index, effect.apply(new AnimatedSlot(interval))));
	}

	public void defaultSlot(Effect<Slot> effect){
		defaultSlot = () -> effect.apply(new Slot());
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
			Icon icon = slot.build();
			AsyncTask task = Async.define(self -> {
				int frameCount = (int) self.count() % slot.frames();
				ItemStack item = slot.apply(icon, frameCount).toItemStack();
				event.inventory.setItem(entry.getKey(), item);
			});
			task.executeTimer(slot.interval);
			activeTasks.add(task);
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
		activeTasks.forEach(AsyncTask::cancel);
	}

}