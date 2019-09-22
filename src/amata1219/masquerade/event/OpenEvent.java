package amata1219.masquerade.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class OpenEvent {

	public Player player;
	public Inventory inventory;

	public OpenEvent(InventoryOpenEvent event){
		player = (Player) event.getView().getPlayer();
		inventory = event.getInventory();
	}

}
