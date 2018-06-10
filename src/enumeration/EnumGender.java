package enumeration;

public enum EnumGender {
	zenski,
	muski;
	
	public static EnumGender valueOf(int a) {
		switch(a) {
		case 1:
			return zenski;
		default:
			return muski;
		}
	}
	
	public static int toInt(EnumGender pol) {
		switch(pol) {
		case zenski:
			return 1;
		default:
			return 2;
		}
	}
}

