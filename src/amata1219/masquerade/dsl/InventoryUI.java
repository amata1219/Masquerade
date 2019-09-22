package amata1219.masquerade.dsl;

import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.dsl.component.Lines;
import amata1219.masquerade.dsl.component.Option;

public interface InventoryUI extends InventoryHolder {

	Function<Player, LayoutHolder> layout();

	default Function<Player, LayoutHolder> build(Option option, LayoutEffect effect){
		return player -> new LayoutHolder(effect.apply(player, new Layout(option)));
	}

	default Function<Player, LayoutHolder> build(int size, LayoutEffect effect){
		return build(new Option(size), effect);
	}

	default Function<Player, LayoutHolder> build(Lines lines, LayoutEffect effect){
		return build(lines.size(), effect);
	}

	default Function<Player, LayoutHolder> build(InventoryType type, LayoutEffect effect){
		return build(new Option(type), effect);
	}

	default void open(Player player){
		player.openInventory(layout().apply(player).buildInventory());
	}

	@Override
	default Inventory getInventory() {
		throw new UnsupportedOperationException("Please use InventoryUI#layout() instead.");
	}

	interface LayoutEffect {

		Layout apply(Player player, Layout layout);

	}

}
