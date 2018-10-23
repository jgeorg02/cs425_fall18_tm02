import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * This program demonstrates a simple TCP/IP socket server. This server is
 * multi-threaded, it created the threads and lets them serve the client.
 */
public class Server {

	/**
	 * This method is responsible for getting the cpu load.
	 * 
	 * @return the percentage value of the cpu load.
	 * @throws Exception
	 */
	public static double getProcessCpuLoad() throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		AttributeList list = mbs.getAttributes(name, new String[] { "ProcessCpuLoad" });

		if (list.isEmpty())
			return Double.NaN;

		Attribute att = (Attribute) list.get(0);
		Double value = (Double) att.getValue();

		if (value == -1.0)
			return Double.NaN;

		return ((int) (value * 1000) / 10.0);
	}

	public static void main(String[] args) {

		int port = 9999, counter = 0, requests = 0; // the counter counts the requests that the server served.
		long start, now;
		double averageCpu = 0, averageUtilizationMem = 0;

		if (args.length == 1)
			port = Integer.parseInt(args[0]);

		// Calculate current memory use:
		long afterUsedMem = 0, actualMemUsed = 0,
				beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is listening on port " + port);

			start = System.nanoTime(); // the current time of starting the server.

			while (true) {
				Socket socket = serverSocket.accept();

				requests++; // counts the requests
				counter++;
				new ServerThread(socket).start();

				now = System.nanoTime(); // current time of getting a request.
				
				if ((now - start) >= 1000000) {

					// Throughput
					System.out.println("Requests for " + ((now - start) / 1000000) + " millisec: " + requests);
					requests = 0;
					start = System.nanoTime();

					// Average Memory Utilization
					afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
					actualMemUsed = Math.abs(afterUsedMem - beforeUsedMem);
					averageUtilizationMem += actualMemUsed;
					System.out.println("Memory Utilization: " + averageUtilizationMem / counter);
					beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

					// Average CPU Load
					try {
						System.out.println("CPU load: " + getProcessCpuLoad());
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
