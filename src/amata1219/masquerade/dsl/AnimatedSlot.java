package amata1219.masquerade.dsl;

import java.util.ArrayList;

import amata1219.masquerade.Effect;

public final class AnimatedSlot extends Slot {

	private Effect<Icon> defaultIcon;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();
	public final int interval;

	public AnimatedSlot(int interval){
		this.interval = interval;
	}

	@Override
	public Icon build() {
		return defaultIcon.apply(new Icon());
	}

	public Icon apply(Icon icon, int frameCount){
		return frames.get(frameCount).apply(icon);
	}

	public AnimatedSlot def(Effect<Icon> effect){
		defaultIcon = effect;
		return this;
	}

	public AnimatedSlot after(Effect<Icon> effect){
		frames.add(effect);
		return this;
	}

	public int frames(){
		return frames.size();
	}

}
