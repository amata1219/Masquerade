package amata1219.masquerade.dsl;

import java.util.ArrayList;
import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.dsl.component.Lines;
import amata1219.masquerade.dsl.component.Option;
import amata1219.masquerade.util.Effect;
import amata1219.masquerade.util.Async.AsyncTask;

public interface InventoryUI extends InventoryHolder {

	default void open(Player player){
		player.openInventory(layout().apply(player).buildInventory());
	}

	Function<Player, Layout> layout();

	@Override
	default public Inventory getInventory() {
		throw new UnsupportedOperationException("Please use InventoryUI#layout() instead.");
	}

	public abstract class AbstractUI implements InventoryUI {

		public final ArrayList<AsyncTask> activeTasks = new ArrayList<>();

		public Function<Player, Layout> build(Option option, Effect<Layout> effect){
			return player -> effect.apply(new Layout(this, option, player));
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

}
