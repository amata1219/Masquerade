package amata1219.masquerade.text;

import java.util.function.Consumer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Text {

	private static final String COLORS = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
	private static final String NULL = String.valueOf(Character.MIN_VALUE);

	public static Text of(String text){
		return new Text(text);
	}
	private String text;

	public Text(String text){
		this.text = text;
	}

	public Text color(){
		char[] characters = text.toCharArray();

		for(int i = 0; i < characters.length - 1; i++){
			char color = characters[i + 1];

			if(characters[i] != '&' || COLORS.indexOf(color) <= -1) continue;

			if(i > 0 && characters[i - 1] == '-') characters[i - 1] = Character.MIN_VALUE;

			characters[i] = '§';
			characters[i + 1] = Character.toLowerCase(color);

			if(i < characters.length - 2 && characters[i + 2] == '-'){
				characters[i + 2] = Character.MIN_VALUE;
				i += 2;
			}else{
				i++;
			}
		}

		text = new String(characters).replace(NULL, "");
		return this;
	}

	public Text format(Object... objects){
		text = String.format(text, objects);
		return this;
	}

	public void accept(Consumer<String> action){
		action.accept(text);
	}

	public void sendTo(CommandSender to){
		accept(to::sendMessage);
	}

	public void actionbar(Player to){
		accept(text -> to.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text)));
	}

	@Override
	public String toString(){
		return text;
	}

}
