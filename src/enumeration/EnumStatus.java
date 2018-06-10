package enumeration;

public enum EnumStatus {
	porucena,
	otkazana,
	dostava,
	dostavljena,
	odbijena;
	
	public static EnumStatus valueOf(int o) {
		switch (o) {
		case 1:
			return porucena;
		case 2:
			return otkazana;
		case 3:
			return dostava;
		case 4:
			return dostavljena;
		case 5:
			return odbijena;
		default:
			return odbijena;
		}
	}
	
	public static int toInt(EnumStatus e) {
		switch(e) {
		case porucena:
			return 1;
		case otkazana:
			return 2;
		case dostava:
			return 3;
		case dostavljena:
			return 4;
		case odbijena:
			return 5;
		default:
			return 5;
		}
	}

}
