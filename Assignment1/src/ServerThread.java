import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			String text;
			int min = 300;
			int max = 2000;

			text = reader.readLine();

			String message[] = text.split(" ");
			String payload = "payload: ";
		
			int payloadSize = (int) (Math.random() * ((max - min) + 1)) + min;
			payloadSize *= 1024;

			writer.println("WELCOME " + message[message.length - 1] + " " + payloadSize);
			writer.flush();
			for (int i = 0; i < payloadSize; i++) 
				payload += '*'; 
		
			writer.println(payload);
			writer.flush();
			
			
			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}