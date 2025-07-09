package Train;


//RailwayManagementSystem.java
import java.util.Scanner;

public class RailwayManagementSystem {
 private static Scanner scanner = new Scanner(System.in);
 private static UserService userService = new UserService();
 private static AdminService adminService = new AdminService();
 
 private RailwayManagementSystem() {
	 System.out.println("╔══════════════════════════════════════════════╗");
	 System.out.println("║      🚆 RAILWAY MANAGEMENT SYSTEM JAVA APP    ║");
	 System.out.println("╚══════════════════════════════════════════════╝");
	 System.out.println("                  _______________              ");
	 System.out.println("      ______     |               |   ______     ");
	 System.out.println("  ___|[] | []|___|     TRAIN     |__| [] | []|___");
	 System.out.println(" |_______________________________|______________|");
	 System.out.println("    0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0   ");
	 System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
 }
 public static void main(String[] args) {
	 RailwayManagementSystem rm = new RailwayManagementSystem();
//     System.out.println("=== Welcome to Railway Management System ===");
     
     while (true) {
         try {
             displayMainMenu();
             int choice = getIntInput();
             
             switch (choice) {
                 case 1:
                     userLogin();
                     break;
                 case 2:
                     adminLogin();
                     break;
                 case 3:
                     registerUser();
                     break;
                 case 4:
                     System.out.println("Thank you for using Railway Management System!");
                     System.exit(0);
                     break;
                 default:
                     System.out.println("Invalid choice! Please try again.");
             }
         } catch (InvalidInputException e) {
             System.out.println("Error: " + e.getMessage());
         } catch (Exception e) {
             System.out.println("An unexpected error occurred: " + e.getMessage());
         }
     }
 }
 
 private static void displayMainMenu() {
     System.out.println("\n========== MAIN MENU ==========");
     System.out.println("1. User Login");
     System.out.println("2. Admin Login");
     System.out.println("3. Register New User");
     System.out.println("4. Exit");
     System.out.print("Enter your choice: ");
 }
 
 private static void userLogin() throws InvalidInputException {
     System.out.print("Enter username: ");
     String username = scanner.nextLine();
     System.out.print("Enter password: ");
     String password = scanner.nextLine();
     
     try {
         if (userService.login(username, password)) {
             System.out.println("Login successful! Welcome " + username);
             userMenu();
         } else {
             throw new LoginException("Invalid username or password!");
         }
     } catch (LoginException e) {
         System.out.println("Login failed: " + e.getMessage());
     }
 }
 
 private static void adminLogin() throws InvalidInputException {
     System.out.print("Enter admin username: ");
     String username = scanner.nextLine();
     System.out.print("Enter admin password: ");
     String password = scanner.nextLine();
     
     try {
         if (adminService.login(username, password)) {
             System.out.println("Admin login successful! Welcome " + username);
             adminMenu();
         } else {
             throw new LoginException("Invalid admin credentials!");
         }
     } catch (LoginException e) {
         System.out.println("Admin login failed: " + e.getMessage());
     }
 }
 
 private static void registerUser() throws InvalidInputException {
     System.out.print("Enter new username: ");
     String username = scanner.nextLine();
     System.out.print("Enter password: ");
     String password = scanner.nextLine();
     System.out.print("Enter email: ");
     String email = scanner.nextLine();
     System.out.print("Enter phone number: ");
     String phone = scanner.nextLine();
     
     try {
         User newUser = new User(username, password, email, phone);
         userService.registerUser(newUser);
         System.out.println("User registered successfully!");
     } catch (UserAlreadyExistsException e) {
         System.out.println("Registration failed: " + e.getMessage());
     }
 }
 
 private static void userMenu() throws InvalidInputException {
     while (true) {
         System.out.println("\n========== USER MENU ==========");
         System.out.println("1. Search Trains");
         System.out.println("2. Book Ticket");
         System.out.println("3. View My Bookings");
         System.out.println("4. Cancel Booking");
         System.out.println("5. Logout");
         System.out.print("Enter your choice: ");
         
         int choice = getIntInput();
         
         switch (choice) {
             case 1:
                 searchTrains();
                 break;
             case 2:
                 bookTicket();
                 break;
             case 3:
                 viewBookings();
                 break;
             case 4:
                 cancelBooking();
                 break;
             case 5:
                 System.out.println("Logged out successfully!");
                 return;
             default:
                 System.out.println("Invalid choice! Please try again.");
         }
     }
 }
 
 private static void adminMenu() throws InvalidInputException {
     while (true) {
         System.out.println("\n========== ADMIN MENU ==========");
         System.out.println("1. Add New Train");
         System.out.println("2. View All Trains");
         System.out.println("3. Update Train");
         System.out.println("4. Delete Train");
         System.out.println("5. View All Bookings");
         System.out.println("6. View User Statistics");
         System.out.println("7. Logout");
         System.out.print("Enter your choice: ");
         
         int choice = getIntInput();
         
         switch (choice) {
             case 1:
                 addTrain();
                 break;
             case 2:
                 adminService.viewAllTrains();
                 break;
             case 3:
                 updateTrain();
                 break;
             case 4:
                 deleteTrain();
                 break;
             case 5:
                 adminService.viewAllBookings();
                 break;
             case 6:
                 adminService.viewUserStatistics();
                 break;
             case 7:
                 System.out.println("Admin logged out successfully!");
                 return;
             default:
                 System.out.println("Invalid choice! Please try again.");
         }
     }
 }
 
 private static void searchTrains() {
     System.out.print("Enter source station: ");
     String source = scanner.nextLine();
     System.out.print("Enter destination station: ");
     String destination = scanner.nextLine();
     
     userService.searchTrains(source, destination);
 }
 
 private static void bookTicket() throws InvalidInputException {
     System.out.print("Enter train number: ");
     int trainNumber = getIntInput();
     System.out.print("Enter number of passengers: ");
     int passengers = getIntInput();
     
     try {
         userService.bookTicket(trainNumber, passengers);
         System.out.println("Ticket booked successfully!");
     } catch (BookingException e) {
         System.out.println("Booking failed: " + e.getMessage());
     }
 }
 
 private static void viewBookings() {
     userService.viewBookings();
 }
 
 private static void cancelBooking() throws InvalidInputException {
     System.out.print("Enter booking ID to cancel: ");
     int bookingId = getIntInput();
     
     try {
         userService.cancelBooking(bookingId);
         System.out.println("Booking cancelled successfully!");
     } catch (BookingException e) {
         System.out.println("Cancellation failed: " + e.getMessage());
     }
 }
 
 private static void addTrain() throws InvalidInputException {
     System.out.print("Enter train number: ");
     int trainNumber = getIntInput();
     System.out.print("Enter train name: ");
     String trainName = scanner.nextLine();
     System.out.print("Enter source station: ");
     String source = scanner.nextLine();
     System.out.print("Enter destination station: ");
     String destination = scanner.nextLine();
     System.out.print("Enter departure time (HH:MM): ");
     String departureTime = scanner.nextLine();
     System.out.print("Enter arrival time (HH:MM): ");
     String arrivalTime = scanner.nextLine();
     System.out.print("Enter total seats: ");
     int totalSeats = getIntInput();
     System.out.print("Enter fare: ");
     double fare = getDoubleInput();
     
     try {
         Train train = new Train(trainNumber, trainName, source, destination, 
                               departureTime, arrivalTime, totalSeats, fare);
         adminService.addTrain(train);
         System.out.println("Train added successfully!");
     } catch (TrainAlreadyExistsException e) {
         System.out.println("Failed to add train: " + e.getMessage());
     }
 }
 
 private static void updateTrain() throws InvalidInputException {
     System.out.print("Enter train number to update: ");
     int trainNumber = getIntInput();
     
     try {
         adminService.updateTrain(trainNumber);
     } catch (TrainNotFoundException e) {
         System.out.println("Update failed: " + e.getMessage());
     }
 }
 
 private static void deleteTrain() throws InvalidInputException {
     System.out.print("Enter train number to delete: ");
     int trainNumber = getIntInput();
     
     try {
         adminService.deleteTrain(trainNumber);
         System.out.println("Train deleted successfully!");
     } catch (TrainNotFoundException e) {
         System.out.println("Delete failed: " + e.getMessage());
     }
 }
 
 private static int getIntInput() throws InvalidInputException {
     try {
         int input = Integer.parseInt(scanner.nextLine());
         return input;
     } catch (NumberFormatException e) {
         throw new InvalidInputException("Please enter a valid number!");
     }
 }
 
 private static double getDoubleInput() throws InvalidInputException {
     try {
         double input = Double.parseDouble(scanner.nextLine());
         return input;
     } catch (NumberFormatException e) {
         throw new InvalidInputException("Please enter a valid decimal number!");
     }
 }
}
