package amata1219.masquerade;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryUI extends InventoryHolder {

	@Override
	default public Inventory getInventory() {
		return null;
	}

}
