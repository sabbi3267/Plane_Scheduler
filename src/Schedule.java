
public class Schedule {
	public Schedule(int tu_id, int cus_tuid, int flight_tuid, String flight_date, String seat_type, int seat_no) {
		super();
		this.tu_id = tu_id;
		this.cus_tuid = cus_tuid;
		this.flight_tuid = flight_tuid;
		this.flight_date = flight_date;
		this.seat_type = seat_type;
		this.seat_no = seat_no;
	}
	private int tu_id;
	private int cus_tuid;
	private int flight_tuid;
	private String flight_date;
	private	String seat_type;
	private int seat_no;
	public int getTu_id() {
		return tu_id;
	}
	public void setTu_id(int tu_id) {
		this.tu_id = tu_id;
	}
	public int getFlight_tuid() {
		return flight_tuid;
	}
	public void setFlight_tuid(int flight_tuid) {
		this.flight_tuid = flight_tuid;
	}
	public int getCus_tuid() {
		return cus_tuid;
	}
	public void setCus_tuid(int cus_tuid) {
		this.cus_tuid = cus_tuid;
	}
	public String getFlight_date() {
		return flight_date;
	}
	public void setFlight_date(String flight_date) {
		this.flight_date = flight_date;
	}
	public String getSeat_type() {
		return seat_type;
	}
	public void setSeat_type(String seat_type) {
		this.seat_type = seat_type;
	}
	public int getSeat_no() {
		return seat_no;
	}
	public void setSeat_no(int seat_no) {
		this.seat_no = seat_no;
	}
	
}
