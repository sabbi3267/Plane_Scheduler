import java.sql.*;


public class Schedule {
	public static void main(String[] args) {

		try {

			Connection connection;
			connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

			//for fetching the data
			String query3 = "SELECT * FROM Seating ";
			Statement preparedStatement3 = connection.createStatement();
			ResultSet rs = preparedStatement3.executeQuery(query3);


			//for inserting the data
			String queryIn = "INSERT INTO Schedule  VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement1 = connection.prepareStatement(queryIn);

			int i = 1;

			boolean seat_ase = true;
			// iterate through the java resultset
			while (rs.next()) {
				int cus_tuid = rs.getInt("Cus_tuid");
				int plane_tuid = rs.getInt("Plane_tuid");
				String seat_type = rs.getString("Seat_type");
				String date = rs.getString("Date");
				System.out.format("%s %s %s %s\n", cus_tuid, plane_tuid, seat_type, date);

				preparedStatement1.setInt(1, i);
				preparedStatement1.setInt(2, cus_tuid);
				preparedStatement1.setString(4, seat_type);

				if(seat_ase) {
					preparedStatement1.setInt(3, plane_tuid);
					preparedStatement1.setString(5, date);
					preparedStatement1.setInt(6, i);

				}


				preparedStatement1.executeUpdate();
				i++;
			}
			preparedStatement3.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}






}
