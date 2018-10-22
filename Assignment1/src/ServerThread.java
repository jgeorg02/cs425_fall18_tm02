import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This thread is responsible to handle client connection. Responds with a
 * simple welcome user_id and send the payload size and the payload. The payload
 * is generated with a random size.
 */
public class ServerThread extends Thread {
	private Socket socket; // the socket
	private int min = 300 * 1024; // min payload size
	private int max = 2000 * 1024; // max payload size
	private int payloadSize = (int) (Math.random() * ((max - min) + 1)) + min;

	/**
	 * This is the constructor of this class. It initializes the socket that the
	 * client and the server are connected.
	 * 
	 * @param socket
	 */
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * This function creates an array that will contain the payload. It fills it
	 * with 1.
	 * 
	 * @return the payload
	 */
	public byte[] getPayload() {

		byte[] payload = new byte[payloadSize];
		Arrays.fill(payload, (byte) 1);
		return payload;
	}

	/**
	 * This function contains the functionality of this thread. It reads the
	 * client's message and responds to it with a "Welcome" message. It also sends
	 * the payload size and the payload.
	 */
	public void run() {

		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());

			String text = reader.readLine(); // the message that the client sent.

			String message[] = text.split(" ");

			dataOutput.writeUTF("WELCOME " + message[message.length - 1] + " " + payloadSize);

			dataOutput.writeInt(payloadSize); // write length of the message
			dataOutput.write(getPayload()); // write the message

			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}