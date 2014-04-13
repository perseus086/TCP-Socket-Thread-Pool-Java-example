/**
 * @author Paul Hinojosa, perseus086
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ask {

	public String request(Socket socket, String word) throws IOException
	{
		String answer = "";
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(outputStream);
		dataOutput.writeUTF(word);
		
		System.out.println("Sending request...\n");

		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInput = new ObjectInputStream(inputStream);
		
		Definition definition = new Definition();
		try {
			definition = (Definition) objectInput.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("An error occured while receiving please try again later");
		}

		if(definition.getStatus()==1)
		{
			answer = "Definition of "+word.toUpperCase()+":\n";
			int line = answer.length()-1;
			for(int i=0;i<line;i++) answer = answer + "=";
			answer = answer + "\n";
			String [] result = definition.getDefinition();
			for(int i=0; i<result.length; i++)
				answer = answer + String.valueOf(i+1) + " --> " + result[i]+"\n";
		}
		else if (definition.getStatus()==0)
		{
			answer = "Definition of " + word.toUpperCase() + " not found...\nPlease check your spelling or does not exist in this dictionary\n";
		}
		
		dataOutput.close();
		outputStream.close();
		objectInput.close();
		inputStream.close();
		
		socket.close();
		
		return answer;
	}
	
}
