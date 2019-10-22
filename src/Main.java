
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.exit;


public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");

            Scanner sc = new Scanner(System.in);




            while (true) {
                System.out.println("1 - TO Create the database");
                System.out.println("2 - TO ADD files the database");
                System.out.println("3 - TO Schedule ");
                System.out.println("4 - TO Drop the tables database");
                System.out.println("5 - TO Drop the tables database");


                    switch (sc.nextInt()){

                        case  1 :
                            create();
                            break;
                        case 2 :
                            insert("plane.txt");
                            break;
//                            while (true){
//                                System.out.println("Enter File name");
//                                System.out.println("Enter done to when complelete");
//                                if(!sc.next().equals("done")){
//
//
//                                }
//                            }


                        case 3:
                            assign_vips();
                            assign_lux() ;
                            print_booking();
                            break;
                        case 4:
                            dropTable();
                            break;
                        case 5:
                           System.exit(0);

                    }

            }


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
        connection = getConnection();

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

    static void assign_vips() throws SQLException, ParseException {

        Connection connection = getConnection();


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
            // checks if a assign_vips


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

                schedule_in_statement.executeUpdate();
                //if passenger wants date 2
            }


        }

        fetch_seat_req_statement.close();



    }

    static void assign_lux() throws SQLException, ParseException {

        Connection connection = getConnection();


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
            // checks if a assign_vips


            if (seat_type.equals("L")) {

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

                schedule_in_statement.executeUpdate();
                //if passenger wants date 2
            }


        }

        fetch_seat_req_statement.close();



    }


    private static Connection getConnection() throws  SQLException   {
        Connection con;    // Database path -- if it's new database, it will be created in the project folder
        con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\nalam\\IdeaProjects\\Plane_Scheduler_V2\\plane_test.db");
        return con;
    }


    static void create()throws  SQLException{

        Connection con;
        con = getConnection();
        Statement create;
        create = con.createStatement();


        // fees
        create.executeUpdate("CREATE TABLE Fees (" +
                "    TUID      INTEGER," +
                "    SEAT_TYPE VARCHAR," +
                "    Fee       INTEGER" +
                ");");
        create.executeUpdate("INSERT INTO Fees (TUID, Seat_type, Fee)" +
                "VALUES (1 , 'V', 4000);");
        create.executeUpdate("INSERT INTO Fees (TUID, Seat_type, Fee)" +
                "VALUES (2 , 'L', 2500);");


        //flights
        create.executeUpdate("CREATE TABLE Flights (" +
                "TUID         INTEGER PRIMARY KEY,"+
                "PLANE_ID     INTEGER ,"+
                "AIRPORT_CODE INTEGER,"+
                "DEPART_GATE  INTEGER,"+
                " DEPART_TIME  TIME);");
        create.executeUpdate("INSERT INTO Flights (TUID, Plane_ID, AIRPORT_CODE, DEPART_GATE, DEPART_TIME)" +
                "VALUES (1 ,1 , 'MBS', 3 , '7:00');");
        create.executeUpdate("INSERT INTO Flights (TUID, Plane_ID, AIRPORT_CODE, DEPART_GATE, DEPART_TIME)" +
                "VALUES (2 ,2 , 'MBS', 1 , '13:00');");
        create.executeUpdate("INSERT INTO Flights (TUID, Plane_ID, AIRPORT_CODE, DEPART_GATE, DEPART_TIME)" +
                "VALUES (3 ,3 , 'MBS', 2 , '21:00');");



        //passenger
        create.executeUpdate( "CREATE TABLE Passenger (" +
                "    TUID       INTEGER UNIQUE" +
                "                       PRIMARY KEY," +
                "    First_I    VARCHAR," +
                "    Middle_I   VARCHAR," +
                "    Last_Name  STRING," +
                "    Contact_no STRING" +
                ");");

        //planes
        create.executeUpdate("CREATE TABLE Planes (" +
                "    TUID     INTEGER NOT NULL" +
                "                     PRIMARY KEY," +
                "    Plane_ID INTEGER," +
                "    MAX_VIP  INTEGER," +
                "    MAX_LUX  INTEGER" +
                ");");


        create.executeUpdate("INSERT INTO Planes (TUID, Plane_ID, MAX_VIP, MAX_LUX )" +
                "VALUES (1 ,'RC407' , 4, 6 );");
        create.executeUpdate("INSERT INTO Planes (TUID, Plane_ID, MAX_VIP, MAX_LUX )" +
                "VALUES (2 ,'TR407' , 3, 5 );");
        create.executeUpdate("INSERT INTO Planes (TUID, Plane_ID, MAX_VIP, MAX_LUX )" +
                "VALUES (3 ,'KR381' , 6, 8 );");

        create.executeUpdate("CREATE TABLE Schedule (" +
                "    TUID          INTEGER UNIQUE" +
                "                          PRIMARY KEY," +
                "    Customer_tuid INTEGER," +
                "    Flight_Date   STRING," +
                "    Seat_type     VARCHAR," +
                "    Flight_tuid   INTEGER," +
                "    Seat_no       INTEGER" +
                ");");
        create.executeUpdate("CREATE TABLE Seating (" +
                "    Cus_tuid   INTEGER," +
                "    Plane_tuid STRING," +
                "    Seat_type  STRING," +
                "    Date       DATE" +
                ");");




    }


    static  void dropTable()throws SQLException{
        Connection con;
        con = getConnection();
        Statement drop;
        drop = con.createStatement();
        drop.execute("DROP TABLE Schedule; " );
        drop.execute("DROP TABLE Seating; " );
        drop.execute("DROP TABLE Passenger; " );
        drop.execute("DROP TABLE Fees; " );
        drop.execute("DROP TABLE Flights; " );
        drop.execute("DROP TABLE Planes; " );


    }


    static  void insert(String fileName) throws SQLException{
        Connection connection = getConnection();


        String query1 = "INSERT INTO Passenger VALUES( ?, ?, ?, ?, ?)";

        String query2 = "INSERT INTO Seating  VALUES( ?, ?, ?, ?)";

        // Create prepare statement
        PreparedStatement passenger_in = connection.prepareStatement(query1);

        PreparedStatement seating_req_in = connection.prepareStatement(query2);

        // Get list product from file text
        ArrayList<Passenger> listPassenger = getListPassengerFromTextFile(fileName);
        ArrayList<Seating> listSeating = getListSeatingFromTextFile(fileName);

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



    }



    static void print_booking() throws SQLException{

        Connection connection = getConnection();



        Statement fetch_seat_req_statement = connection.createStatement();

        ResultSet rs = fetch_seat_req_statement.executeQuery("SELECT * FROM Schedule Natural JOIN Passenger LEFT JOIN Flights WHERE Flights.TUID = Schedule.Flight_tuid ;"

                );

        System.out.format("%10s %10s %10s %20s %20s%20s %10s %10s %10s %10s %10s %10s\n", "Customer ID", "First I","Middle I","Last Name", "Contact NO." ,"date", "Plane No", "Seat_type" , "Seat_no" ,"AIRPORT", "DEPART_GATE" , "DEPART_TIME");

        // iterate through the java resultset
        while (rs.next()) {
            int cus_tuid = rs.getInt("Customer_tuid");
            String f_I = rs.getString("First_I");
            String m_I = rs.getString("Middle_I");
            String last_name = rs.getString("Last_name");
            String contact_no = rs.getString("Contact_no");
            String date = rs.getString("Flight_Date");
            int plane_tuid = rs.getInt("Flight_tuid");
            String seat_type = rs.getString("Seat_type");
            int seat_no = rs.getInt("Seat_no");
            String airport = rs.getString("AIRPORT_CODE");
            String gate = rs.getString("DEPART_GATE");
            String time = rs.getString("DEPART_TIME");

                if(plane_tuid==1){
                    switch (seat_no){
                        case 5:
                            seat_type ="L";
                            seat_no  = 1;
                            break;
                        case 6:
                            seat_type ="L";
                            seat_no = 2;
                            break;
                        case 7:
                            seat_type ="L";
                            seat_no = 3;
                            break;
                        case 8:
                            seat_type ="L";
                            seat_no = 4;
                            break;
                        case 9:
                            seat_type ="L";
                            seat_no = 5;
                            break;
                        case 10:
                            seat_type ="L";
                            seat_no = 6;
                        break;
                    }

                }

                if(plane_tuid==2){
                    switch (seat_no){
                     case 4:
                        seat_type ="L";
                        seat_no  = 1;
                        break;
                        case 5:
                        seat_type ="L";
                        seat_no = 2;
                        break;
                    case 6:
                        seat_type ="L";
                        seat_no = 3;
                        break;
                    case 7:
                        seat_type ="L";
                        seat_no = 4;
                        break;
                    case 8:
                        seat_type ="L";
                        seat_no = 5;
                     break;
                }

            }
             if(plane_tuid==3){
                switch (seat_no){
                    case 7:
                        seat_type ="L";
                        seat_no  = 1;
                        break;
                    case 8:
                        seat_type ="L";
                        seat_no = 2;
                        break;
                    case 9:
                        seat_type ="L";
                        seat_no = 3;
                        break;
                    case 10:
                        seat_type ="L";
                        seat_no = 4;
                        break;
                    case 11:
                        seat_type ="L";
                        seat_no = 5;
                        break;
                    case 12:
                        seat_type ="L";
                        seat_no = 6;
                        break;
                    case 13:
                        seat_type ="L";
                        seat_no = 7;
                        break;
                    case 14:
                        seat_type ="L";
                        seat_no = 8;
                        break;

                }

            }


            System.out.format("%10s %10s %10s %20s %20s %20s %10s %10s %10s %10s %10s %10s\n", cus_tuid, f_I,m_I,last_name,contact_no ,date, plane_tuid, seat_type , seat_no, airport, gate, time);




        }

        fetch_seat_req_statement.close();






    }


    static void print_report() throws  SQLException{

        Connection connection = getConnection();

        Statement per_plane;
        per_plane = connection.createStatement();
        ResultSet report = per_plane.executeQuery("SELECT s.Customer_tuid,p.First_I, p.Middle_I, p.Last_Name, p.Contact_no , s.Flight_date, s.Flight_tuid , s.Seat_type, s.seat_no" +
                "From Schedule as s" +
                "left Join Passenger as p" +
                "Where s.Customer_tuid = p.TUID" );

        while (report.next()) {
            int cus_tuid = report.getInt("COUNT(Customer_tuid)");
            int plane_tuid = report.getInt("Flight_tuid");
            String seat_type = report.getString("Seat_type");
            String date = report.getString("Flight_Date");




            System.out.format("%s %s %s %s \n", cus_tuid, date, plane_tuid, seat_type );




        }


        //int total_plane1 =

        //int=

        report.close();
    }

}
