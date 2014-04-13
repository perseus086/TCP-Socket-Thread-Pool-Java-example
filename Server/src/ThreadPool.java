/**
 * @author Paul Hinojosa, paulhl, 601499
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ThreadPool {
	private Queue queue = null;
	private Worker[] workerThread = new Worker[20];
	private Receiver reception = null;
	private Scanner keyboard;
	private int numberOfWorkers;
	private int port;
	private ServerSocket serverSocket;

	public ThreadPool(int numberOfWorkers, int maxBufferSize, int port) {
		this.numberOfWorkers = numberOfWorkers;
		this.queue = new Queue(maxBufferSize);
		this.port = port;
	}

	public synchronized void initialize() {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("A problem ocurred openning the socket");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println("Port must be between 0 and 65535");
			System.exit(2);
		}
		
		System.out
				.println("[SERVER] to close wait until no activity and press crtl+C or write quit");
		System.out.println("[SERVER] PORT=" + serverSocket.getLocalPort()
				+ " opened");

		for (int i = 0; i < numberOfWorkers; i++)
			workerThread[i] = new Worker(this.queue);
		for (int i = 0; i < numberOfWorkers; i++)
			workerThread[i].start();

		this.reception = new Receiver(this.serverSocket, this.queue);
		reception.start();

		String command = "";
		keyboard = new Scanner(System.in);

		while (!command.equals("quit")) {
			command = keyboard.nextLine().toLowerCase();
		}

		System.out.println("[SERVER] trying to stop Receiver thread");
		reception.stopRunning();
		for (int i = 0; i < numberOfWorkers; i++) {
			System.out.println("[SERVER] Trying to stop Thread-" + i);
			workerThread[i].stopRunning();
		}

		try {
			serverSocket.close();
			System.out.println("[SERVER] Closing socket");
		} catch (IOException e) {
			System.out.println("[S-SOCKET] Cannot close server socket");
		}
		System.out.println("[S-SOCKET] Closed");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		System.out.println("[SERVER] Closed");
		System.exit(0);
	}
}