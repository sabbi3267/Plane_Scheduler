import java.sql.*;
import java.util.Arrays;

//import java.util.Scanner;

public class Plane {
    private String tu_id;
    private int plane_id;
    private int max_vip;
    private int max_lux;
    private int total_seat;

    public Plane(String tu_id) {

        super();
        this.tu_id = tu_id;
        if (this.tu_id == "1") {
            this.max_vip = 4;
            this.max_lux = 6;
            this.setTotal_seat(10);
        } else if (this.tu_id == "2") {
            this.max_vip = 3;
            this.max_lux = 5;
            this.setTotal_seat(8);
        } else if (this.tu_id == "3") {
            this.max_vip = 6;
            this.max_lux = 8;
            this.setTotal_seat(14);
        }
    }


    public int getPlane_id() {
        return plane_id;
    }

    public void setPlane_id(int plane_id) {
        this.plane_id = plane_id;
    }

    public int getMax_vip() {
        return max_vip;
    }

    public void setMax_vip(int max_vip) {
        this.max_vip = max_vip;
    }

    public int getMax_lux() {
        return max_lux;
    }

    public void setMax_lux(int max_lux) {
        this.max_lux = max_lux;
    }

    public int getTotal_seat() {
        return total_seat;
    }

    public void setTotal_seat(int total_seat) {
        this.total_seat = total_seat;
    }

    public static void main(String[] args) {

        try {

            int seat_matrix[][] = null;
            Connection connection;
            connection = DriverManager.getConnection("jdbc:sqlite:plane.db");

            String query3 = "SELECT * FROM Seating ";

            Statement preparedStatement3 = connection.createStatement();

            ResultSet rs = preparedStatement3.executeQuery(query3);



			//PreparedStatement preparedStatement4 = connection.prepareStatement(book);

			while (rs.next()) {
				int cus_tuid = rs.getInt("Cus_tuid");
				int plane_tuid = rs.getInt("Plane_tuid");
				String seat_type = rs.getString("Seat_type");
				String date = rs.getString("Date");

				System.out.println(seat_type);
				System.out.println(cus_tuid);

//				Plane pl = new Plane(plane_tuid);
//				int r = (pl.total_seat) / 2;
//				seat_matrix = new int[r][2];
//				int m=0;
//				int n=0;
//
//				if (seat_type.equals("V")) {
//
//
//
//
//
//				}


                print2D(seat_matrix);

                // print the results
                System.out.format("%s %s %s %s\n", cus_tuid, plane_tuid, seat_type, date);
            }
            preparedStatement3.close();


            // Seating seat = new Seating();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void print2D(int mat[][]) {
        // Loop through all rows
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }




}

