
public class Seating {
	public Seating(  int cus_tuid, String plane_tuid, String seat_type, String date) {
		super();
	//	this.status = status;
		//this.tuid = tuid;
		this.cus_tuid = cus_tuid;
		this.plane_tuid = plane_tuid;
		this.seat_type = seat_type;
		this.date = date;
	}

	//private String status;
	private int tuid;
	private int cus_tuid;
	private String plane_tuid;
	private String seat_type; 
	private String date;
	
	
	
	public int getCus_tuid() {
		return cus_tuid;
	}
	public void setCus_tuid(int cus_tuid) {
		this.cus_tuid = cus_tuid;
	}
	public String getPlane_tuid() {
		return plane_tuid;
	}
	public void setPlane_tuid(String plane_tuid) {
		this.plane_tuid = plane_tuid;
	}
	public String getSeat_type() {
		return seat_type;
	}
	public void setSeat_type(String seat_type) {
		this.seat_type = seat_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTuid() {
		return tuid;
	}
	public void setTuid(int tuid) {
		this.tuid = tuid;
	}

//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
	
	
}
