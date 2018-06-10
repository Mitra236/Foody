package points;

import buyers.Buyer;

public class Points {
	
	Buyer buyer;
	int points;
	
	public Points() {
		this.buyer = new Buyer();
		this.points = 0;
	}

	public Points(Buyer buyer, int points) {
		super();
		this.buyer = buyer;
		this.points = points;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Points [buyer=" + buyer.getUsername() + ", points=" + points + "]";
	}
	
	

}
