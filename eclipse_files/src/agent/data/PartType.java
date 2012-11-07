package agent.data;

public enum PartType {
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	E("E"),
	F("F"),
	G("G"),
	H("H");
	
	private final String name;       

    private PartType(String s) {
        name = s;
    }

    public String toString(){
       return name;
    }
}
