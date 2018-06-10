package enumeration;

public enum EnumRestaurant {
	picerija,
	domaci,
	rostilj,
	kineski,
	indijski,
	poslasticarnica;
	
	public static EnumRestaurant valueOf(int r) {
		switch(r) {
		case 1:
			return picerija;
		case 2:
			return domaci;
		case 3:
			return rostilj;
		case 4:
			return kineski;
		case 5:
			return indijski;
		default:
			return poslasticarnica;
		}
	}
	
	public static int toInt(EnumRestaurant e) {
		switch (e) {
		
		case picerija:
			return 1;
		case domaci:
			return 2;
		case rostilj:
			return 3;
		case kineski:
			return 4;
		case indijski:
			return 5;
		case poslasticarnica:
			return 6;
		default:
			return 6;
		}
	}
}
