
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Load driver success");

            Connection connection = DriverManager.getConnection("jdbc:sqlite:plane.db");


            String query1 = "INSERT INTO Passenger VALUES( ?, ?, ?, ?, ?)";

            String query2 = "INSERT INTO Seating  VALUES( ?, ?, ?, ?)";

			String book = " INSERT INTO Schedule (Customer_tuid,Flight_tuid,Flight_Date,Seat_type, Seat_no) VALUES ( ?, ?, ?, ?, ?)";

			// Create prepare statement
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

			PreparedStatement preparedStatement3 = connection.prepareStatement(book);

            // Get list product from file text
            ArrayList<Passenger> listPassenger = getListPassengerFromTextFile("plane.txt");
            ArrayList<Seating> listSeating = getListSeatingFromTextFile("plane.txt");

            // Insert list to db

//            for (int i = 0; i < listPassenger.size(); i++) {
//                preparedStatement1.setInt(1, listPassenger.get(i).getTu_id());
//                preparedStatement1.setString(2, listPassenger.get(i).getFirst_I());
//                preparedStatement1.setString(3, listPassenger.get(i).getMiddle_I());
//                preparedStatement1.setString(4, listPassenger.get(i).getLast_Name());
//                preparedStatement1.setString(5, listPassenger.get(i).getContact_no());
//                preparedStatement1.executeUpdate();
//                System.out.println("Insert success record:" + (i + 1));
//
//            }
//
//            for (int i = 0; i < listSeating.size(); i++) {
//                //preparedStatement2.setInt(1, listSeating.get(i).getTuid());
//                preparedStatement2.setInt(1, listSeating.get(i).getCus_tuid());
//                preparedStatement2.setString(2, listSeating.get(i).getPlane_tuid());
//                preparedStatement2.setString(3, listSeating.get(i).getSeat_type());
//                preparedStatement2.setString(4, listSeating.get(i).getDate());
//                preparedStatement2.executeUpdate();
//                System.out.println("Insert success record:" + (i + 1));
//            }



			for (int i = 0; i < listSeating.size(); i++) {
				int seat_no=1;
				Plane pl = new Plane(listSeating.get(i).getPlane_tuid());
				int max_seat = pl.getTotal_seat();
				if (listSeating.get(i).getSeat_type() == "V") {
					preparedStatement3.setInt(1, listSeating.get(i).getCus_tuid());
					preparedStatement3.setString(2, listSeating.get(i).getPlane_tuid());
					preparedStatement3.setString(3, listSeating.get(i).getSeat_type());
					preparedStatement3.setString(4, listSeating.get(i).getDate());
					preparedStatement3.setInt(5, seat_no);
					seat_no++;
					if(seat_no<=max_seat){
						preparedStatement3.executeUpdate();
					}

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
        ArrayList<Passenger> listResult = new ArrayList<>();

        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis);
            bReader = new BufferedReader(isr);
            // String save line get from text file
            String line;
            // Array save product
            String[] strPassenger;

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

	public static ArrayList<Seating> getListSeatingFromTextFile(String filepath) {
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

}
