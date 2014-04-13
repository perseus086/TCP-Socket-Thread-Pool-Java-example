/**
 * @author Paul Hinojosa, perseus086
 */

import java.io.IOException;
import java.net.ServerSocket;

public class Receiver extends Thread{
	 private volatile boolean isRunning = true;
	 private Queue queue;
	 private ServerSocket serverSocket;
	
	public Receiver(ServerSocket serverSocket, Queue queue){
		this.serverSocket = serverSocket;
		this.queue = queue;
	}
	
	
	public void run(){
		System.out.println("[Receiver] Running...");
		
		 while(this.isRunning)
		 {
			 try {
				queue.enqueue(serverSocket.accept());
			} catch (IOException e) {
				System.out.println("[Receiver] Something get wrong with the comunication");
			}
		 }
		 System.out.println("[Receiver] Finished");
	 }
	 
	 
	 public synchronized void stopRunning()
	 {
		 this.isRunning = false;
		 try {
				serverSocket.close();
				System.out.println("[Receiver] Closing server socket");
			} catch (IOException e) {
				System.out.println("[Receiver] Failing closing socket");
			}
	 }
}