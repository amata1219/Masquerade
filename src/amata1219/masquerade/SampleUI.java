package amata1219.masquerade;

import java.util.function.Function;

import org.bukkit.entity.Player;

import amata1219.masquerade.dsl.InventoryUI;
import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.option.Lines;

public class SampleUI implements InventoryUI {

	@Override
	public Function<Player, Layout> layout() {
		return build(Lines.x2, (player, l) -> {
			l.title = "Sample UI";
		});
	}

}
