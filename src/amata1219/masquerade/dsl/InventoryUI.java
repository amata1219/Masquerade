package amata1219.masquerade.dsl;

import java.util.function.Function;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.dsl.component.Lines;
import amata1219.masquerade.dsl.component.Option;
import amata1219.masquerade.util.BiEffect;

public interface InventoryUI extends InventoryHolder {

	default void open(Player player){
		player.openInventory(layout().apply(player).buildInventory());
	}

	Function<Player, UI> layout();

	default Function<Player, UI> build(Option option, BiEffect<Player, Layout> effect){
		return player -> new UI(effect.apply(player, new Layout(option)));
	}

	default Function<Player, UI> build(int size, BiEffect<Player, Layout> effect){
		return build(new Option(size), effect);
	}

	default Function<Player, UI> build(Lines lines, BiEffect<Player, Layout> effect){
		return build(lines.size(), effect);
	}

	default Function<Player, UI> build(InventoryType type, BiEffect<Player, Layout> effect){
		return build(new Option(type), effect);
	}

	@Override
	default Inventory getInventory() {
		throw new UnsupportedOperationException("Please use InventoryUI#layout() instead.");
	}

	public class UI implements InventoryHolder {

		public final Layout layout;

		public UI(Layout layout){
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

		public Inventory createInventory(InventoryType type, int size, String title){
			if(type != null){
				if(title != null) return Bukkit.createInventory(this, type, title);
				else return Bukkit.createInventory(this, type);
			}else{
				if(title != null) return Bukkit.createInventory(this, size, title);
				else return Bukkit.createInventory(this, size);
			}
		}

	}

}
