package amata1219.masquerade;

import java.util.ArrayList;

public final class AnimatedSlot extends Slot {

	private final ArrayList<Effect<Icon>> effects = new ArrayList<>();
	private final Icon icon = new Icon();
	private int index;

	@Override
	public Icon build() {
		return effects.get(index < effects.size() ? index++ : (index = 0)).apply(icon);
	}

	public AnimatedSlot init(Effect<Icon> effect){
		effect.runFor(icon);
		return this;
	}

	public AnimatedSlot after(Effect<Icon> effect){
		effects.add(effect);
		return this;
	}

}
