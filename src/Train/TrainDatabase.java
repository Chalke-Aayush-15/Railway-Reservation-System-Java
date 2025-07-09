package Train;

//TrainDatabase.java
import java.util.*;

class TrainDatabase {
 private static Map<Integer, Train> trains = new HashMap<>();
 
 // Static block to initialize some sample trains
 static {
     trains.put(12345, new Train(12345, "Shatabdi Express", "Delhi", "Mumbai", 
                                "06:00", "20:30", 100, 1500.0));
     trains.put(12347, new Train(12347, "Duronto Express", "Mumbai", "Chennai", 
                                "22:15", "11:45", 120, 1200.0));
 }
 
 // Method to add a new train
 public static void addTrain(Train train) throws TrainAlreadyExistsException {
     if (trains.containsKey(train.getTrainNumber())) {
         throw new TrainAlreadyExistsException("Train with number " + 
                                             train.getTrainNumber() + " already exists!");
     }
     
     // Validate train data
     validateTrainData(train);
     
     trains.put(train.getTrainNumber(), train);
 }
 
 // Method to get train by number
 public static Train getTrainByNumber(int trainNumber) {
     return trains.get(trainNumber);
 }
 
 // Method to get all trains
 public static List<Train> getAllTrains() {
     return new ArrayList<>(trains.values());
 }
 
 // Method to search trains by source and destination
 public static List<Train> searchTrains(String source, String destination) {
     List<Train> result = new ArrayList<>();
     
     for (Train train : trains.values()) {
         if (train.getSource().equalsIgnoreCase(source) && 
             train.getDestination().equalsIgnoreCase(destination)) {
             result.add(train);
         }
     }
     
     // Sort by departure time
     result.sort((t1, t2) -> t1.getDepartureTime().compareTo(t2.getDepartureTime()));
     
     return result;
 }
 
 // Method to delete a train
 public static void deleteTrain(int trainNumber) throws TrainNotFoundException {
     if (!trains.containsKey(trainNumber)) {
         throw new TrainNotFoundException("Train with number " + trainNumber + " not found!");
     }
     
     trains.remove(trainNumber);
 }
 
 // Method to update train (not needed as we directly modify the object)
 public static void updateTrain(Train updatedTrain) {
     trains.put(updatedTrain.getTrainNumber(), updatedTrain);
 }
 
 // Method to search trains by route (partial match)
 public static List<Train> searchTrainsByRoute(String searchTerm) {
     List<Train> result = new ArrayList<>();
     
     for (Train train : trains.values()) {
         if (train.getSource().toLowerCase().contains(searchTerm.toLowerCase()) ||
             train.getDestination().toLowerCase().contains(searchTerm.toLowerCase()) ||
             train.getTrainName().toLowerCase().contains(searchTerm.toLowerCase())) {
             result.add(train);
         }
     }
     
     return result;
 }
 
 // Method to get trains with available seats
 public static List<Train> getTrainsWithAvailableSeats() {
     List<Train> result = new ArrayList<>();
     
     for (Train train : trains.values()) {
         if (train.getAvailableSeats() > 0) {
             result.add(train);
         }
     }
     
     return result;
 }
 
 // Method to get popular routes (most booked trains)
 public static Map<String, Integer> getPopularRoutes() {
     Map<String, Integer> routeCount = new HashMap<>();
     
     for (Train train : trains.values()) {
         String route = train.getSource() + " -> " + train.getDestination();
         int bookedSeats = train.getTotalSeats() - train.getAvailableSeats();
         routeCount.put(route, routeCount.getOrDefault(route, 0) + bookedSeats);
     }
     
     return routeCount;
 }
 
 // Utility method to validate train data
 private static void validateTrainData(Train train) throws TrainAlreadyExistsException {
     if (train.getTrainNumber() <= 0) {
         throw new TrainAlreadyExistsException("Train number must be positive!");
     }
     
     if (train.getTrainName() == null || train.getTrainName().trim().isEmpty()) {
         throw new TrainAlreadyExistsException("Train name cannot be empty!");
     }
     
     if (train.getSource() == null || train.getSource().trim().isEmpty()) {
         throw new TrainAlreadyExistsException("Source station cannot be empty!");
     }
     
     if (train.getDestination() == null || train.getDestination().trim().isEmpty()) {
         throw new TrainAlreadyExistsException("Destination station cannot be empty!");
     }
     
     if (train.getSource().equalsIgnoreCase(train.getDestination())) {
         throw new TrainAlreadyExistsException("Source and destination cannot be the same!");
     }
     
     if (train.getTotalSeats() <= 0) {
         throw new TrainAlreadyExistsException("Total seats must be positive!");
     }
     
     if (train.getFare() <= 0) {
         throw new TrainAlreadyExistsException("Fare must be positive!");
     }
     
     // Validate time format (basic validation)
     if (!isValidTimeFormat(train.getDepartureTime()) || 
         !isValidTimeFormat(train.getArrivalTime())) {
         throw new TrainAlreadyExistsException("Invalid time format! Use HH:MM format.");
     }
 }
 
 // Utility method to validate time format
 private static boolean isValidTimeFormat(String time) {
     if (time == null || !time.matches("\\d{1,2}:\\d{2}")) {
         return false;
     }
     
     String[] parts = time.split(":");
     try {
         int hours = Integer.parseInt(parts[0]);
         int minutes = Integer.parseInt(parts[1]);
         
         return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
     } catch (NumberFormatException e) {
         return false;
     }
 }
 
 // Method to get database statistics
 public static Map<String, Object> getDatabaseStatistics() {
     Map<String, Object> stats = new HashMap<>();
     
     stats.put("Total Trains", trains.size());
     stats.put("Total Capacity", trains.values().stream()
                                       .mapToInt(Train::getTotalSeats)
                                       .sum());
     stats.put("Available Seats", trains.values().stream()
                                        .mapToInt(Train::getAvailableSeats)
                                        .sum());
     
     // Get unique stations
     Set<String> stations = new HashSet<>();
     for (Train train : trains.values()) {
         stations.add(train.getSource());
         stations.add(train.getDestination());
     }
     stats.put("Total Stations", stations.size());
     
     return stats;
 }
}