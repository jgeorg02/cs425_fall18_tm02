import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input from
 * the user and prints echoed message from the server.
 */
public class Client extends Thread {

	private int userId;
	private String ip;
	private int port;
	private int n;

	public Client(int user, String ip, int port, int n) {
		this.userId = user;
		this.ip = ip;
		this.port = port;
		this.n = n;

	}

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
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				
				reader.readLine();

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