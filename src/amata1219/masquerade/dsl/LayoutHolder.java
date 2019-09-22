package amata1219.masquerade.dsl;

import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import amata1219.masquerade.dsl.component.Layout;

public class LayoutHolder implements InventoryHolder {

	public final Layout layout;

	public LayoutHolder(Layout layout){
		this.layout = layout;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

	public Inventory buildInventory(){
		Inventory inventory = createInventory(layout.option.type, layout.option.size, layout.title);
		IntStream.range(0, inventory.getSize()).forEach(index -> inventory.setItem(index, layout.slotAt(index).build().toItemStack()));
		return inventory;
	}

	private Inventory createInventory(InventoryType type, int size, String title){
		if(type != null)
			if(title != null) return Bukkit.createInventory(this, type, title);
			else return Bukkit.createInventory(this, type);
		else
			if(title != null) return Bukkit.createInventory(this, size, title);
			else return Bukkit.createInventory(this, size);
	}

}
