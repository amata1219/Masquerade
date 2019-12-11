package amata1219.masquerade.text;

import java.util.function.Consumer;

public class Text {

	private static final String COLORS = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
	private static final String NULL = String.valueOf(Character.MIN_VALUE);
	
	public static Text of(String text){
		return new Text(text);
	}
	
	public static Text empty(){
		return of("");
	}
	
	private String text;
	
	private Text(String text){
		this.text = coloring(text);
	}
	
	private static String coloring(String text){
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

		return new String(characters).replace(NULL, "");
	}
	
	public Text apply(Object... objects){
		text = String.format(text, objects);
		return this;
	}
	
	public void accept(Consumer<String> action){
		action.accept(text);
	}

}
