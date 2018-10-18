
public class ClientGenerator {

	public static void main(String args[]) {

		// default value
		int N = 10, repetitions = 300;

		if (args.length == 0) {
			System.out.println("You have to give server's ip and port! ");
			return;
		} else if (args.length == 4) {
			N = Integer.parseInt(args[2]);
			repetitions = Integer.parseInt(args[3]);
		}

		for (int i = 1; i <= N; i++) {
			new Client(i, args[0], Integer.parseInt(args[1]), repetitions).start();
		}

	}

}
