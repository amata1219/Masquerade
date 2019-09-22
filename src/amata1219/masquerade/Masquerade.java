package amata1219.masquerade;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import amata1219.masquerade.dsl.component.Layout;
import amata1219.masquerade.enchantment.GleamEnchantment;
import amata1219.masquerade.listener.UIListener;
import amata1219.masquerade.reflection.Reflection;
import graffiti.Maybe;
import graffiti.SafeCast;

public class Masquerade extends JavaPlugin {

	private static Masquerade plugin;

	@Override
	public void onEnable(){
		plugin = this;

		getServer().getPluginManager().registerEvents(new UIListener(), this);

		Field acceptingNew = Reflection.getField(Enchantment.class, "acceptingNew");
		Reflection.setFieldValue(acceptingNew, null, true);

		try{
			Enchantment.registerEnchantment(GleamEnchantment.INSTANCE);
		}catch(Exception e){

		}finally{
			Reflection.setFieldValue(acceptingNew, null, false);
		}
	}

	@Override
	public void onDisable(){
		for(Player player : getServer().getOnlinePlayers()){
			Maybe.unit(player.getOpenInventory())
			.bind(v -> v.getTopInventory())
			.bind(i -> SafeCast.down(i, Layout.class))
			.ifJust(l -> player.closeInventory());
		}

		HandlerList.unregisterAll(this);
	}

	public static Masquerade plugin(){
		return plugin;
	}

}
