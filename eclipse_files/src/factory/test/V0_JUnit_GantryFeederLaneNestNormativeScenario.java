package factory.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import factory.FeederAgent;
import factory.GantryAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.data.Bin;
import factory.data.Part;
import factory.data.PartType;
import factory.test.mock.MockAgent;

/**
 * This tests the Gantry, Feeder, Lane and Nest in the normative scenario
 * @author Arjun Bhargava
 */
public class V0_JUnit_GantryFeederLaneNestNormativeScenario extends TestCase {

	static GantryAgent gantry;
	static FeederAgent feeder;
	static LaneAgent lane;
	static NestAgent nest;
	protected Date date;

	private final URL URL = V0_JUnit_KitRobotAgent_Test_NormativeScenario.class
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

		nest.msgHereIsPartType(PartType.A);
		assertEquals("Nest Agent should have 1 requested type", 1,
				nest.requestList.size());
		nest.pickAndExecuteAnAction();
		assertEquals("Lane Agent should have 1 requested type", 1,
				lane.requestList.size());
		lane.pickAndExecuteAnAction();
		assertEquals("Feeder Agent should have 1 requested type", 1,
				feeder.requestList.size());
		feeder.pickAndExecuteAnAction();
		assertEquals("Gantry Agent should have 1 requested type", 1,
				gantry.requestedParts.size());
		Part part = new Part(PartType.A);
		Bin bin = new Bin(part);
		gantry.msgHereIsBinConfig(bin);
		assertEquals("Gantry Agent should have 1 bin", 1, gantry.binList.size());
		gantry.animation.release();
		gantry.pickAndExecuteAnAction();
		System.out
				.println("Gantry gui doing receive bin and messaging agent receiveBinDone");
		gantry.msgreceiveBinDone(bin);
		gantry.pickAndExecuteAnAction();
		System.out
				.println("Gantry gui doing dropbin and messaging agent dropBinDone");
		gantry.msgdropBinDone(bin);
		gantry.pickAndExecuteAnAction();
		assertEquals("Feeder Agent should have 1 currentPart", 1,
				feeder.currentParts.size());
		feeder.animation.release();
		feeder.pickAndExecuteAnAction();
		System.out
				.println("Feeder gui doing give part to diverter and messaging agent givePartToDiverterDone");
		feeder.msgGivePartToDiverterDone(part);
		feeder.pickAndExecuteAnAction();
		assertEquals("Lane Agent should have 1 currentPart", 1,
				lane.currentParts.size());
		lane.pickAndExecuteAnAction();
		assertEquals("Nest Agent should have 1 currentPart", 1,
				nest.currentParts.size());
		nest.pickAndExecuteAnAction();
		System.out
				.println("Parts Robot Agent taking part from nest and messaging TakingPart");
		nest.msgTakingPart(part);
		assertEquals("Nest Agent should have 0 currentPart", 0,
				nest.currentParts.size());
		
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
