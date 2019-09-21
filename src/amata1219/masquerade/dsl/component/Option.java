package amata1219.masquerade.dsl.component;

import org.bukkit.event.inventory.InventoryType;

public class Option {

	public final int size;
	public final InventoryType type;

	public Option(int size){
		this.size = size;
		this.type = null;
	}

	public Option(InventoryType type){
		this.size = 0;
		this.type = type;
	}

}
