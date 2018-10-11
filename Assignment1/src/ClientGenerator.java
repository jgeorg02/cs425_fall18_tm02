
public class ClientGenerator {
	
	public static void main(String args[]) {
		
		// default value
		int N = 10;
		
		if(args.length == 0) {
			System.out.println("You have to give server's ip and port! ");
			return;
		}else if (args.length == 3) {
			N = Integer.parseInt(args[2]);
		}
		
		for (int i = 1; i <= N; i++) {
			Client client = new Client(i);
			client.clientConnection(args[0], Integer.parseInt(args[1]));
		}
		
	}

}
