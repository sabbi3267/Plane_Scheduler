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

			int date1_planes [] = {1,1,1};

			int date2_planes [] = {1,1,1};

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

				if(date.equals("12/01/2020") ) {
					switch (plane_tuid) {
						case 1:
							if(date1_planes[0] <=10) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date1_planes[0]);
								date1_planes[0] += 1;
							}
						case 2:
							if(date1_planes[1] <=8) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date1_planes[1]);
								date1_planes[1] += 1;
							}
						case 3:
							if(date1_planes[2] <=14) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date1_planes[2]);
								date1_planes[2] += 1;
							}
					}
				}
				else if(date.equals("12/02/2020")) {
					switch (plane_tuid) {
						case 1:
							if(date2_planes[0] <=10) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[0]);
								date2_planes[0] += 1;
							}

						case 2:
							if(date2_planes[1] <=8) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[1]);
								date2_planes[1] += 1;
							}
						case 3:
							if(date2_planes[2] <=14) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[2]);
								date2_planes[2] += 1;
							}
					}
				}

				else break;
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
