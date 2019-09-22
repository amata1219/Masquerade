package amata1219.masquerade;

import java.util.function.Function;
import java.util.stream.IntStream;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import amata1219.masquerade.dsl.InventoryUI;
import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.option.Lines;

public class SampleUI implements InventoryUI {

	@Override
	public Function<Player, Layout> layout() {
		return build(Lines.x2, (player, l) -> {
			l.title = "タイトル";

			//スロット未設定のインデックスに適用される
			l.defaultSlot(s -> s.icon(i -> i.material = Material.LIGHT_GRAY_STAINED_GLASS_PANE));

			//スロット番号0に適用される
			l.put(s -> {
				s.icon(i -> {
					i.material = Material.CHAINMAIL_HELMET;
					i.damage = 128;
					i.displayName = "表示名";
					i.lore(
						"1行目",
						"2行目",
						"3行目"
					);
					i.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
					i.gleam();
					i.flag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
					i.raw = item -> item.getItemMeta().setUnbreakable(true);
				});

				s.onClick(e -> playSound(player, Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1));
			}, 0);

			//スロット番号1から3に適用される
			l.put(s -> {
				s.icon(i -> {
					ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta meta = (SkullMeta) skull.getItemMeta();
					meta.setOwningPlayer(player);
					skull.setItemMeta(meta);
					i.based = skull;
				});
			}, IntStream.range(1, 4));

			//スロット番号4にアニメーションスロットとして適用され、20ticksの間隔でアイコンが更新される
			l.put(20, s -> {
				//defでデフォルト状態を、after群でアニメーションを定義する
				s.def(i -> {
					i.material = Material.GLASS;
				}).after(i -> {
					i.material = Material.WHITE_STAINED_GLASS;
					i.amount = 1;
				}).after(i -> {
					i.material = Material.LIGHT_GRAY_STAINED_GLASS;
					i.amount = 2;
				}).after(i -> {
					i.material = Material.GRAY_STAINED_GLASS;
					i.amount = 3;
				});

				s.onClick(e -> player.teleport(player.getLocation().add(0, 10, 0)));
			}, 4);

			l.onOpen(e -> playSound(player, Sound.MUSIC_DISC_MELLOHI, 1, 1));

			l.onClose(e -> player.stopSound(Sound.MUSIC_DISC_MELLOHI));

			l.onClick(e -> {
				if(e.isOutOfInventory()) playSound(player, Sound.BLOCK_ANVIL_PLACE, 1, 1);
			});
		});
	}

}
