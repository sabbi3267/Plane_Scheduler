
public class Passenger {

	private int tu_id;
	private String first_I;
	private String middle_I;
	private String last_Name;
	private String contact_no;


	public Passenger( int TUID, String First_I, String Middle_I, String Last_Name, String Contact_no) {
		super();
		this.tu_id = TUID;
		this.first_I= First_I;
		this.middle_I=Middle_I;
		this.last_Name = Last_Name;
		this.contact_no = Contact_no;
		
	}


	public String getFirst_I() {
		return first_I;
	}

	public void setFirst_I(String first_I) {
		this.first_I = first_I;
	}

	public String getMiddle_I() {
		return middle_I;
	}

	public void setMiddle_I(String middle_I) {
		this.middle_I = middle_I;
	}

	public String getLast_Name() {
		return last_Name;
	}

	public void setLast_Name(String last_Name) {
		this.last_Name = last_Name;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public int getTu_id() {
		return tu_id;
	}

	public void setTu_id(int tu_id) {
		this.tu_id = tu_id;
	}

}
