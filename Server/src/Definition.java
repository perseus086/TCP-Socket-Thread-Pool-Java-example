/**
 * @author Paul Hinojosa, perseus086
 */

import java.io.Serializable;

//This class is Serializable because it will be transmitted as
//object

public class Definition implements Serializable{
	private static final long serialVersionUID = 1L;
	private int status;
	private String[] definition;
	
	//Getters and setters
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String[] getDefinition() {
		return definition;
	}
	public void setDefinition(String[] definition) {
		this.definition = definition;
	}
}