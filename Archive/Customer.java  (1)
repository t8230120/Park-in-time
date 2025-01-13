import java.sql.*;
import java.util.Scanner;

public class Customer extends User {

    private static Connection dbcon;
    Scanner scanner = new Scanner(System.in);

    public static void Customermain(String[] args) {
        
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        String url = "jdbc:mysql://127.0.0.1:3306/park?useSSL=false&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbcon = DriverManager.getConnection(url, "root", "PROGprog");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }


        int choice = 0;
        do {
            System.out.println("What do you want to do, " + username + "?");
            System.out.println("1. Add car");
            System.out.println("2. Delete car");
            System.out.println("3. Edit car");
            System.out.println("4. Reserve parking");
            System.out.println("5. Cancel parking");
            System.out.println("6. Check active parking");
            System.out.println("7. Exit app");
            System.out.print("Please choose a number from 1-7: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    deleteCar();
                    break;
                case 3:
                    editCar();
                    break;
                case 4:
                    reserveParking();
                    break;
                case 5:
                    cancelParking();
                    break;
                case 6:
                    checkActiveParking();
                    break;
                case 7:
                    System.out.println("Exiting the app. Have a good day!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 7.");
            }
        } while (choice != 7);

        try {
            dbcon.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void addCar() {
        System.out.println("You picked 1.Add Car");

        System.out.print("Enter license plate: ");
        String licensePlate = scanner.nextLine();

        System.out.print("Enter car model: ");
        String carModel = scanner.nextLine();

        System.out.print("Enter car type ID: ");
        int carTypeId = scanner.nextInt();
        scanner.nextLine();

        String query = "INSERT INTO Cars (license_plate, car_model, car_type_id) VALUES ('" 
                + licensePlate + "', '" + carModel + "', " + carTypeId + ")";

        try (Statement stmt = dbcon.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Car added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
    }

    public static void deleteCar() {
        System.out.println("You picked 2.Delete Car");

        System.out.print("Enter the license plate of the car to delete: ");
        String licensePlate = scanner.nextLine();

        String query = "DELETE FROM Cars WHERE license_plate = '" + licensePlate + "'";

        try (Statement stmt = dbcon.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Car deleted successfully.");
            } else {
                System.out.println("No car found with the given license plate.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting car: " + e.getMessage());
        }
    }

    public static void editCar() {
        System.out.println("You picked 3.Edit Car");

        System.out.print("Enter the license plate of the car to edit: ");
        String licensePlate = scanner.nextLine();

        System.out.print("Enter the new car model: ");
        String newModel = scanner.nextLine();

        System.out.print("Enter the new car type ID: ");
        int newCarTypeId = scanner.nextInt();
        scanner.nextLine();

        String query = "UPDATE Cars SET car_model = '" + newModel + "', car_type_id = " 
                + newCarTypeId + " WHERE license_plate = '" + licensePlate + "'";

        try (Statement stmt = dbcon.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Car updated successfully.");
            } else {
                System.out.println("No car found with the given license plate.");
            }
        } catch (SQLException e) {
            System.out.println("Error editing car: " + e.getMessage());
        }
    }

    public static void reserveParking() {
        System.out.println("You picked 4.Reserve Parking");

        try {
            
            String areaQuery = "SELECT area_id, area_name FROM Areas";
            try (Statement stmt = dbcon.createStatement(); ResultSet rs = stmt.executeQuery(areaQuery)) {
                System.out.println("Available Areas:");
                while (rs.next()) {
                    int areaId = rs.getInt("area_id");
                    String areaName = rs.getString("area_name");
                    System.out.println(areaId + ". " + areaName);
                }
            }

            System.out.print("Enter the area ID you want to park in: ");
            int areaId = scanner.nextInt();
            scanner.nextLine();

            String parkingQuery = "SELECT parking_id, parking_address, price FROM Parking WHERE area_id = " + areaId;
            try (Statement stmt = dbcon.createStatement(); ResultSet rs = stmt.executeQuery(parkingQuery)) {
                System.out.println("Available Parkings:");
                while (rs.next()) {
                    String parkingId = rs.getString("parking_id");
                    String parkingAddress = rs.getString("parking_address");
                    double price = rs.getDouble("price");
                    System.out.println(parkingId + ". Address: " + parkingAddress + ", Price per hour: " + price);
                }
            }

            System.out.print("Enter the parking ID you want to reserve: ");
            String parkingId = scanner.nextLine();

            System.out.print("Enter your license plate: ");
            String licensePlate = scanner.nextLine();

            System.out.print("Enter arrival time (YYYY-MM-DD HH:MM:SS): ");
            String arrivalTime = scanner.nextLine();

            System.out.print("Enter the number of hours you will stay: ");
            int stayDuration = scanner.nextInt();
            scanner.nextLine();

            String priceQuery = "SELECT price FROM Parking WHERE parking_id = '" + parkingId + "'";
            double pricePerHour = 0;
            try (Statement stmt = dbcon.createStatement(); ResultSet rs = stmt.executeQuery(priceQuery)) {
                if (rs.next()) {
                    pricePerHour = rs.getDouble("price");
                } else {
                    System.out.println("Invalid parking ID.");
                    return;
                }
            }

            double totalPrice = pricePerHour * stayDuration;

            String insertQuery = "INSERT INTO Reservation (license_plate, parking_id, reservation_date) VALUES ('"
                    + licensePlate + "', '" + parkingId + "', '" + arrivalTime + "')";
            try (Statement stmt = dbcon.createStatement()) {
                stmt.executeUpdate(insertQuery);
                System.out.println("Parking reserved successfully. Price: $" + totalPrice);
                System.out.println("You will pay at the parking location.");
            }

        } catch (SQLException e) {
            System.out.println("Error during reservation: " + e.getMessage());
        }
    }


    public static void cancelParking() {
        System.out.println("You picked 5.Cancel Parking");

        System.out.print("Enter your reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        String query = "UPDATE Reservation SET is_active = FALSE WHERE reservation_id = " + reservationId;

        try (Statement stmt = dbcon.createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Parking reservation canceled successfully.");
            } else {
                System.out.println("No active reservation found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error canceling reservation: " + e.getMessage());
        }
    }

    public static void checkActiveParking() {
        System.out.println("You picked 6.Check Active Parking");

        System.out.print("Enter your license plate: ");
        String licensePlate = scanner.nextLine();

        String query = "SELECT * FROM Reservation WHERE license_plate = '" 
                + licensePlate + "' AND is_active = TRUE";

        try (Statement stmt = dbcon.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                System.out.println("Active Parking Details:");
                System.out.println("Reservation ID: " + rs.getInt("reservation_id"));
                System.out.println("Parking ID: " + rs.getString("parking_id"));
                System.out.println("Reservation Date: " + rs.getTimestamp("reservation_date"));
            } else {
                System.out.println("No active parking found for this license plate.");
            }
        } catch (SQLException e) {
            System.out.println("Error checking active parking: " + e.getMessage());
        }
    }
}
