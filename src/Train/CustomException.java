package Train;

//Custom Exception Classes

//InvalidInputException.java
class InvalidInputException extends Exception {
 public InvalidInputException(String message) {
     super(message);
 }
}

//LoginException.java
class LoginException extends Exception {
 public LoginException(String message) {
     super(message);
 }
}

//UserAlreadyExistsException.java
class UserAlreadyExistsException extends Exception {
 public UserAlreadyExistsException(String message) {
     super(message);
 }
}

//TrainNotFoundException.java
class TrainNotFoundException extends Exception {
 public TrainNotFoundException(String message) {
     super(message);
 }
}

//TrainAlreadyExistsException.java
class TrainAlreadyExistsException extends Exception {
 public TrainAlreadyExistsException(String message) {
     super(message);
 }
}

//BookingException.java
class BookingException extends Exception {
 public BookingException(String message) {
     super(message);
 }
}

//InsufficientSeatsException.java
class InsufficientSeatsException extends BookingException {
 public InsufficientSeatsException(String message) {
     super(message);
 }
}