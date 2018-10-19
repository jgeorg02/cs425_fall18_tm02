import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form. This server is multi-threaded.
 */
public class Server {

	public static double getProcessCpuLoad() throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		AttributeList list = mbs.getAttributes(name, new String[] { "ProcessCpuLoad" });

		if (list.isEmpty())
			return Double.NaN;

		Attribute att = (Attribute) list.get(0);
		Double value = (Double) att.getValue();

		// usually takes a couple of seconds before we get real values
		if (value == -1.0)
			return Double.NaN;
		// returns a percentage value with 1 decimal point precision
		return ((int) (value * 1000) / 10.0);
	}

	public static void main(String[] args) {

		int port = 9999;
		int requests = 0;
		long start;

		if (args.length == 1)
			port = Integer.parseInt(args[0]);

		// Calculate current memory use:
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long afterUsedMem;
		long actualMemUsed;

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is listening on port " + port);

			start = System.nanoTime();

			while (true) {
				Socket socket = serverSocket.accept();

				requests++;
				new ServerThread(socket).start();

				if ((System.nanoTime() - start) >= 1000000) {

					// Throughput
					System.out.println("Requests for a millisec: " + requests);
					requests = 0;
					start = System.nanoTime();

					// Average Memory Utilization
					afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
					actualMemUsed = Math.abs(afterUsedMem - beforeUsedMem);
					System.out.println("Average Memory Utilization: " + actualMemUsed);
					beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

					// Average CPU Load
					try {
						System.out.println("Average CPU load: " + getProcessCpuLoad());
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println();

				}

			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		} finally {

		}
	}
}