package Train;

//User.java
class User {
 private String username;
 private String password;
 private String email;
 private String phoneNumber;
 
 // Constructor
 public User(String username, String password, String email, String phoneNumber) {
     this.username = username;
     this.password = password;
     this.email = email;
     this.phoneNumber = phoneNumber;
 }
 
 // Getters and Setters
 public String getUsername() { return username; }
 public void setUsername(String username) { this.username = username; }
 
 public String getPassword() { return password; }
 public void setPassword(String password) { this.password = password; }
 
 public String getEmail() { return email; }
 public void setEmail(String email) { this.email = email; }
 
 public String getPhoneNumber() { return phoneNumber; }
 public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
 
 @Override
 public String toString() {
     return "User{username='" + username + "', email='" + email + 
            "', phone='" + phoneNumber + "'}";
 }
}

//Train.java
class Train {
 private int trainNumber;
 private String trainName;
 private String source;
 private String destination;
 private String departureTime;
 private String arrivalTime;
 private int totalSeats;
 private int availableSeats;
 private double fare;
 
 // Constructor
 public Train(int trainNumber, String trainName, String source, String destination,
             String departureTime, String arrivalTime, int totalSeats, double fare) {
     this.trainNumber = trainNumber;
     this.trainName = trainName;
     this.source = source;
     this.destination = destination;
     this.departureTime = departureTime;
     this.arrivalTime = arrivalTime;
     this.totalSeats = totalSeats;
     this.availableSeats = totalSeats; // Initially all seats are available
     this.fare = fare;
 }
 
 // Getters and Setters
 public int getTrainNumber() { return trainNumber; }
 public void setTrainNumber(int trainNumber) { this.trainNumber = trainNumber; }
 
 public String getTrainName() { return trainName; }
 public void setTrainName(String trainName) { this.trainName = trainName; }
 
 public String getSource() { return source; }
 public void setSource(String source) { this.source = source; }
 
 public String getDestination() { return destination; }
 public void setDestination(String destination) { this.destination = destination; }
 
 public String getDepartureTime() { return departureTime; }
 public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
 
 public String getArrivalTime() { return arrivalTime; }
 public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
 
 public int getTotalSeats() { return totalSeats; }
 public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
 
 public int getAvailableSeats() { return availableSeats; }
 public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
 
 public double getFare() { return fare; }
 public void setFare(double fare) { this.fare = fare; }
 
 // Method to book seats
 public boolean bookSeats(int numberOfSeats) throws InsufficientSeatsException {
     if (availableSeats >= numberOfSeats) {
         availableSeats -= numberOfSeats;
         return true;
     } else {
         throw new InsufficientSeatsException("Only " + availableSeats + " seats available!");
     }
 }
 
 // Method to cancel seats (make them available again)
 public void cancelSeats(int numberOfSeats) {
     availableSeats += numberOfSeats;
     if (availableSeats > totalSeats) {
         availableSeats = totalSeats;
     }
 }
 
 @Override
 public String toString() {
     return String.format("Train %d: %s | %s -> %s | Dep: %s | Arr: %s | Available: %d/%d | Fare: $%.2f",
             trainNumber, trainName, source, destination, departureTime, 
             arrivalTime, availableSeats, totalSeats, fare);
 }
}

//Booking.java
class Booking {
 private static int bookingCounter = 1000; // Static counter for unique booking IDs
 private int bookingId;
 private String username;
 private int trainNumber;
 private String trainName;
 private int numberOfPassengers;
 private double totalFare;
 private String bookingDate;
 private String status; // CONFIRMED, CANCELLED
 
 // Constructor
 public Booking(String username, int trainNumber, String trainName, 
               int numberOfPassengers, double farePerPerson) {
     this.bookingId = ++bookingCounter;
     this.username = username;
     this.trainNumber = trainNumber;
     this.trainName = trainName;
     this.numberOfPassengers = numberOfPassengers;
     this.totalFare = farePerPerson * numberOfPassengers;
     this.bookingDate = java.time.LocalDate.now().toString();
     this.status = "CONFIRMED";
 }
 
 // Getters and Setters
 public int getBookingId() { return bookingId; }
 
 public String getUsername() { return username; }
 public void setUsername(String username) { this.username = username; }
 
 public int getTrainNumber() { return trainNumber; }
 public void setTrainNumber(int trainNumber) { this.trainNumber = trainNumber; }
 
 public String getTrainName() { return trainName; }
 public void setTrainName(String trainName) { this.trainName = trainName; }
 
 public int getNumberOfPassengers() { return numberOfPassengers; }
 public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }
 
 public double getTotalFare() { return totalFare; }
 public void setTotalFare(double totalFare) { this.totalFare = totalFare; }
 
 public String getBookingDate() { return bookingDate; }
 public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
 
 public String getStatus() { return status; }
 public void setStatus(String status) { this.status = status; }
 
 // Method to cancel booking
 public void cancelBooking() {
     this.status = "CANCELLED";
 }
 
 @Override
 public String toString() {
     return String.format("Booking ID: %d | Train: %d (%s) | Passengers: %d | Total Fare: $%.2f | Date: %s | Status: %s",
             bookingId, trainNumber, trainName, numberOfPassengers, totalFare, bookingDate, status);
 }
}