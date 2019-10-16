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

			int date3_planes [] = {1,1,1};

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


				// checks if a vip
				if (seat_type.equals("V")) {

					//checks teh date
					if (date.equals("12/01/2020")) {

						//checks the plane
						switch (plane_tuid) {

							//if passenger want plane 1
							case 1:
							//if plane 1 is not full books plane 1
								if (date1_planes[0] <= 10) {
									preparedStatement1.setInt(3, plane_tuid);
									preparedStatement1.setString(5, date);
									preparedStatement1.setInt(6, date1_planes[0]);
									date1_planes[0] += 1;
							// if plane 1 is full books plane 2
								} else if (date1_planes[1] <= 8) {
									preparedStatement1.setInt(3, plane_tuid + 1);
									preparedStatement1.setString(5, date);
									preparedStatement1.setInt(6, date1_planes[1]);
									date1_planes[1] += 1;
								}

							// if passenger wants plane 2
							case 2:

								// if plane 2 is not full books it
								if (date1_planes[1] <= 8) {
									preparedStatement1.setInt(3, plane_tuid);
									preparedStatement1.setString(5, date);
									preparedStatement1.setInt(6, date1_planes[1]);
									date1_planes[1] += 1;

								// if plane 2 is full goes to plane 3
								} else if (date1_planes[2] <= 14) {
									preparedStatement1.setInt(3, plane_tuid + 1);
									preparedStatement1.setString(5, date);
									preparedStatement1.setInt(6, date1_planes[2]);
									date1_planes[2] += 1;
								}

							// if passenger wants plane 3
							case 3:

								// if plane 3 is not full books plane 3
								if (date1_planes[2] <= 14) {
									preparedStatement1.setInt(3, plane_tuid);
									preparedStatement1.setString(5, date);
									preparedStatement1.setInt(6, date1_planes[2]);
									date1_planes[2] += 1;

								// if plane 3 is full goes to next day plane 1
								} else if (date2_planes[0] <= 10) {
									preparedStatement1.setInt(3, plane_tuid-2);
									preparedStatement1.setString(5, "12/02/2020");
									preparedStatement1.setInt(6, date2_planes[0]);
									date2_planes[0] += 1;
								}

						}

					}

				//if passenger wants date 2

					else if (date.equals("12/02/2020")) {

					switch (plane_tuid) {

						//if passenger wants plane 1
						case 1:

							// if plane 1 is not full books plane 1
							if (date2_planes[0] <= 10) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[0]);
								date2_planes[0] += 1;

							// if plane 1 is full goes to plane 2
							} else if (date2_planes[1] <= 8) {
								preparedStatement1.setInt(3, plane_tuid+1);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[1]);
								date2_planes[1] += 1;

							}

						// if passenger wants plane 2
						case 2:
							// if plane 2 is not full
							if (date2_planes[1] <= 8) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[1]);
								date2_planes[1] += 1;

							// if plane 2 is full goes to plane 3
							} else if (date2_planes[2] <= 14) {
								preparedStatement1.setInt(3, plane_tuid+1);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[2]);
								date2_planes[2] += 1;

							}
						// if passenger wants plane 3
						case 3:
							// if plane 3 is not full
							if (date2_planes[2] <= 14) {
								preparedStatement1.setInt(3, plane_tuid);
								preparedStatement1.setString(5, date);
								preparedStatement1.setInt(6, date2_planes[2]);
								date2_planes[2] += 1;
							}

							else if (date3_planes[0] <= 10) {
								preparedStatement1.setInt(3, plane_tuid-2);
								preparedStatement1.setString(5, "12/03/2020");
								preparedStatement1.setInt(6, date3_planes[0]);
								date3_planes[0] += 1;
							}
					}
				}


				preparedStatement1.executeUpdate();
				i++;
			}
			}

			preparedStatement3.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}






}
