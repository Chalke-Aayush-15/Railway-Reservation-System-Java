package Train;

//AdminService.java
import java.util.*;

class AdminService implements AdminInterface {
 private static Map<String, String> adminCredentials = new HashMap<>();
 private static String currentAdmin = null;
 private Scanner scanner = new Scanner(System.in);
 
 // Static block to initialize admin credentials
 static {
     adminCredentials.put("admin", "admin123");
     adminCredentials.put("railway_admin", "railway@2024");
 }
 
 @Override
 public boolean login(String username, String password) throws LoginException {
     if (adminCredentials.containsKey(username) && 
         adminCredentials.get(username).equals(password)) {
         currentAdmin = username;
         return true;
     }
     return false;
 }
 
 @Override
 public void logout() {
     currentAdmin = null;
     System.out.println("Admin logged out successfully!");
 }
 
 @Override
 public void displayMenu() {
     System.out.println("\n========== ADMIN MENU ==========");
     System.out.println("1. Add New Train");
     System.out.println("2. View All Trains");
     System.out.println("3. Update Train");
     System.out.println("4. Delete Train");
     System.out.println("5. View All Bookings");
     System.out.println("6. View User Statistics");
     System.out.println("7. Logout");
 }
 
 @Override
 public void addTrain(Train train) throws TrainAlreadyExistsException {
     if (currentAdmin == null) {
         System.out.println("Admin not logged in!");
         return;
     }
     
     TrainDatabase.addTrain(train);
 }
 
 @Override
 public void viewAllTrains() {
     if (currentAdmin == null) {
         System.out.println("Admin not logged in!");
         return;
     }
     
     List<Train> allTrains = TrainDatabase.getAllTrains();
     if (allTrains.isEmpty()) {
         System.out.println("No trains in the system!");
     } else {
         System.out.println("\n========== ALL TRAINS ==========");
         for (Train train : allTrains) {
             System.out.println(train);
         }
     }
 }
 
 @Override
 public void updateTrain(int trainNumber) throws TrainNotFoundException {
     if (currentAdmin == null) {
         System.out.println("Admin not logged in!");
         return;
     }
     
     Train train = TrainDatabase.getTrainByNumber(trainNumber);
     if (train == null) {
         throw new TrainNotFoundException("Train with number " + trainNumber + " not found!");
     }
     
     System.out.println("Current train details: " + train);
     System.out.println("\nWhat would you like to update?");
     System.out.println("1. Train Name");
     System.out.println("2. Source Station");
     System.out.println("3. Destination Station");
     System.out.println("4. Departure Time");
     System.out.println("5. Arrival Time");
     System.out.println("6. Total Seats");
     System.out.println("7. Fare");
     System.out.print("Enter your choice: ");
     
     try {
         int choice = Integer.parseInt(scanner.nextLine());
         
         switch (choice) {
             case 1:
                 System.out.print("Enter new train name: ");
                 String newName = scanner.nextLine();
                 train.setTrainName(newName);
                 break;
             case 2:
                 System.out.print("Enter new source station: ");
                 String newSource = scanner.nextLine();
                 train.setSource(newSource);
                 break;
             case 3:
                 System.out.print("Enter new destination station: ");
                 String newDestination = scanner.nextLine();
                 train.setDestination(newDestination);
                 break;
             case 4:
                 System.out.print("Enter new departure time (HH:MM): ");
                 String newDepartureTime = scanner.nextLine();
                 train.setDepartureTime(newDepartureTime);
                 break;
             case 5:
                 System.out.print("Enter new arrival time (HH:MM): ");
                 String newArrivalTime = scanner.nextLine();
                 train.setArrivalTime(newArrivalTime);
                 break;
             case 6:
                 System.out.print("Enter new total seats: ");
                 int newTotalSeats = Integer.parseInt(scanner.nextLine());
                 int bookedSeats = train.getTotalSeats() - train.getAvailableSeats();
                 if (newTotalSeats < bookedSeats) {
                     System.out.println("Cannot reduce seats below already booked seats (" + bookedSeats + ")");
                     return;
                 }
                 train.setTotalSeats(newTotalSeats);
                 train.setAvailableSeats(newTotalSeats - bookedSeats);
                 break;
             case 7:
                 System.out.print("Enter new fare: ");
                 double newFare = Double.parseDouble(scanner.nextLine());
                 train.setFare(newFare);
                 break;
             default:
                 System.out.println("Invalid choice!");
                 return;
         }
         
         System.out.println("Train updated successfully!");
         System.out.println("Updated details: " + train);
         
     } catch (NumberFormatException e) {
         System.out.println("Invalid input format!");
     }
 }
 
 @Override
 public void deleteTrain(int trainNumber) throws TrainNotFoundException {
     if (currentAdmin == null) {
         System.out.println("Admin not logged in!");
         return;
     }
     
     Train train = TrainDatabase.getTrainByNumber(trainNumber);
     if (train == null) {
         throw new TrainNotFoundException("Train with number " + trainNumber + " not found!");
     }
     
     // Check if there are any confirmed bookings for this train
     List<Booking> allBookings = UserService.getAllBookings();
     boolean hasConfirmedBookings = false;
     for (Booking booking : allBookings) {
         if (booking.getTrainNumber() == trainNumber && booking.getStatus().equals("CONFIRMED")) {
             hasConfirmedBookings = true;
             break;
         }
     }
     
     if (hasConfirmedBookings) {
         System.out.println("Cannot delete train - it has confirmed bookings!");
         System.out.print("Do you want to cancel all bookings and delete the train? (y/n): ");
         String choice = scanner.nextLine();
         
         if (choice.equalsIgnoreCase("y")) {
             // Cancel all bookings for this train
             for (Booking booking : allBookings) {
                 if (booking.getTrainNumber() == trainNumber && booking.getStatus().equals("CONFIRMED")) {
                     booking.cancelBooking();
                 }
             }
             TrainDatabase.deleteTrain(trainNumber);
             System.out.println("All bookings cancelled and train deleted successfully!");
         } else {
             System.out.println("Train deletion cancelled!");
         }
     } else {
         TrainDatabase.deleteTrain(trainNumber);
     }
 }
 
 @Override
 public void viewAllBookings() {
     if (currentAdmin == null) {
         System.out.println("Admin not logged in!");
         return;
     }
     
     List<Booking> allBookings = UserService.getAllBookings();
     if (allBookings.isEmpty()) {
         System.out.println("No bookings found!");
     } else {
         System.out.println("\n========== ALL BOOKINGS ==========");
         for (Booking booking : allBookings) {
             System.out.println(booking);
         }
         
         // Show summary
         double totalRevenue = 0;
         int confirmedBookings = 0;
         int cancelledBookings = 0;
         
         for (Booking booking : allBookings) {
             if (booking.getStatus().equals("CONFIRMED")) {
                 totalRevenue += booking.getTotalFare();
                 confirmedBookings++;
             } else {
                 cancelledBookings++;
             }
         }
         
         System.out.println("\n========== BOOKING SUMMARY ==========");
         System.out.println("Total Bookings: " + allBookings.size());
         System.out.println("Confirmed Bookings: " + confirmedBookings);
         System.out.println("Cancelled Bookings: " + cancelledBookings);
         System.out.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
     }
 }
 
 @Override
 public void viewUserStatistics() {
     if (currentAdmin == null) {
         System.out.println("Admin not logged in!");
         return;
     }
     
     Map<String, Integer> stats = UserService.getUserStatistics();
     
     System.out.println("\n========== USER STATISTICS ==========");
     for (Map.Entry<String, Integer> entry : stats.entrySet()) {
         System.out.println(entry.getKey() + ": " + entry.getValue());
     }
     
     // Additional train statistics
     List<Train> allTrains = TrainDatabase.getAllTrains();
     System.out.println("Total Trains: " + allTrains.size());
     
     int totalSeats = 0;
     int availableSeats = 0;
     for (Train train : allTrains) {
         totalSeats += train.getTotalSeats();
         availableSeats += train.getAvailableSeats();
     }
     
     System.out.println("Total Seats in System: " + totalSeats);
     System.out.println("Available Seats: " + availableSeats);
     System.out.println("Occupancy Rate: " + 
                      String.format("%.2f%%", ((totalSeats - availableSeats) * 100.0 / totalSeats)));
 }
 
 // Method to add new admin (for super admin functionality)
 public void addNewAdmin(String username, String password) {
     if (currentAdmin != null) {
         adminCredentials.put(username, password);
         System.out.println("New admin added successfully!");
     } else {
         System.out.println("Only logged-in admins can add new admins!");
     }
 }
}