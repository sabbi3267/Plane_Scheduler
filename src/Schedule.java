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
            String seating_out = "SELECT * FROM Seating ";

			Statement seating_select = connection.createStatement();

			ResultSet seating_rs = seating_select.executeQuery(seating_out);


            int index =1;

            System.out.println();

			//for inserting the data



            String queryIn = "INSERT INTO Schedule  VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement schedule_insert = connection.prepareStatement(queryIn);







			// iterate through the java resultset
			while (seating_rs.next()) {

			    // gets the seating data from the table
                int cus_tuid = seating_rs.getInt("Cus_tuid");
                int plane_tuid = seating_rs.getInt("Plane_tuid");
                String seat_type = seating_rs.getString("Seat_type");
                String date = seating_rs.getString("Date");

                // gets available seats on the current plane



                if (seat_type.equals("V")) {

                    int seat = available(date,plane_tuid);

                    if(plane_tuid ==1 & seat <11 ) {

                        schedule_insert.setInt(1, index);
                        schedule_insert.setInt(2, cus_tuid);
                        schedule_insert.setString(4, seat_type);
                        schedule_insert.setInt(3, plane_tuid);
                        schedule_insert.setString(5, date);
                        schedule_insert.setInt(6, seat +1);

                        add_seat(date,plane_tuid,seat);

                    }

//                    seat = available(date,plane_tuid+1);
//                    else if ( plane_tuid == 2  & ){
//
//                        schedule_insert.setInt(1, index);
//                        schedule_insert.setInt(2, cus_tuid);
//                        schedule_insert.setString(4, seat_type);
//                        schedule_insert.setInt(3, plane_tuid);
//                        schedule_insert.setString(5, date);
//                        schedule_insert.setInt(6, seat +1);
//
//                        add_seat(date,plane_tuid,seat);
//
//                    }




                }

                schedule_insert.executeUpdate();




				//System.out.format("%s %s %s %s %s %s\n", tuid, count,cus_tuid, date,  plane_tuid,seat_type);





			}

			seating_select.close();

//			System.out.println(Arrays.toString(date1_planes));
//			System.out.println(Arrays.toString(date2_planes));
//			System.out.println(Arrays.toString(date3_planes));

		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}


	 static int available(String date,int plane_tuid ) throws SQLException {
        // gets available seats on the current plane
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

        String avail_out = "Select * From Date_time WHERE Date = ? AND WHERE plane_id = ? ";
         ResultSet avail_rs;
         try (PreparedStatement date_time = connection.prepareStatement(avail_out)) {

             date_time.setString(1, date);
             date_time.setInt(2, plane_tuid);

             avail_rs = date_time.executeQuery(avail_out);
         }


         int recent_seat = avail_rs.getInt("seat_taken");


         return recent_seat;

    }


    static  void add_seat (String date,int plane_tuid, int recent_seat ) throws SQLException {
        // gets available seats on the current plane
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

        String avail_in = "UPDATE Date_tim SET seat_taken = ? WHERE Date = ? AND WHERE plane_id = ? ";
        PreparedStatement avail_prepare = connection.prepareStatement(avail_in);

        avail_prepare.setInt(1,recent_seat+1);
        avail_prepare.setString(2,date);
        avail_prepare.setInt(3,plane_tuid);
        avail_prepare.executeUpdate();
    }
}
