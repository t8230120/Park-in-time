import java.sql.*;
import java.util.Scanner;

public class ParkingManager extends User {

    private static Connection dbcon;
    Scanner scanner = new Scanner(System.in);

    private static final String dbName= "park";
    private static final String dbUser= "root";
    private static final String dbPassword= "PROGprog"; 

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        String url = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?user=" + dbUser + "&password=" + dbPassword;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) { 
            System.out.println("ClassNotFoundException: " + e.getMessage());
            System.exit(0);
        }
        
        try {
		dbcon = DriverManager.getConnection(url);			
        } catch (SQLException e) {			
            System.out.println("SQLException: " + e.getMessage());
            System.exit(0);
                
        } 

        int choice = 0;
        do {
            System.out.println("What do you want to do, " + username + "?");
            System.out.println("1. Add parking");
            System.out.println("2. Delete parking");
            System.out.println("3. Edit parking");
            System.out.println("4. Exit app");
            System.out.print("Please choose a number from 1-4: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            switch (choice) {
                case 1:
                    addParking(scanner);
                    break;
                case 2:
                    deleteParking(scanner);
                    break;
                case 3:
                    editParking(scanner);
                    break;
                case 4:
                    System.out.println("Exiting the app. Have a good day!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 4.");
            }
        } while (choice != 4);

        try {
            dbcon.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void addParking(Scanner scanner) {
        System.out.println("You picked 1.Add Parking");

        System.out.print("Enter parking id: ");
        String parkingId = scanner.nextLine();

        System.out.print("Enter parking address: ");
        String parkingAddress = scanner.nextLine();

        System.out.print("Enter parking spots: ");
        int parkingSpots = scanner.nextInt();
       
        System.out.print("Enter area id: ");
        int areaId = scanner.nextInt();
        
        System.out.print("Enter price: ");
        int price = scanner.nextInt();
        
        System.out.print("Enter opening hour: ");
        int openingHour = scanner.nextInt();
        
        System.out.print("Enter closing hour: ");
        int closingHour = scanner.nextInt();

        System.out.print("Enter google maps link: ");
        int googleMapsLInk = scanner.nextInt();
        scanner.nextLine();
        String query = "INSERT INTO Parking (parking id, parking address, parking spots,
                    area id, price, opening hour, closing hour, google maps link) VALUES ('" 
                    + parkingId + "', '" + parkingAddress + "', " + parkingSpots+ ""
                    + areaId + "', '" + price + "', " + openingHour+ 
                    + closingHour + "', " googleMapsLInk ") ";

        try (Statement stmt = dbcon.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("parking added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding parking: " + e.getMessage());
        }  finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void deleteParking(Scanner scanner) {
        System.out.println("You picked 2.Delete parking");

        System.out.print("Enter the parking id of the parking to delete: ");
        String licensePlate = scanner.nextLine();

        String query = "DELETE FROM parking WHERE parking id = '" + parkingId + "'";

        try (Statement stmt = dbcon.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("parking deleted successfully.");
            } else {
                System.out.println("No parking found with the given parking id.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting parking: " + e.getMessage());
        }  finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void editParkingScanner scanner) {
        System.out.println("You picked 3.Edit parking");

        System.out.print("Enter the parking id of the parking to edit: ");
        String parkingId = scanner.nextLine();

        System.out.print("Enter new parking address: ");
        String newParkingAddress = scanner.nextLine();

        System.out.print("Enter new parking spots: ");
        int newParkingSpots = scanner.nextInt();
       
        System.out.print("Enter new area id: ");
        int newAreaId = scanner.nextInt();
        
        System.out.print("Enter new price: ");
        int newprice = scanner.nextInt();
        
        System.out.print("Enter new opening hour: ");
        int newOpeningHour = scanner.nextInt();
        
        System.out.print("Enter new closing hour: ");
        int newClosingHour = scanner.nextInt();

        System.out.print("Enter new google maps link: ");
        int newGoogleMapsLInk = scanner.nextInt();
       
        scanner.nextLine();

        String query = "UPDATE Parking SET parking_address =
        '" + newParkingAddress + "', parking_spots = " + newParkingSpots+ ",
         area_id=" + newAreaIdareaId + "',price= '" + newpriceprice + "',
          opening_hour= " + newOpeningHour+ ",closing_hour=" + closingHour + "',
          google_maps_link= " +newGoogleMapsLInk+ " where parking id="+parkingId+;

       
        try (Statement stmt = dbcon.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Parking updated successfully.");
            } else {
                System.out.println("No parking found with the given parking id.");
            }
        } catch (SQLException e) {
            System.out.println("Error editing parking: " + e.getMessage());
        }  finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
