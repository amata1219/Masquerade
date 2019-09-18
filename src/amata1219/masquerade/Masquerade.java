package amata1219.masquerade;

import org.bukkit.plugin.java.JavaPlugin;

public class Masquerade extends JavaPlugin {

	private static Masquerade plugin;

	@Override
	public void onEnable(){
		plugin = this;
	}

	@Override
	public void onDisable(){

	}

	public static Masquerade plugin(){
		return plugin;
	}

}
