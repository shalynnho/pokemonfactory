package factory;

import java.util.ArrayList;
import java.util.List;

import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import agent.Agent;
import factory.data.*;
import factory.data.Bin.BinStatus;

public class GantryAgent extends Agent implements Gantry {

	private List<Bin> binList = new ArrayList<Bin>();   
    private List<PartType> requestedParts = new ArrayList<PartType>();
	
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
				}
			}
		}
		for(PartType requested : requestedParts) {
			for(Bin bin : binList) {
				if(bin.part.type == requested && bin.binState == BinStatus.OVER_FEEDER) {
					fillFeeder(bin);
				}
			}
		}
		for(PartType requested : requestedParts) {
			for(Bin bin : binList) {
				if(bin.part.type == requested && bin.binState == BinStatus.EMPTY) {
					discardBin(bin);
				}
			}
		}
		
		return false;
	}
	
	public void moveToFeeder(Bin bin) {     
        bin.binState = BinStatus.MOVING;     
        //GUIGantry.receiveBin(bin);      
        stateChanged();}
	public void fillFeeder(Bin bin) {       
        feeder.msgHereAreParts(bin.part);      
        bin.binState = BinStatus.FILLING_FEEDER;     
        //GUIGantry.dropBin(bin, bin.feeder);     
        stateChanged();}        
    public void discardBin(Bin bin) {       
        bin.binState = BinStatus.DISCARDING;     
        //GUIGangry.removeBin(bin);       
        stateChanged();}   
	
	
	
	
}
