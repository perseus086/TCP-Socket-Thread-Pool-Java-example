/**
 * @author Paul Hinojosa, perseus086
 */

import java.io.Serializable;

public class Definition implements Serializable {
	private static final long serialVersionUID = 1L;
	public int status;
	public String[] definition;
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