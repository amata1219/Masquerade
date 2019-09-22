package amata1219.masquerade.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.event.ClickEvent;
import amata1219.masquerade.event.CloseEvent;
import amata1219.masquerade.event.OpenEvent;
import amata1219.masquerade.monad.Maybe;
import amata1219.masquerade.reflection.SafeCast;

public class UIListener implements Listener {

	@EventHandler
	public void onOpen(InventoryOpenEvent event){
		cast(event.getInventory()).ifJust(l -> l.fire(new OpenEvent(event)));
	}

	@EventHandler
	public void onClick(InventoryClickEvent event){
		cast(event.getInventory()).ifJust(l -> {
			event.setCancelled(true);
			l.fire(new ClickEvent(event));
		});
	}

	@EventHandler
	public void onOpen(InventoryCloseEvent event){
		cast(event.getInventory()).ifJust(l -> l.fire(new CloseEvent(event)));
	}

	private Maybe<Layout> cast(Inventory inventory){
		return Maybe.unit(inventory).bind(Inventory::getHolder).bind(h -> SafeCast.down(h, Layout.class));
	}

}
