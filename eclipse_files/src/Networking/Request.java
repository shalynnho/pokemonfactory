package Networking;

import java.io.Serializable;

public class Request implements Serializable{
	private String command;
	private String target;
	Object data;
	
	public Request(String newCommand, String newTarget, Object newData) {
		command = newCommand;
		target = newTarget;
		data = newData;
	}
	
	public String getCommand() {
		return command;
	}
	public String getTarget() {
		return target;
	}
	public Object getData() {
		return data;
	}
}
