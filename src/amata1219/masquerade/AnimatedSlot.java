package amata1219.masquerade;

import java.util.ArrayList;

public final class AnimatedSlot extends Slot {

	private Effect<Icon> initializer;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();
	private Icon icon;
	private int frame;

	@Override
	public Icon build() {
		return frames.get(frame < frames.size() ? frame++ : (frame = 0)).apply(icon);
	}

	public AnimatedSlot init(Effect<Icon> effect){
		initializer = effect;
		return this;
	}

	public AnimatedSlot after(Effect<Icon> effect){
		frames.add(effect);
		return this;
	}

	public void reset(){
		icon = initializer.apply(new Icon());
		frame = 0;
	}

}
