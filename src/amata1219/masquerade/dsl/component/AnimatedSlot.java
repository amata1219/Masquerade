package amata1219.masquerade.dsl.component;

import java.util.ArrayList;

import amata1219.masquerade.effect.Effect;

public final class AnimatedSlot extends Slot {

	public final int interval;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();

	public AnimatedSlot(int interval){
		this.interval = interval;
	}

	public Icon apply(Icon icon, int frameCount){
		return frames.get(frameCount).apply(icon);
	}

	public int frames(){
		return frames.size();
	}

	public AnimatedSlot def(Effect<Icon> effect){
		defaultIcon = effect;
		return this;
	}

	public AnimatedSlot after(Effect<Icon> effect){
		frames.add(effect);
		return this;
	}

}
