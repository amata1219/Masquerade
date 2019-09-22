package amata1219.masquerade;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import amata1219.masquerade.enchantment.GleamEnchantment;
import amata1219.masquerade.listener.UIListener;
import amata1219.masquerade.reflection.Reflection;

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
		HandlerList.unregisterAll(this);
	}

	public static Masquerade plugin(){
		return plugin;
	}

}
