
public class Fees {
	public Fees(int tu_id, String seat_type, int fee) {
		super();
		this.tu_id = tu_id;
		this.seat_type = seat_type;
		this.fee = fee;
	}
	private int tu_id;
	private String seat_type;
	private int fee;
	public int getTu_id() {
		return tu_id;
	}
	public void setTu_id(int tu_id) {
		this.tu_id = tu_id;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getSeat_type() {
		return seat_type;
	}
	public void setSeat_type(String seat_type) {
		this.seat_type = seat_type;
	}
}
