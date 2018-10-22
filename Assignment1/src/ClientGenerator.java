/**
 * This class is responsible to create a new Client. Every new Client need to
 * know the IP address, port number, the number of users who send a request and
 * the number of requests that each user send. These need to give as arguments.
 * 
 */

public class ClientGenerator {

	public static void main(String args[]) {

		// default values
		int clients = 10, requests = 300, repetitions = 1;

		// checks the arguments
		if (args.length == 0) {
			System.out.println("You have to give server's IP and port! ");
			return;
		} else if (args.length == 3) {
			repetitions = Integer.parseInt(args[2]);
		}

		for (int j = 1; j <= repetitions; j++) {
			for (int i = 1; i <= clients; i++) {
				new Client(i, args[0], Integer.parseInt(args[1]), requests).start();
			}
		}
	}

}
