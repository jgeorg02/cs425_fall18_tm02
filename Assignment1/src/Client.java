import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * This class is responsible to simulates a number of concurrent users that
 * generates a series of requests towards the server. For each simulated user,
 * this class establishes a connection to the server. Also, this class
 * calculates the round-trip time (RTT).
 *
 */
public class Client extends Thread {

	// A number of each user
	private int userId;
	// IP address
	private String ip;
	// Port number
	private int port;
	// Number of requests
	private int n;

	/**
	 * Constructor of Client class.
	 * 
	 * @param user
	 *            A number of each user
	 * @param ip
	 *            IP address
	 * @param port
	 *            Port number
	 * @param n
	 *            Number of requests
	 */
	public Client(int user, String ip, int port, int n) {
		this.userId = user;
		this.ip = ip;
		this.port = port;
		this.n = n;

	}

	/**
	 * This function establishes a connection from client to server. Also,
	 * calculates the RRT.
	 */
	public void run() {

		String hostname = ip;

		for (int i = 0; i < n; i++) {
			try (Socket socket = new Socket(hostname, port)) {

				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);

				Scanner scan = new Scanner(System.in);
				String text;

				text = "HELLO " + "Local Address: " + socket.getLocalAddress() + " Port: " + port + " User id: "
						+ userId;

				long timeSend = System.nanoTime();

				writer.println(text);

				InputStream input = socket.getInputStream();
				DataInputStream dataInput = new DataInputStream(input);

				dataInput.readUTF();
				int length = dataInput.readInt(); // read length of incoming message
				if (length > 0) {
					byte[] message = new byte[length];
					dataInput.readFully(message, 0, message.length); // read the message
				}

				// RTT:
				System.out.println("RTT: " + (long) (System.nanoTime() - timeSend) + " ns");

				scan.close();

				socket.close();

			} catch (UnknownHostException ex) {

				System.out.println("Server not found: " + ex.getMessage());

			} catch (IOException ex) {

				System.out.println("I/O error: " + ex.getMessage());
			}
		}
	}
}