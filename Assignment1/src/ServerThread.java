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
			System.out.println("Server: " + text);

			String message[] = text.split(" ");

			/*
			 * Long time = new Long(System.currentTimeMillis()); ByteBuffer buffer =
			 * ByteBuffer.allocate(Long.BYTES); buffer.putLong(time);
			 * 
			 * System.out.println(new String(buffer.array(), Charset.forName("UTF-8")));
			 */

			/*long l = System.currentTimeMillis();
			byte b[] = new byte[8];

			ByteBuffer buf = ByteBuffer.wrap(b);
			buf.putLong(l);
System.out.println(b.length);*/
			//if (b.length <= 300 * 1000 && b.length >= 2000 * 1000) System.out.println("hi");
			
			int payload = (int)(Math.random()*((max-min)+1))+min;

				writer.println("WELCOME " + message[message.length - 1] + " " + payload * 1024);

			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}