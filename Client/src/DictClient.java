/**
 * @author Paul Hinojosa, perseus086
 */

import java.net.*;
import java.io.*;

public class DictClient {

	public static void main(String[] args) {
		//validate that there are 3 arguments
		if (args.length != 3) {
			System.err
					.println("java DictClient <address> <port> <word-to-look-for|-g>");
			System.exit(1);
		}

		String address = args[0];
		String port = args[1];
		
		//Console mode if word is not -g
		if (!args[2].equals("-g")) {
			Socket socket = null;
			try {
				socket = new Socket(address, Integer.parseInt(port));
			} catch (NumberFormatException e) {
				System.out.println("Bad port format. Please enter a number.\n");
				System.exit(2);
			} catch (UnknownHostException e) {
				System.out.println("Cannot find the specified host. Please check host name...\n");
				System.exit(3);
			} catch (IOException e) {
				System.out.println(" >Cannot connect with server...");
				System.out.println(" >Please check if port number corresponds to port number in server...");
				System.out.println(" >Possibly server is not operating. Please try again later...\n");
				System.exit(4);
			} catch(IllegalArgumentException e) {
				System.out.println("Please enter a number between 0 and 65535 for the port.\n");
				System.exit(2);
			}
			String word = args[2];
			//Creates a new Ask object
			Ask askfor = new Ask();
			try {
				System.out.println(askfor.request(socket, word));
			} catch (IOException e) {
				System.out.println("Something get wrong with the connection...\n");
				System.exit(4);
			}
			System.exit(0);
		}
		
		//GUI mode
		else{
			ClientGUI gui = new ClientGUI(address,Integer.parseInt(port));
			gui.setVisible(true);
		}
	}
}
