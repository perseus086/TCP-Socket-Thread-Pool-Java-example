/**
 * @author Paul Hinojosa, perseus086
 * 
 * GUI Mode not very well implemented
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame{
	private JLabel label;
	private JButton button;
	private JTextField text;
	private JTextArea txta;
	private JScrollPane scrollPane;
	private String address;
	private int port;
	
	public ClientGUI(String address, int port)
	{
		this.address = address;
		this.port = port;
		
		setLayout(new FlowLayout());
		setSize(600, 450);
		label = new JLabel("Word to search:");
		add(label);
		
		text = new JTextField(15);
		add(text);
		
		button = new JButton("Search!");
		add(button);
		
		event e = new event();
		button.addActionListener(e);
		
		txta = new JTextArea(23,45);
		txta.setLineWrap(true);
		txta.setWrapStyleWord(true);
		txta.setEditable(false);
		scrollPane = new JScrollPane(txta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);	
	}

	public class event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Socket socket = null;
			try {
				socket = new Socket(address, port);
			} catch (NumberFormatException ex) {
				System.out.println("Bad port format. Please enter a number.\n");
				 System.exit(2);
			} catch (UnknownHostException ex) {
				System.out.println("Cannot find the specified host. Please check host name...\n");
				System.exit(3);
			} catch (IOException ex) {
				System.out.println(" >Cannot connect with server...");
				System.out.println(" >Please check if port number corresponds to port number in server...");
				System.out.println(" >Possibly server is not operating. Please try again later...\n");
				System.exit(4);
			}catch(IllegalArgumentException ex) {
				System.out.println("Please enter a number between 0 and 65535 for the port.\n");
				System.exit(2);
			}
			String word = text.getText().trim();
			System.out.println(word);
			Ask askfor = new Ask();
			try {
				txta.setText(askfor.request(socket, word));
			} catch (IOException ex) {
				System.out.println("Something get wrong with the connection...\n");
				System.exit(4);
			}	
		}
	}	
}
