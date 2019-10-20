
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Load driver success");

            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");


            String query1 = "INSERT INTO Passenger VALUES( ?, ?, ?, ?, ?)";

            String query2 = "INSERT INTO Seating  VALUES( ?, ?, ?, ?)";

            // Create prepare statement
            PreparedStatement passenger_in = connection.prepareStatement(query1);

            PreparedStatement seating_req_in = connection.prepareStatement(query2);

            // Get list product from file text
            ArrayList<Passenger> listPassenger = getListPassengerFromTextFile("C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.txt");
            ArrayList<Seating> listSeating = getListSeatingFromTextFile("C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.txt");

            // Insert list to db

            for (int i = 0; i < listPassenger.size(); i++) {
                passenger_in.setInt(1, listPassenger.get(i).getTu_id());
                passenger_in.setString(2, listPassenger.get(i).getFirst_I());
                passenger_in.setString(3, listPassenger.get(i).getMiddle_I());
                passenger_in.setString(4, listPassenger.get(i).getLast_Name());
                passenger_in.setString(5, listPassenger.get(i).getContact_no());
                passenger_in.executeUpdate();
                System.out.println("Insert success record:" + (i + 1));

            }

            for (int i = 0; i < listSeating.size(); i++) {
                //preparedStatement2.setInt(1, listSeating.get(i).getTuid());
                seating_req_in.setInt(1, listSeating.get(i).getCus_tuid());
                seating_req_in.setString(2, listSeating.get(i).getPlane_tuid());
                seating_req_in.setString(3, listSeating.get(i).getSeat_type());
                seating_req_in.setString(4,  listSeating.get(i).getDate());
                seating_req_in.executeUpdate();
                System.out.println("Insert success record:" + (i + 1));
            }


            String fetch_seating_req = "SELECT * FROM Seating ";
            Statement fetch_seat_req_statement = connection.createStatement();
            ResultSet rs = fetch_seat_req_statement.executeQuery(fetch_seating_req);


            //for inserting the data
            String schedule_in = "INSERT INTO Schedule  VALUES( ?, ?, ?, ?, ?, ?)";
            PreparedStatement schedule_in_statement = connection.prepareStatement(schedule_in);

            int i = 1;


            // iterate through the java resultset
            while (rs.next()) {
                int cus_tuid = rs.getInt("Cus_tuid");
                int plane_tuid = rs.getInt("Plane_tuid");
                String seat_type = rs.getString("Seat_type");
                String date = rs.getString("Date");



                System.out.format("%s %s %s %s\n", cus_tuid, plane_tuid, seat_type, date);

                schedule_in_statement.setInt(1, i);
                schedule_in_statement.setInt(2, cus_tuid);
                schedule_in_statement.setString(4, seat_type);

                i++;
                // checks if a vip
                if (seat_type.equals("V")) {

                    if(plane_tuid==0){
                        plane_tuid = 1;
                    }

                    //checks teh date
                    List<Object> assigned_seat = assignSeat(date,plane_tuid);


                    String assigned_date = String.valueOf(assigned_seat.get(0));
                    schedule_in_statement.setString(3,assigned_date);

                    int plane = Integer.parseInt(String.valueOf(assigned_seat.get(1)));
                    schedule_in_statement.setInt(5,plane);

                    int seat_no = Integer.parseInt(String.valueOf(assigned_seat.get(2))) ;
                    schedule_in_statement.setInt(6,seat_no+1);

                    System.out.print(assigned_date + " " +plane+ " " + (seat_no + 1));
                    System.out.println("");

                    //if passenger wants date 2
                }

                if (seat_type.equals("L")) {

                    //checks teh date

                    if(plane_tuid==0){
                        plane_tuid = 1;
                    }

                    List <Object> assigned_seat = assignSeat(date,plane_tuid);


                    String assigned_date = String.valueOf(assigned_seat.get(0));
                    schedule_in_statement.setString(3,assigned_date);

                    int plane = Integer.parseInt(String.valueOf(assigned_seat.get(1)));
                    schedule_in_statement.setInt(5,plane);

                    int seat_no = Integer.parseInt(String.valueOf(assigned_seat.get(2))) ;
                    schedule_in_statement.setInt(6,seat_no+1);

                    System.out.print(assigned_date + " " +plane+ " " + (seat_no + 1));
                    System.out.println("");

                    //if passenger wants date 2
                }

                schedule_in_statement.executeUpdate();
                //i++;

            }

            fetch_seat_req_statement.close();





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Passenger> getListPassengerFromTextFile(String filePath) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        ArrayList<Passenger> listResult = new ArrayList<Passenger>();

        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis);
            bReader = new BufferedReader(isr);
            // String save line get from text file
            String line = null;
            // Array save product
            String[] strPassenger = null;

            // Loop and get all data in text file
            while (true) {
                // Get 1 line
                line = bReader.readLine();
                // Check line get empty, exit loop
                if (line == null) {
                    break;
                } else {
                    strPassenger = line.split(" ");

                    if (strPassenger[0].equals("P")) {
                        System.out.println(strPassenger[0]);
                        listResult.add(new Passenger(Integer.parseInt(strPassenger[1]), strPassenger[2],
                                strPassenger[3], strPassenger[4], strPassenger[5]));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Read file error DEF");
            e.printStackTrace();
        } finally {
            // close file
            try {
                bReader.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return listResult;
    }

    private static ArrayList<Seating> getListSeatingFromTextFile(String filepath) {
        // TODO Auto-generated method stub
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        ArrayList<Seating> listResult = new ArrayList<Seating>();
        try {
            fis = new FileInputStream(filepath);
            isr = new InputStreamReader(fis);
            bReader = new BufferedReader(isr);
            // String save line get from text file
            String line = null;
            // Array save product
            String[] strSeating = null;
            int source_tuid = 0;
            // Loop and get all data in text file
            while (true) {
                // Get 1 line

                line = bReader.readLine();
                // Check line get empty, exit loop
                if (line == null) {
                    break;
                } else {
                    strSeating = line.split(" ");

                    if (strSeating[0].equals("S")) {
                        //source_tuid ++;


                        System.out.println(source_tuid);
                        System.out.println(strSeating[0]);
                        listResult.add(new Seating(Integer.parseInt(strSeating[1]), strSeating[2], strSeating[3],
                                strSeating[4]));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Read file error");
            e.printStackTrace();
        } finally {
            // close file
            try {
                bReader.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return listResult;
    }


    static int available(String date,int plane_tuid ) throws SQLException {
        // gets available seats on the current plane
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler\\plane.db");

        //prepare the statement to get the data
        String avail_out = "Select COUNT(Customer_tuid) From Schedule WHERE Flight_Date = ? AND Flight_tuid = ? ";
        PreparedStatement date_time = connection.prepareStatement(avail_out);
        date_time.setString(1, date);
        date_time.setInt(2, plane_tuid);

        //getting the data in a resultset
        ResultSet avail_rs;
        avail_rs = date_time.executeQuery();

        int recent_seat =1;

        recent_seat = avail_rs.getInt("COUNT(Customer_tuid)");

        date_time.close();

        connection.close();
        return recent_seat;


    }


    static List<Object> assignSeat(String date, int plane_tuid) throws SQLException, ParseException {


        int last_seat = available(date,plane_tuid);


        int max = 0;

        switch (plane_tuid){
            case 1 :
                max =10;
                break;
            case 2 :
                max = 8;
                break;
            case 3 :
                max = 14;
                break;

        }

        if (max > last_seat){


            return  Arrays.asList(date,plane_tuid,last_seat);
        }
        else {
            plane_tuid++;
            if(plane_tuid < 4){
                assignSeat(date,plane_tuid);
            }
            else {

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(date));
                //Incrementing the date by 1 day
                c.add(Calendar.DAY_OF_MONTH, 1);

                String addedDate = sdf.format(c.getTime());

                plane_tuid -=1;



                assignSeat(addedDate,plane_tuid);
            }
        }


        return assignSeat(date,plane_tuid);

    }


}
