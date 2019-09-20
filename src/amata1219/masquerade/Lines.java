package amata1219.masquerade;

public enum Lines {

	x1,
	x2,
	x3,
	x4,
	x5,
	x6;

	public static Lines of(int size){
		return values()[Math.min(size > 0 ? size / 9 - 1 : 0, 5)];
	}

	public int size(){
		return (ordinal() + 1) * 9;
	}

}
