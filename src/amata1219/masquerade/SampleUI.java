package amata1219.masquerade;

import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import amata1219.masquerade.dsl.Layout;

public class SampleUI extends UI {

	@Override
	public Function<Player, Layout> layout() {
		return build(Lines.x6, l -> {
			l.title = "sample";

			l.defaultSlot(20, s -> {
				s.def(i -> {
					i.type = Material.WHITE_WOOL;
					i.displayName = "default";
				})
				.after(i -> {
					i.type = Material.WHITE_WOOL;
					i.displayName = "first";
				})
				.after(i -> {
					i.type = Material.LIGHT_GRAY_WOOL;
					i.displayName = "second";
				})
				.after(i -> {
					i.type = Material.BLACK_WOOL;
					i.displayName = "third";
				});
			});

			l.onClose(e -> e.player.playSound(e.player.getLocation(), Sound.UI_TOAST_IN, 1, 1));
		});
	}

}
