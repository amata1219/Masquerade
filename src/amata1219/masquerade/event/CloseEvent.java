package amata1219.masquerade.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import amata1219.masquerade.UI;

public class CloseEvent {

	public UI ui;
	public Player player;
	public Inventory inventory;

	public CloseEvent(UI ui, InventoryCloseEvent event){
		this.ui = ui;
		player = (Player) event.getView().getPlayer();
		inventory = event.getInventory();
	}

}
