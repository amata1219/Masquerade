package amata1219.masquerade.event;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class CloseEvent {

	public Inventory inventory;

	public CloseEvent(InventoryCloseEvent event){
		inventory = event.getInventory();
	}

}
