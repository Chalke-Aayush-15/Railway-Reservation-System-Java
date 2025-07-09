package Train;

//UserInterface.java
public interface UserInterface {
 boolean login(String username, String password) throws LoginException;
 void logout();
 void displayMenu();
}

//AdminInterface.java  
interface AdminInterface extends UserInterface {
 void addTrain(Train train) throws TrainAlreadyExistsException;
 void deleteTrain(int trainNumber) throws TrainNotFoundException;
 void updateTrain(int trainNumber) throws TrainNotFoundException;
 void viewAllTrains();
 void viewAllBookings();
 void viewUserStatistics();
}

//BookingInterface.java
interface BookingInterface {
 void bookTicket(int trainNumber, int passengers) throws BookingException;
 void cancelBooking(int bookingId) throws BookingException;
 void viewBookings();
}

//SearchInterface.java
interface SearchInterface {
 void searchTrains(String source, String destination);
}
