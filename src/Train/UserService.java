package Train;

//UserService.java
import java.util.*;

class UserService implements UserInterface, BookingInterface, SearchInterface {
 private static Map<String, User> users = new HashMap<>();
 private static List<Booking> userBookings = new ArrayList<>();
 private static String currentUser = null;
 
 @Override
 public boolean login(String username, String password) throws LoginException {
     User user = users.get(username);
     if (user != null && user.getPassword().equals(password)) {
         currentUser = username;
         return true;
     }
     return false;
 }
 
 @Override
 public void logout() {
     currentUser = null;
     System.out.println("User logged out successfully!");
 }
 
 @Override
 public void displayMenu() {
     System.out.println("\n========== USER MENU ==========");
     System.out.println("1. Search Trains");
     System.out.println("2. Book Ticket");
     System.out.println("3. View My Bookings");
     System.out.println("4. Cancel Booking");
     System.out.println("5. Logout");
 }
 
 public void registerUser(User user) throws UserAlreadyExistsException {
     if (users.containsKey(user.getUsername())) {
         throw new UserAlreadyExistsException("Username already exists!");
     }
     
     // Validate email format
     if (!isValidEmail(user.getEmail())) {
         throw new UserAlreadyExistsException("Invalid email format!");
     }
     
     // Validate phone number
     if (!isValidPhone(user.getPhoneNumber())) {
         throw new UserAlreadyExistsException("Invalid phone number format!");
     }
     
     users.put(user.getUsername(), user);
 }
 
 @Override
 public void searchTrains(String source, String destination) {
     List<Train> availableTrains = TrainDatabase.searchTrains(source, destination);
     
     if (availableTrains.isEmpty()) {
         System.out.println("No trains found for the given route!");
     } else {
         System.out.println("\n========== AVAILABLE TRAINS ==========");
         for (Train train : availableTrains) {
             System.out.println(train);
         }
     }
 }
 
 @Override
 public void bookTicket(int trainNumber, int passengers) throws BookingException {
     if (currentUser == null) {
         throw new BookingException("Please login first!");
     }
     
     Train train = TrainDatabase.getTrainByNumber(trainNumber);
     if (train == null) {
         throw new BookingException("Train not found!");
     }
     
     if (passengers <= 0) {
         throw new BookingException("Number of passengers must be greater than 0!");
     }
     
     try {
         train.bookSeats(passengers);
         
         Booking booking = new Booking(currentUser, trainNumber, train.getTrainName(), 
                                     passengers, train.getFare());
         userBookings.add(booking);
         
         System.out.println("Booking Details:");
         System.out.println(booking);
         
     } catch (InsufficientSeatsException e) {
         throw new BookingException(e.getMessage());
     }
 }
 
 @Override
 public void viewBookings() {
     if (currentUser == null) {
         System.out.println("Please login first!");
         return;
     }
     
     List<Booking> myBookings = new ArrayList<>();
     for (Booking booking : userBookings) {
         if (booking.getUsername().equals(currentUser)) {
             myBookings.add(booking);
         }
     }
     
     if (myBookings.isEmpty()) {
         System.out.println("You have no bookings!");
     } else {
         System.out.println("\n========== YOUR BOOKINGS ==========");
         for (Booking booking : myBookings) {
             System.out.println(booking);
         }
     }
 }
 
 @Override
 public void cancelBooking(int bookingId) throws BookingException {
     if (currentUser == null) {
         throw new BookingException("Please login first!");
     }
     
     Booking bookingToCancel = null;
     for (Booking booking : userBookings) {
         if (booking.getBookingId() == bookingId && 
             booking.getUsername().equals(currentUser) && 
             booking.getStatus().equals("CONFIRMED")) {
             bookingToCancel = booking;
             break;
         }
     }
     
     if (bookingToCancel == null) {
         throw new BookingException("Booking not found or already cancelled!");
     }
     
     // Cancel the booking
     bookingToCancel.cancelBooking();
     
     // Make seats available again
     Train train = TrainDatabase.getTrainByNumber(bookingToCancel.getTrainNumber());
     if (train != null) {
         train.cancelSeats(bookingToCancel.getNumberOfPassengers());
     }
     
     System.out.println("Booking cancelled successfully!");
     System.out.println("Refund amount: $" + bookingToCancel.getTotalFare());
 }
 
 // Utility methods
 private boolean isValidEmail(String email) {
     return email != null && email.contains("@") && email.contains(".");
 }
 
 private boolean isValidPhone(String phone) {
     return phone != null && phone.length() >= 10 && phone.matches("\\d+");
 }
 
 // Static method to get all bookings (for admin access)
 public static List<Booking> getAllBookings() {
     return new ArrayList<>(userBookings);
 }
 
 // Static method to get user statistics
 public static Map<String, Integer> getUserStatistics() {
     Map<String, Integer> stats = new HashMap<>();
     stats.put("Total Users", users.size());
     stats.put("Total Bookings", userBookings.size());
     
     int confirmedBookings = 0;
     int cancelledBookings = 0;
     for (Booking booking : userBookings) {
         if (booking.getStatus().equals("CONFIRMED")) {
             confirmedBookings++;
         } else {
             cancelledBookings++;
         }
     }
     stats.put("Confirmed Bookings", confirmedBookings);
     stats.put("Cancelled Bookings", cancelledBookings);
     
     return stats;
 }
}