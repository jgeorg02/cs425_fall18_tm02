import java.net.*;
import java.util.Scanner;
import java.io.*;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input from
 * the user and prints echoed message from the server.
 */
public class Client {

	private int userId;

	public Client(int user) {
		this.userId = user;

	}

	public void clientConnection(String ip, int port) {

		String hostname = ip;
		// int port = 999;

		try (Socket socket = new Socket(hostname, port)) {

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			Scanner scan = new Scanner(System.in);
			String text;

			text = "HELLO " + "Local Address: " + socket.getLocalAddress() + " Port: " + port + " User id: " + userId;

			writer.println(text);

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			System.out.println(reader.readLine());

			scan.close();

			socket.close();

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}