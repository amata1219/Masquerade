package amata1219.masquerade.dsl;

import amata1219.masquerade.Effect;
import amata1219.masquerade.event.ClickEvent;

public abstract class Slot {

	protected Effect<ClickEvent> actionOnClick;

	public abstract Icon build();

	public void onClick(Effect<ClickEvent> action){
		actionOnClick = action;
	}

	public void fire(ClickEvent event){
		actionOnClick.runFor(event);
	}

}
