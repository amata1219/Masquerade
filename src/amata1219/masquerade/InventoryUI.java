package amata1219.masquerade;

import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import amata1219.masquerade.dsl.Layout;

public interface InventoryUI extends InventoryHolder {

	default void open(Player player){
		player.openInventory(layout().apply(player).buildInventory());
	}

	Function<Player, Layout> layout();

	@Override
	default public Inventory getInventory() {
		throw new UnsupportedOperationException("Please use InventoryUI#layout() instead.");
	}

}
