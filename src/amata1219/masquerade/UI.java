package amata1219.masquerade;

import java.util.ArrayList;
import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import amata1219.masquerade.Async.AsyncTask;
import amata1219.masquerade.dsl.Layout;

public abstract class UI implements InventoryUI {

	public final ArrayList<AsyncTask> activeTasks = new ArrayList<>();

	public Function<Player, Layout> build(Option option, Effect<Layout> effect){
		return player -> effect.apply(new Layout(player, this, option));
	}

	public Function<Player, Layout> build(int size, Effect<Layout> effect){
		return build(new Option(size), effect);
	}

	public Function<Player, Layout> build(Lines lines, Effect<Layout> effect){
		return build(lines.size(), effect);
	}

	public Function<Player, Layout> build(InventoryType type, Effect<Layout> effect){
		return build(new Option(type), effect);
	}

}
