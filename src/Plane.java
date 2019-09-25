import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

//import java.util.Scanner;

public class Plane {
	private int tu_id;
	private int plane_id;
	private int max_vip;
	private int max_lux;
	private int total_seat;

	public Plane(int tu_id) {

		super();
		this.tu_id = tu_id;
		if (this.tu_id == 1) {
			this.max_vip = 4;
			this.max_lux = 6;
			this.setTotal_seat(10);
		}

		else if (this.tu_id == 2) {
			this.max_vip = 3;
			this.max_lux = 5;
			this.setTotal_seat(8);
		}

		else if (this.tu_id == 3) {
			this.max_vip = 6;
			this.max_lux = 8;
			this.setTotal_seat(14);
		}
	}

	public int getTu_id() {
		return tu_id;
	}

	public void setTu_id(int tu_id) {
		this.tu_id = tu_id;
	}

	public int getPlane_id() {
		return plane_id;
	}

	public void setPlane_id(int plane_id) {
		this.plane_id = plane_id;
	}

	public int getMax_vip() {
		return max_vip;
	}

	public void setMax_vip(int max_vip) {
		this.max_vip = max_vip;
	}

	public int getMax_lux() {
		return max_lux;
	}

	public void setMax_lux(int max_lux) {
		this.max_lux = max_lux;
	}

	public int getTotal_seat() {
		return total_seat;
	}

	public void setTotal_seat(int total_seat) {
		this.total_seat = total_seat;
	}

	public static void main(String[] args) {

		try {

			int seat_matrix[][] = null;
			Connection connection;
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			String query3 = "SELECT * FROM Seating ";

			Statement preparedStatement3 = connection.createStatement();

			ResultSet rs = preparedStatement3.executeQuery(query3);

			// iterate through the java resultset
			while (rs.next()) {
				int cus_tuid = rs.getInt("Cus_tuid");
				int plane_tuid = rs.getInt("Plane_tuid");
				String seat_type = rs.getString("Seat_type");
				String date = rs.getString("Date");

				System.out.println(seat_type);
				System.out.println(cus_tuid);

				Plane pl = new Plane(plane_tuid);
				int r = (pl.total_seat) / 2;
				seat_matrix = new int[r][2];
				int m=0;
				int n=0;
				if (seat_type.equals("V")) {
					seat_matrix[m][n] = cus_tuid;
					if(n<1) {
						n++;
					}else{
						m++;
					}
						
					
					
				}

				print2D(seat_matrix);

				// print the results
				System.out.format("%s %s %s %s\n", cus_tuid, plane_tuid, seat_type, date);
			}
			preparedStatement3.close();

			// Seating seat = new Seating();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void print2D(int mat[][]) {
		// Loop through all rows
		for (int[] row : mat)

			// converting each row as string
			// and then printing in a separate line
			System.out.println(Arrays.toString(row));
	}

}
