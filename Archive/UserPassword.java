import java.util.Scanner;
import java.util.regex.Pattern;

public class UserPassword {
    
    // Method to put password requirements
    public static boolean passwordRequirements(String password) {
        // Password must have at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long.
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{}|;:'\",.<>?/]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    // Method to check if the entered password matches the stored password
    public static boolean confirmPassword(String enteredPassword, String storedPassword) {
        return enteredPassword.equals(storedPassword);
    }

    // Method to reset password
    public static String resetPassword() {
        Scanner scanner = new Scanner(System.in);
        String newPassword;
        while (true) {
            System.out.print("Enter a new password: ");
            newPassword = scanner.nextLine();
            if (passwordRequirements(newPassword)) {
                System.out.println("Password reset successful.");
                break;
            } else {
                System.out.println("Invalid password. Please follow the password requirements.");
            }
        }
        return newPassword;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String storedPassword = "Example@123";
        String enteredPassword;

        System.out.println("Welcome to the Password Manager!");

        // Password Confirmation Process
        System.out.print("Enter your password: ");
        enteredPassword = scanner.nextLine();
        
        if (confirmPassword(enteredPassword, storedPassword)) {
            System.out.println("Password is correct!");
        } else {
            System.out.println("Incorrect password. Please try again.");
        }

        // Password Reset Process
        System.out.print("Do you want to reset your password? (yes/no): ");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            storedPassword = resetPassword();  // Call for password reset
        }
        
        scanner.close();
    }
}
