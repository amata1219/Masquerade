package amata1219.masquerade.dsl;

import amata1219.masquerade.Effect;

public class UnanimatedSlot extends Slot {

	public Effect<Icon> defaultIcon;

	@Override
	public Icon build() {
		return defaultIcon.apply(new Icon());
	}

}
