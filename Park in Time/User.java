import java.sql.*;
import java.util.Scanner;

public class User {

    protected static Connection dbcon;
    private static final String dbName = "park";
    private static final String dbUser = "root";
    private static final String dbPassword = "PROGprog";

    public static void main(String[] args) {
        initializeDatabaseConnection();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Park in Time!");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        if (authenticateUser(scanner, username)) {
            String userType = getUserType(username);
            if ("employee".equalsIgnoreCase(userType)) {
                Employee.main(null);
            } else if ("parking_manager".equalsIgnoreCase(userType)) {
                ParkingManager.main(null);
            } else {
                System.out.println("Unknown user type. Exiting the application.");
            }
        }

        closeDatabaseConnection();
    }

    private static void initializeDatabaseConnection() {
        String url = "jdbc:mysql://127.0.0.1:3306/" + dbName + "?user=" + dbUser + "&password=" + dbPassword;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbcon = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            System.exit(0);
        }
    }

    private static void closeDatabaseConnection() {
        try {
            if (dbcon != null) dbcon.close();
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    private static boolean authenticateUser(Scanner scanner, String username) {
        String query = "SELECT username, user_password FROM Users WHERE username = '" + username + "'";
        try (Statement stmt = dbcon.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                for (int attempts = 2; attempts >= 0; attempts--) {
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (password.equals(rs.getString("user_password"))) {
                        System.out.println("Login successful.");
                        return true;
                    }

                    if (attempts > 0) {
                        System.out.println("Incorrect password. You have " + attempts + " attempt(s) left.");
                    } else {
                        System.out.println("Maximum attempts reached. Exiting the application.");
                        return false;
                    }
                }
            } else {
                System.out.println("Username not found. Would you like to create a new profile? (yes/no)");
                String choice = scanner.nextLine().trim().toLowerCase();

                if ("yes".equals(choice)) {
                    createNewProfile(scanner, username);
                    return true;
                } else {
                    System.out.println("Exiting the application.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }

        return false;
    }

    private static void createNewProfile(Scanner scanner, String username) {
        System.out.println("Creating a new profile for username: " + username);

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Are you an 'employee' or a 'parking_manager'? ");
        String userType = scanner.nextLine().trim().toLowerCase();

        String insertQuery = "INSERT INTO Users (username, user_password, email, phone_number, userType) VALUES ('"
                + username + "', '" + password + "', '" + email + "', '" + phoneNumber + "', '" + userType + "')";

        try (Statement stmt = dbcon.createStatement()) {
            int rowsAffected = stmt.executeUpdate(insertQuery);
            if (rowsAffected > 0) {
                System.out.println("Profile created successfully.");
            } else {
                System.out.println("Failed to create profile. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating profile: " + e.getMessage());
        }
    }

    private static String getUserType(String username) {
        String query = "SELECT userType FROM Users WHERE username = '" + username + "'";
        try (Statement stmt = dbcon.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getString("userType");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user type: " + e.getMessage());
        }

        return null;
    }
}
