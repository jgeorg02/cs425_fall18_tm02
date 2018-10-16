import java.io.*;
import java.net.*;

/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form. This server is multi-threaded.
 */
public class Server {

	public static void main(String[] args) {

		int port = 9999;
		int requests = 0;
		long start;

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is listening on port " + port);
			
			start = System.nanoTime();

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println();
				
				requests++;
				new ServerThread(socket).start();
	
				if ((System.nanoTime() - start) >= 1000000) {
					System.out.println("Requests for a millisec:" + requests);
					requests = 0;
					start = System.nanoTime();
				}
			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}