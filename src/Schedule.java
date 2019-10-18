import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;


public class Schedule {


	public static void main(String[] args) {



		try {

			Connection connection;
			connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

			//for fetching the data
			String query3 = "Select Max(TUID), Count(Cus_tuid),Cus_tuid , date, Plane_tuid, seat_type" +
                    " From Seating " +
                    "Group by Date,Plane_tuid " +
                    "Order by Date,Plane_tuid, seat_type desc ";

			Statement preparedStatement3 = connection.createStatement();

			ResultSet rs = preparedStatement3.executeQuery(query3);




            System.out.println();

			//for inserting the data
        	String queryIn = "UPDATE Seating SET seat_type = ? WHERE Cus_tuid = ? ";

    		PreparedStatement preparedStatement1 = connection.prepareStatement(queryIn);
//
//			int i = 1;a
//
//			int date1_planes [] = {1,1,1};
//
//			int date2_planes [] = {1,1,1};
//
//			int date3_planes [] = {1,1,1};
//
//
//            HashMap<String, Integer> map = new HashMap<>();


			// iterate through the java resultset
			while (rs.next()) {

                int tuid = rs.getInt("Max(TUID)");
                int count = rs.getInt("Count(Cus_tuid)");
				int cus_tuid = rs.getInt("Cus_tuid");
				int plane_tuid = rs.getInt("Plane_tuid");
				String seat_type = rs.getString("Seat_type");
				String date = rs.getString("Date");


                if(plane_tuid==1 & count >10) {
                    if (seat_type.equals("V")) {
                        preparedStatement1.setString(1, "L");
			            preparedStatement1.setInt(2, cus_tuid);

                    }

                }

                preparedStatement1.executeUpdate();

//				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//
//				Calendar c = Calendar.getInstance();
//
//				c.setTime(sdf.parse(date));
//
//				String newDate = sdf.format(c.getTime());




				System.out.format("%s %s %s %s %s %s\n", tuid, count,cus_tuid, date,  plane_tuid,seat_type);




//				preparedStatement1.setInt(1, i);
//				preparedStatement1.setInt(2, cus_tuid);
//				preparedStatement1.setString(4, seat_type);


				// checks if a vip
//				if (seat_type.equals("V")) {
//
//                    if (date.equals(12/01/2020)) {
//
//                        //checks the plane
//                        switch (plane_tuid) {
//
//                            //if passenger want plane 1
//                            case 1:
//                                //if plane 1 is not full books plane 1
//                                if (date1_planes[0] < 11) {
//                                    preparedStatement1.setInt(3, plane_tuid);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[0]);
//                                    date1_planes[0] += 1;
//                                    // if plane 1 is full books plane 2
//                                } else {
//                                    preparedStatement1.setInt(3, plane_tuid + 1);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[1]);
//                                    date1_planes[1] += 1;
//                                }
//                                break;
//                            case 0:
//                                //if plane 1 is not full books plane 1
//                                if (date1_planes[0] < 11) {
//                                    preparedStatement1.setInt(3, plane_tuid+1);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[0]);
//                                    date1_planes[0] += 1;
//                                    // if plane 1 is full books plane 2
//                                } else {
//                                    preparedStatement1.setInt(3, plane_tuid + 2);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[1]);
//                                    date1_planes[1] += 1;
//                                }
//                                break;
//                            // if passenger wants plane 2
//                            case 2:
//
//                                // if plane 2 is not full books it
//                                if (date1_planes[1] < 8) {
//                                    preparedStatement1.setInt(3, plane_tuid);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[1]);
//                                    date1_planes[1] += 1;
//
//                                    // if plane 2 is full goes to plane 3
//                                } else  {
//                                    preparedStatement1.setInt(3, plane_tuid + 1);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[2]);
//                                    date1_planes[2] += 1;
//                                }
//                                break;
//                            // if passenger wants plane 3
//                            case 3:
//
//                                // if plane 3 is not full books plane 3
//                                if (date1_planes[2] < 14) {
//                                    preparedStatement1.setInt(3, plane_tuid);
//                                    preparedStatement1.setString(5, date);
//                                    preparedStatement1.setInt(6, date1_planes[2]);
//                                    date1_planes[2] += 1;
//
//                                    // if plane 3 is full goes to next day plane 1
//                                } else  {
//                                    preparedStatement1.setInt(3, plane_tuid-2);
//                                    preparedStatement1.setString(5, "12/02/2020");
//                                    preparedStatement1.setInt(6, date2_planes[0]);
//                                    date2_planes[0] += 1;
//                                }
//                                break;
//                        }
//
//                    }
//
//
//
//
//			}
//
//
//				if (seat_type.equals("L")) {
//
//					//checks teh date
//					if (date.equals(12/01/2020)) {
//
//						//checks the plane
//						switch (plane_tuid) {
//
//							//if passenger want plane 1
//							case 1:
//								//if plane 1 is not full books plane 1
//								if (date1_planes[0] < 11) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[0]);
//									date1_planes[0] += 1;
//									// if plane 1 is full books plane 2
//								} else {
//									preparedStatement1.setInt(3, plane_tuid + 1);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[1]);
//									date1_planes[1] += 1;
//								}
//								break;
//							case 0:
//								//if plane 1 is not full books plane 1
//								if (date1_planes[0] < 11) {
//									preparedStatement1.setInt(3, plane_tuid+1);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[0]);
//									date1_planes[0] += 1;
//									// if plane 1 is full books plane 2
//								} else {
//									preparedStatement1.setInt(3, plane_tuid + 2);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[1]);
//									date1_planes[1] += 1;
//								}
//								break;
//							// if passenger wants plane 2
//							case 2:
//
//								// if plane 2 is not full books it
//								if (date1_planes[1] < 8) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[1]);
//									date1_planes[1] += 1;
//
//									// if plane 2 is full goes to plane 3
//								} else  {
//									preparedStatement1.setInt(3, plane_tuid + 1);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[2]);
//									date1_planes[2] += 1;
//								}
//								break;
//							// if passenger wants plane 3
//							case 3:
//
//								// if plane 3 is not full books plane 3
//								if (date1_planes[2] < 14) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[2]);
//									date1_planes[2] += 1;
//
//									// if plane 3 is full goes to next day plane 1
//								} else  {
//									preparedStatement1.setInt(3, plane_tuid-2);
//									preparedStatement1.setString(5, "12/02/2020");
//									preparedStatement1.setInt(6, date2_planes[0]);
//									date2_planes[0] += 1;
//								}
//								break;
//						}
//
//					}
//
//					//if passenger wants date 2
//
//					else if (date.equals("12/02/2020")) {
//
//						switch (plane_tuid) {
//
//							//if passenger wants plane 1
//							case 1:
//
//								// if plane 1 is not full books plane 1
//								if (date2_planes[0] < 10) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date2_planes[0]);
//									date2_planes[0] += 1;
//
//									// if plane 1 is full goes to plane 2
//								} else  {
//									preparedStatement1.setInt(3, plane_tuid+1);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date2_planes[1]);
//									date2_planes[1] += 1;
//
//								}
//								break;
//
//							case 0:
//								//if plane 1 is not full books plane 1
//								if (date1_planes[0] < 11) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[0]);
//									date1_planes[0] += 1;
//									// if plane 1 is full books plane 2
//								} else {
//									preparedStatement1.setInt(3, plane_tuid + 1);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date1_planes[1]);
//									date1_planes[1] += 1;
//								}
//								break;
//							// if passenger wants plane 2
//							case 2:
//								// if plane 2 is not full
//								if (date2_planes[1] < 8) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date2_planes[1]);
//									date2_planes[1] += 1;
//
//									// if plane 2 is full goes to plane 3
//								} else  {
//									preparedStatement1.setInt(3, plane_tuid+1);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date2_planes[2]);
//									date2_planes[2] += 1;
//
//								}
//								break;
//							// if passenger wants plane 3
//
//							case 3:
//								// if plane 3 is not full
//								if (date2_planes[2] < 14) {
//									preparedStatement1.setInt(3, plane_tuid);
//									preparedStatement1.setString(5, date);
//									preparedStatement1.setInt(6, date2_planes[2]);
//									date2_planes[2] += 1;
//								}
//
//								else  {
//									preparedStatement1.setInt(3, plane_tuid - 2);
//									preparedStatement1.setString(5, "12/03/2020");
//									preparedStatement1.setInt(6, date3_planes[0]);
//									date3_planes[0] += 1;
//								}
//								break;
//						}
//					}
//
//
//
//				}


				//preparedStatement1.executeUpdate();
				//i++;
			}

			preparedStatement3.close();

//			System.out.println(Arrays.toString(date1_planes));
//			System.out.println(Arrays.toString(date2_planes));
//			System.out.println(Arrays.toString(date3_planes));

		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}






}
