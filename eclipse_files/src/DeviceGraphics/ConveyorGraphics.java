package DeviceGraphics;

public class ConveyorGraphics extends DeviceGraphics implements GraphicsInterfaces.ConveyorGraphics {

	    //Variables
	    private ArrayList<KitGraphics> kitsOnConveyor; // all kits on conveyor
	    private Location location;
	    private ClientReader cr;
	    private ClientWriter cw;
	        
	    public ConveyorGraphics(){
	        location = new Location(0,0);
	        kitsOnConveyor = new ArrayList<KitGraphics>();
	        cr = new ClientReader();
	        cw = new ClientWriter();
	    } 

	    public void bringEmptyKit(){
	        kitsOnConveyor.add(new KitGraphics());
	    } 

	    public void giveKitToKitRobot(KitGraphics kg){
	        kg.setFull(true);
	       // kitsOnConveyor.remove(kg); 
	      
	    } 

	    /**
	     * send a completed kit offscreen
	     *
	     * @param kit - a kit must be received from KitRobot before sending it away
	     */

	    public void receiveKit(KitGraphics kg){
	        kitsOnConveyor.add(kg);
	    } 	
}
