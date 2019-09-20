package amata1219.masquerade;

import java.util.ArrayList;

public final class AnimatedSlot extends Slot {

	private Effect<Icon> defaultIcon;
	private final ArrayList<Effect<Icon>> frames = new ArrayList<>();

	@Override
	public Icon build() {
		return defaultIcon.apply(new Icon());
	}

	public Icon build(int frameCount){
		return frames.get(frameCount).apply(build());
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
