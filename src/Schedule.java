import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;


public class Schedule {


	public static void main(String[] args) throws SQLException {

//        int seat = available("12/01/2020",1);
//
//        System.out.println(seat);
//
//        add_seat("12/01/2020",1,seat);
//
//        seat = available("12/01/2020",1);
//
//        System.out.println(seat);

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
                    int seat_avail  = available(date,plane_tuid);


                        //checks the plane
                        switch (plane_tuid) {

                            //if passenger want plane 1
                            case 1:
                                //if plane 1 is not full books plane 1
                                if (seat_avail < 11) {
                                    preparedStatement1.setInt(3, plane_tuid);
                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,1,seat_avail);
                                    // if plane 1 is full books plane 2
                                } else {
                                    preparedStatement1.setInt(3, plane_tuid + 1);
                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,plane_tuid+1,seat_avail);
                                }
                                break;
                            case 0:
                                //if plane 1 is not full books plane 1
                                if (seat_avail<11) {
                                    preparedStatement1.setInt(3, plane_tuid+1);
                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,plane_tuid+1,seat_avail);
                                    // if plane 1 is full books plane 2
                                } else {
                                    preparedStatement1.setInt(3, plane_tuid + 2);
                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,plane_tuid+2,seat_avail);
                                }
                                break;
                            // if passenger wants plane 2
                            case 2:

                                // if plane 2 is not full books it
                                if (seat_avail < 8) {
                                    preparedStatement1.setInt(3, plane_tuid);
                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,plane_tuid,seat_avail);

                                    // if plane 2 is full goes to plane 3
                                } else  {
                                    preparedStatement1.setInt(3, plane_tuid + 1);
                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,plane_tuid+1,seat_avail);
                                }
                                break;
                            // if passenger wants plane 3
                            case 3:

                                // if plane 3 is not full books plane 3
                                if (seat_avail < 14) {
                                    preparedStatement1.setInt(3, plane_tuid);

                                    preparedStatement1.setString(5, date);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(date,plane_tuid,seat_avail);

                                    // if plane 3 is full goes to next day plane 1
                                } else  {
                                    preparedStatement1.setInt(3, plane_tuid-2);

                                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                                    Calendar c = Calendar.getInstance();
                                                    c.setTime(sdf.parse(date));
                //Incrementing the date by 1 day
                                                    c.add(Calendar.DAY_OF_MONTH, 1);
                                                    String addedDate = sdf.format(c.getTime());
                                    preparedStatement1.setString(5, addedDate);
                                    preparedStatement1.setInt(6, seat_avail);
                                    add_seat(addedDate,plane_tuid-2,seat_avail);
                                }
                                break;
                        }

                    //if passenger wants date 2
                }
                if (seat_type.equals("L")) {

                    //checks teh date
                    int seat_avail  = available(date,plane_tuid);


                    //checks the plane
                    switch (plane_tuid) {
                        //if passenger want plane 1
                        case 1:
                            //if plane 1 is not full books plane 1
                            if (seat_avail < 11) {
                                preparedStatement1.setInt(3, plane_tuid);
                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,1,seat_avail);
                                // if plane 1 is full books plane 2
                            } else {
                                preparedStatement1.setInt(3, plane_tuid + 1);
                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,plane_tuid+1,seat_avail);
                            }
                            break;
                        case 0:
                            //if plane 1 is not full books plane 1
                            if (seat_avail<11) {
                                preparedStatement1.setInt(3, plane_tuid+1);
                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,plane_tuid+1,seat_avail);
                                // if plane 1 is full books plane 2
                            } else {
                                preparedStatement1.setInt(3, plane_tuid + 2);
                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,plane_tuid+2,seat_avail);
                            }
                            break;
                        // if passenger wants plane 2
                        case 2:

                            // if plane 2 is not full books it
                            if (seat_avail < 8) {
                                preparedStatement1.setInt(3, plane_tuid);
                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,plane_tuid,seat_avail);

                                // if plane 2 is full goes to plane 3
                            } else  {
                                preparedStatement1.setInt(3, plane_tuid + 1);
                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,plane_tuid+1,seat_avail);
                            }
                            break;
                        // if passenger wants plane 3
                        case 3:

                            // if plane 3 is not full books plane 3
                            if (seat_avail < 14) {
                                preparedStatement1.setInt(3, plane_tuid);

                                preparedStatement1.setString(5, date);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(date,plane_tuid,seat_avail);

                                // if plane 3 is full goes to next day plane 1
                            } else  {
                                preparedStatement1.setInt(3, plane_tuid-2);

                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                Calendar c = Calendar.getInstance();
                                c.setTime(sdf.parse(date));
                                //Incrementing the date by 1 day
                                c.add(Calendar.DAY_OF_MONTH, 1);
                                String addedDate = sdf.format(c.getTime());
                                preparedStatement1.setString(5, addedDate);
                                preparedStatement1.setInt(6, seat_avail);
                                add_seat(addedDate,plane_tuid-2,seat_avail);
                            }
                            break;
                    }



                }


                preparedStatement1.executeUpdate();
                i++;
                preparedStatement1.close();
            }

            preparedStatement3.close();



        } catch (SQLException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





    }







	 static int available(String date,int plane_tuid ) throws SQLException {
        // gets available seats on the current plane
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

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

        connection.close();
        return recent_seat;


    }


    static  void add_seat (String date,int plane_tuid, int recent_seat ) throws SQLException {
        // gets available seats on the current plane

        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

        String avail_in = "UPDATE Date_time SET seat_taken = ? WHERE Date = ? AND plane_id = ? ";
        PreparedStatement avail_prepare = connection.prepareStatement(avail_in);
        recent_seat++;
        avail_prepare.setInt(1,recent_seat);
        avail_prepare.setString(2,date);
        avail_prepare.setInt(3,plane_tuid);
        avail_prepare.executeUpdate();

        avail_prepare.close();
    }
}
