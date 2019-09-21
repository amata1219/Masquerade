package amata1219.masquerade.dsl.component;

import amata1219.masquerade.event.ClickEvent;
import amata1219.masquerade.util.Effect;

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
