package amata1219.masquerade.dsl.component;

import java.util.ArrayList;

import org.bukkit.inventory.Inventory;

import amata1219.masquerade.async.Async;
import amata1219.masquerade.async.Async.AsyncTask;
import amata1219.masquerade.effect.Effect;
import graffiti.Tuple;

public final class AnimatedSlot extends Slot {

	private final int interval;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();

	public AnimatedSlot(int interval){
		this.interval = interval;
	}

	public Icon apply(Icon icon, int frameCount){
		return frames.get(frameCount).apply(icon);
	}

	public int frames(){
		return frames.size();
	}

	public AnimatedSlot def(Effect<Icon> effect){
		icon = effect;
		return this;
	}

	public AnimatedSlot after(Effect<Icon> effect){
		frames.add(effect);
		return this;
	}

	Tuple<AsyncTask, Integer> createTask(Inventory inventory, int index){
		Icon icon = build();
		AsyncTask task = Async.define(self -> inventory.setItem(index, apply(icon, (int) self.count() % frames()).toItemStack()));
		return new Tuple<>(task, interval);
	}

}
