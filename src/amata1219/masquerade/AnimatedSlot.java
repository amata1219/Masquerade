package amata1219.masquerade;

import java.util.ArrayList;

public final class AnimatedSlot extends Slot {

	private Icon icon;
	private int frameCount;
	private Effect<Icon> defaultIcon;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();

	@Override
	public Icon build() {
		return frames.get(frameCount < frames.size() ? frameCount++ : (frameCount = 0)).apply(icon);
	}

	public AnimatedSlot def(Effect<Icon> effect){
		defaultIcon = effect;
		return this;
	}

	public AnimatedSlot after(Effect<Icon> effect){
		frames.add(effect);
		return this;
	}

	public void reset(){
		icon = defaultIcon.apply(new Icon());
		frameCount = 0;
	}

}
