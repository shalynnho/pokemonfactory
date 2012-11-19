package Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;

public abstract class ReadSaveData implements Serializable {
    protected Object obj;
    
    /**
     * Attemps to read input file
     * @param File f
     */
    protected void readFile(File f) {
    	try{
    		InputStream is = new FileInputStream(f);
    		InputStream buffer = new BufferedInputStream(is);
    		ObjectInput oi = new ObjectInputStream(buffer);
    	}
    	catch (IOException ex) {
    		System.out.println("Cannot Read this file");
    	}
    }
    
    public abstract void parseObject(ObjectInput input);
    
}
