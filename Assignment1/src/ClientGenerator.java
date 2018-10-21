/**
 * This class is responsible to create a new Client. Every new Client need to
 * know the IP address, port number, the number of users who send a request and
 * the number of requests that each user send. These need to give as arguments.
 * 
 */

public class ClientGenerator {

	public static void main(String args[]) {

		// default value for number of users
		int N = 10;
		
		// default value for number of requests
		int repetitions = 300;

		// checks the arguments
		if (args.length == 0) {
			System.out.println("You have to give server's ip and port! ");
			return;
		} else if (args.length == 4) {
			N = Integer.parseInt(args[2]);
			repetitions = Integer.parseInt(args[3]);
		}

		// create new clients
		for (int i = 1; i <= N; i++) {
			new Client(i, args[0], Integer.parseInt(args[1]), repetitions).start();
		}

	}

}
