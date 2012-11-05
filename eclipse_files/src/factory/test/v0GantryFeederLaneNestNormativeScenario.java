package factory.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import factory.FeederAgent;
import factory.GantryAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.test.mock.MockAgent;
import junit.framework.TestCase;

public class v0GantryFeederLaneNestNormativeScenario extends TestCase {

	static GantryAgent gantry;
	static FeederAgent feeder;
	static LaneAgent lane;
	static NestAgent nest;
	protected Date date;

	private final URL URL = KitRobotAgentTestNormativeScenario.class
			.getResource(".");
	private final String FILEPATH = URL.toString().replace("file:", "");
	
	@Test
	public void testNormativeScenario() throws InterruptedException {
		gantry = new GantryAgent("Test Gantry");
		feeder = new FeederAgent("Test Feeder");
		lane = new LaneAgent("Test Lane");
		nest = new NestAgent("Test Nest");
		gantry.setFeeder(feeder);
		feeder.setGantry(gantry);
		feeder.setLane(lane);
		lane.setFeeder(feeder);
		lane.setNest(nest);
		nest.setLane(lane);
		
		
		
	}
	
	
	

	/**
	 * This is a modified version of Sean Turner's helper function which prints
	 * out the logs from any number of MockAgents. This should help to assist in
	 * debugging.
	 * @return a string containing the logs from the mock agents.
	 */
	public String getLogs(List<MockAgent> MockAgents) {
		StringBuilder sb = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		sb.append("Found logs for " + MockAgents.size() + " Mock agent(s).");
		sb.append(newLine);

		for (MockAgent m : MockAgents) {
			sb.append(newLine);
			sb.append("-------" + m.getName() + " Log-------");
			sb.append(newLine);
			sb.append(m.getLog().toString());
			sb.append("-------End" + m.getName() + " Log-------");
			sb.append(newLine);
		}

		// System.out.println(sb.toString());

		return sb.toString();

	}

	/**
	 * Generates a log file of the messages received by MockAgents from the
	 * KitRobotAgent
	 * @param test the name of the test
	 * @param log string representation of a log to be printed to the file
	 */
	public void generateLogFile(String test, String log) {

		FileOutputStream fos;

		System.out.println("Message logs saved to " + FILEPATH + test + "("
				+ date.toString() + ").txt");

		try {
			fos = new FileOutputStream(FILEPATH + test + "(" + date.toString()
					+ ").txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(log);
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}
	
	
}
