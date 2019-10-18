import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;


public class Schedule {


	public static void main(String[] args) throws SQLException {

        int seat = available("12/01/2020",1);

        System.out.println(seat);

        add_seat("12/01/2020",1,seat);

        seat = available("12/01/2020",1);

        System.out.println(seat);

//		try {
//
//			Connection connection;
//			connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Noor PC\\IdeaProjects\\Plane_Scheduler\\plane.db");
//
//			//for fetching the data
//            String seating_out = "SELECT * FROM Seating ";
//
//			Statement seating_select = connection.createStatement();
//
//			ResultSet seating_rs = seating_select.executeQuery(seating_out);
//
//
//            int index =1;
//
//            System.out.println();
//
//			//for inserting the data
//
//
//
//            String queryIn = "INSERT INTO Schedule  VALUES(?, ?, ?, ?, ?, ?)";
//            PreparedStatement schedule_insert = connection.prepareStatement(queryIn);
//
//
//
//
//
//
//
//			// iterate through the java resultset
//			while (seating_rs.next()) {
//
//			    // gets the seating data from the table
//                int cus_tuid = seating_rs.getInt("Cus_tuid");
//                int plane_tuid = seating_rs.getInt("Plane_tuid");
//                String seat_type = seating_rs.getString("Seat_type");
//                String date = seating_rs.getString("Date");
//
//                // gets available seats on the current plane
//
//
//
//                if (seat_type.equals("V")) {
//
//                    int seat = available(date,plane_tuid);
//
//                    if(plane_tuid ==1 & seat <11 ) {
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
//
////                    seat = available(date,plane_tuid+1);
////                    else if ( plane_tuid == 2  & ){
////
////                        schedule_insert.setInt(1, index);
////                        schedule_insert.setInt(2, cus_tuid);
////                        schedule_insert.setString(4, seat_type);
////                        schedule_insert.setInt(3, plane_tuid);
////                        schedule_insert.setString(5, date);
////                        schedule_insert.setInt(6, seat +1);
////
////                        add_seat(date,plane_tuid,seat);
////
////                    }
//
//
//
//
//                }
//
//                schedule_insert.executeUpdate();
//
//
//
//
//				//System.out.format("%s %s %s %s %s %s\n", tuid, count,cus_tuid, date,  plane_tuid,seat_type);
//
//
//
//
//
//			}
//
//			seating_select.close();
//
//		} catch (SQLException  e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}


	 static int available(String date,int plane_tuid ) throws SQLException {
        // gets available seats on the current plane
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Noor PC\\IdeaProjects\\Plane_Scheduler\\plane.db");

        //prepare the statement to get the data
        String avail_out = "Select * From Date_time WHERE Date = ? AND plane_id = ? ";
        PreparedStatement date_time = connection.prepareStatement(avail_out);
        date_time.setString(1, date);
        date_time.setInt(2, plane_tuid);

         //getting the data in a resultset
        ResultSet avail_rs;
        avail_rs = date_time.executeQuery();



        int recent_seat =0;

        recent_seat = avail_rs.getInt("seat_taken");

        date_time.close();

        return recent_seat;

    }


    static  void add_seat (String date,int plane_tuid, int recent_seat ) throws SQLException {
        // gets available seats on the current plane
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Noor PC\\IdeaProjects\\Plane_Scheduler\\plane.db");

        String avail_in = "UPDATE Date_time SET seat_taken = ? WHERE Date = ? AND plane_id = ? ";

        PreparedStatement avail_prepare = connection.prepareStatement(avail_in);
        recent_seat++;
        avail_prepare.setInt(1,recent_seat);
        avail_prepare.setString(2,date);
        avail_prepare.setInt(3,plane_tuid);

        avail_prepare.executeUpdate();
    }
}
