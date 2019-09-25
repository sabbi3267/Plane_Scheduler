
public class Flights {
	public Flights(int tu_id, String airport, int gate, String time) {
		super();
		this.tu_id = tu_id;
		this.airport = airport;
		this.gate = gate;
		this.time = time;
	}
	private int tu_id;
	private String airport;
	private int gate;
	private String time;
	public int getTu_id() {
		return tu_id;
	}
	public void setTu_id(int tu_id) {
		this.tu_id = tu_id;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public int getGate() {
		return gate;
	}
	public void setGate(int gate) {
		this.gate = gate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
