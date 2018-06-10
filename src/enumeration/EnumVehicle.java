package enumeration;

public enum EnumVehicle {
	bicikl,
	skuter,
	automobil,
	kombi;
	
	public static EnumVehicle valueOf(int a) {
		switch(a) {
		case 1:
			return bicikl;
		case 2:
			return skuter;
		case 3:
			return automobil;
		case 4:
			return kombi;
		default:
			return kombi;
			
		}
	}
	
	public static int toInt(EnumVehicle v) {
		switch(v) {
		case bicikl:
			return 1;
		case skuter:
			return 2;
		case automobil:
			return 3;
		case kombi:
			return 4;
		default:
			return 4;
		
		}
		
	}
}
