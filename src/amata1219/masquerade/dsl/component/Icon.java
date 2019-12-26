package amata1219.masquerade.dsl.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Iterables;

import amata1219.masquerade.effect.Effect;
import amata1219.masquerade.enchantment.GleamEnchantment;
import amata1219.masquerade.text.Text;

public class Icon {

	public Material material = Material.AIR;
	public int amount = 1;
	public int damage;
	public String displayName;
	public List<String> lore = new ArrayList<>();
	public Map<Enchantment, Integer> enchantments = new HashMap<>();
	public Set<ItemFlag> flags = new HashSet<>();
	public ItemStack basedItemStack;
	public Effect<ItemStack> raw;

	public ItemStack toItemStack(){
		ItemStack item = basedItemStack != null ? basedItemStack : new ItemStack(material);
		basedItemStack.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		if(meta != null){
			if(meta instanceof Damageable) ((Damageable) meta).setDamage(damage);
			meta.setDisplayName(displayName);
			meta.setLore(lore);
			enchantments.forEach((ench, level) -> meta.addEnchant(ench, level, true));
			meta.addItemFlags(Iterables.toArray(flags, ItemFlag.class));
			item.setItemMeta(meta);
		}

		if(raw != null) raw.apply(item);

		return item;
	}

	public void lore(String... strs){
		for(String str : strs) lore.add(str);
	}
	
	public void lore(Text... texts){
		for(Text text : texts) text.accept(lore::add);
	}

	public void enchant(Enchantment enchantment, int level){
		enchantments.put(enchantment, level);
	}

	public void gleam(){
		enchant(GleamEnchantment.INSTANCE, 1);
	}

	public void flag(ItemFlag... flags){
		for(ItemFlag flag : flags) this.flags.add(flag);
	}

}
