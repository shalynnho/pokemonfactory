package Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ConsoleWriter {
    public static String consoleID = "";
    private String name = "";

    public ConsoleWriter(String name) {
	this.name = name;
    }

    public void startConsole() {
	if (consoleID.equals("")) {
	    consoleID = sendData("start=y");
	}
	System.out.println("Console started: " + consoleID);
    }
    
    public void sendMessage(String message) {
	message = "[" + name + "] " + message; 
	if (consoleID != "") {
	    message += "&consoleID=" + consoleID;
	}
	System.out.println("Sending " + message);
	sendData("message=" + message);
    }

    public String sendData(String data) {
	try {
    	    URLConnection connection = new URL("http://ptz-debug.appspot.com/listen/").openConnection();
    	    connection.setDoOutput(true);
    	    connection.setRequestProperty("Accept-Charset", "UTF-8");
    	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	    
    	    OutputStream output = connection.getOutputStream();
	    output.write(new String(data).getBytes("UTF-8"));
	    
	    InputStream response = connection.getInputStream();
	    return read(response);
	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}

    }

    public String read(InputStream response) {
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
	    return reader.readLine();
	} catch (Exception e) {
	    return "";
	}
    }
}
