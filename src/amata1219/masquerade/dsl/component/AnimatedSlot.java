package amata1219.masquerade.dsl.component;

import java.util.ArrayList;

import org.bukkit.inventory.Inventory;

import amata1219.masquerade.async.AsyncTask;
import amata1219.masquerade.effect.Effect;
import amata1219.masquerade.tuplet.Tuple;

public final class AnimatedSlot extends Slot {

	private final int interval;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();

	public AnimatedSlot(int interval){
		this.interval = interval;
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
		AsyncTask task = AsyncTask.define(self -> inventory.setItem(index, frames.get((int) self.count() % frames.size()).apply(icon).toItemStack()));
		return new Tuple<>(task, interval);
	}

}
