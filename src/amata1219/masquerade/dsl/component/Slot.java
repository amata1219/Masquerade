package amata1219.masquerade.dsl.component;

import amata1219.masquerade.effect.Effect;
import amata1219.masquerade.event.ClickEvent;

public class Slot {

	protected Effect<Icon> icon;
	protected Effect<ClickEvent> actionOnClick = __ -> {};

	public Icon build() {
		return icon.apply(new Icon());
	}

	public void icon(Effect<Icon> icon){
		this.icon = icon;
	}

	public void onClick(Effect<ClickEvent> action){
		actionOnClick = action;
	}

	public void fire(ClickEvent event){
		actionOnClick.runFor(event);
	}

}
