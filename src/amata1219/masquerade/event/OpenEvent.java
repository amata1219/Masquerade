package amata1219.masquerade.event;

import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class OpenEvent {

	public Inventory inventory;

	public OpenEvent(InventoryOpenEvent event){
		inventory = event.getInventory();
	}

}
