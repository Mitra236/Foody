package enumeration;

public enum EnumArticle {
	Jelo,
	Pice;
	
	public static EnumArticle valueOf(int a) {
		switch(a) {
		case 1:
			return Jelo;
		default:
			return Pice;
		}
	}
	
	public static int toInt(EnumArticle article) {
		switch(article) {
		case Jelo:
			return 1;
		default:
			return 2;
		}
	}

}
