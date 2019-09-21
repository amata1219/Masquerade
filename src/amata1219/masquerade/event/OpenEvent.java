package amata1219.masquerade.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import amata1219.masquerade.UI;

public class OpenEvent {

	public UI ui;
	public Player player;
	public Inventory inventory;

	public OpenEvent(UI ui, InventoryOpenEvent event){
		this.ui = ui;
		player = (Player) event.getView().getPlayer();
		inventory = event.getInventory();
	}

}
