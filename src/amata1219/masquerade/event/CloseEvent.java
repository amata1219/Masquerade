package amata1219.masquerade.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class CloseEvent {

	public Player player;
	public Inventory inventory;

	public CloseEvent( InventoryCloseEvent event){
		player = (Player) event.getView().getPlayer();
		inventory = event.getInventory();
	}

}
