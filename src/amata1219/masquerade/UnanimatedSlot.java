package amata1219.masquerade;

public class UnanimatedSlot extends Slot {

	public Effect<Icon> defaultIcon;

	@Override
	public Icon build() {
		return defaultIcon.apply(new Icon());
	}

}
