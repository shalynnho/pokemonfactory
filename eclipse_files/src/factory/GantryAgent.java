package factory;

import java.util.ArrayList;
import java.util.List;

import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import agent.Agent;
import factory.data.*;
import factory.data.Bin.BinStatus;

public class GantryAgent extends Agent implements Gantry {

	public List<Bin> binList = new ArrayList<Bin>();   
    public List<PartType> requestedParts = new ArrayList<PartType>();
	
    //WAITING FOR GANTRYGRAPHICS
    //private GantryGraphics gantryGraphic;
    private FeederAgent feeder;
    
    
    private final String name;
    
    public GantryAgent(String name) {
    	super();
    	
    	this.name = name;
    }
    
    public void msgHereIsBinConfig(Bin bin) {   
        binList.add(bin);  
        stateChanged();}    
    public void msgINeedParts(PartType type) {              
        requestedParts.add(type);         
        stateChanged();}                
    public void msgreceiveBinDone(Bin bin) {         
        bin.binState = BinStatus.OVER_FEEDER;        
        stateChanged();}        
    public void msgdropBinDone(Bin bin) {      
        bin.binState = BinStatus.EMPTY;      
        stateChanged(); }       
    public void msgremoveBinDone(Bin bin) {       
        binList.remove(bin);        
        stateChanged();}
    
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		for(PartType requested : requestedParts) {
			for(Bin bin : binList) {
				if(bin.part.type == requested && bin.binState == BinStatus.FULL) {
					moveToFeeder(bin);
					return true;
				}
			}
		}
		for(PartType requested : requestedParts) {
			for(Bin bin : binList) {
				if(bin.part.type == requested && bin.binState == BinStatus.OVER_FEEDER) {
					fillFeeder(bin);
					return true;
				}
			}
		}
		for(PartType requested : requestedParts) {
			for(Bin bin : binList) {
				if(bin.part.type == requested && bin.binState == BinStatus.EMPTY) {
					discardBin(bin);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void moveToFeeder(Bin bin) {
		print("Moving bin to over feeder");
        bin.binState = BinStatus.MOVING;     
        //GUIGantry.receiveBin(bin);      
        stateChanged();
	}
	public void fillFeeder(Bin bin) { 
		print("Placing bin in feeder and filling feeder");
        feeder.msgHereAreParts(bin.part);      
        bin.binState = BinStatus.FILLING_FEEDER;     
        //GUIGantry.dropBin(bin, bin.feeder);     
        stateChanged();
	}        
    public void discardBin(Bin bin) {
    	print("Discarding bin");
        bin.binState = BinStatus.DISCARDING;     
        //GUIGangry.removeBin(bin);       
        stateChanged();
    }   
	
	public String getName() {
		return name;
	}
	public void setFeeder(FeederAgent feeder) {
		this.feeder = feeder;
	}
	
}
