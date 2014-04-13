/**
 * @author Paul Hinojosa, paulhl, 601499
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker extends Thread{

	private Queue queue;
	private volatile boolean isRunning;
	// Constructors
	public Worker(Queue queue) {
		this.queue = queue;
		this.isRunning = true;
	}

	@Override
	public void run(){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] Running...");
		System.out.println("[" + threadName + "] Initalizing variables...");
		
		//Initializing variables
		Socket socket;
		DataInputStream inputStream = null;
		String word = "";
		ObjectOutputStream objectOutput = null;
		Search search = new Search();
		
		while (isRunning) {
			while (!queue.isEmpty()) {
				//Takes the corresponding socket that was first in the queue
				socket = (Socket) queue.dequeue();
				
				System.out.println("[" + threadName + "] Connecting with: "
						+ socket.getPort());
				//Initiates the input stream communication
				try {
					inputStream = new DataInputStream(socket.getInputStream());
					word = inputStream.readUTF();
					System.out.println("[" + threadName + "] Word: " + word
							+ " from " + socket.getPort());

				} catch (IOException e) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong with the inputStream");
				}
				
				//Creates a new object Definition
				Definition definition = search.searchFor(word);
				
				System.out.println("[" + threadName
						+ "] Sending defintion of: " + word + " to "
						+ socket.getPort());
				
				//Output stream: response
				OutputStream output;
				try {
					output = socket.getOutputStream();
					objectOutput = new ObjectOutputStream(output);
					objectOutput.writeObject(definition);
				} catch (IOException e1) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong while sending response");
				}

				System.out.println("[" + threadName
						+ "] Closing connections with: " + socket.getPort());
				try {
					inputStream.close();
					objectOutput.close();
					socket.close();
				} catch (Exception e) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong while closing socket");
				}
			}
		}
		System.out.println("[" + threadName + "] Finished");	
	}
	
	//Method helps breaking the while loop
	public synchronized void stopRunning()
	{
		this.isRunning = false;
	}
}
