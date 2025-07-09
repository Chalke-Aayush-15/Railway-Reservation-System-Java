package Train;

public class userinput{

	public static void main(String[] args) {
		String user_name="aayush";
		
		try {
			if (user_name.length() < 5) {
				System.out.println("Valid user");
			}
			else {
				throw new userexception("This is user invalid exception");
			}
		}
		catch(userexception e) {
			System.out.println("Fail");
		}
	}
}
