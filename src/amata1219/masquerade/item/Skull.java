package amata1219.masquerade.item;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Skull {

	public static ItemStack createFrom(UUID uuid){
		return createFrom(Bukkit.getOfflinePlayer(uuid));
	}

	public static ItemStack createFrom(OfflinePlayer player){
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(player);
		skull.setItemMeta(meta);
		return skull;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createFrom(String base64){
		int hash = base64.hashCode();
		UUID uuid = new UUID(hash, hash);
		String data = "{SkullOwner:{Id:\"" + uuid.toString() + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}";
		return Bukkit.getUnsafe().modifyItemStack(new ItemStack(Material.PLAYER_HEAD), data);
	}

}
