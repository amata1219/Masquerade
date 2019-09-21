package amata1219.masquerade.dsl;

import amata1219.masquerade.Effect;
import amata1219.masquerade.event.ClickEvent;

public class Slot {

	protected Effect<Icon> defaultIcon;
	protected Effect<ClickEvent> actionOnClick;

	public Icon build() {
		return defaultIcon.apply(new Icon());
	}

	public void onClick(Effect<ClickEvent> action){
		actionOnClick = action;
	}

	public void fire(ClickEvent event){
		actionOnClick.runFor(event);
	}

}
