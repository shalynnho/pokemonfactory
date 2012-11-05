package factory.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import factory.CameraAgent;
import factory.NestAgent;
import factory.PartsRobotAgent;
import factory.data.Part;

/**
 * This tests the Parts Robot and Camera in the normatice scenario.
 * @author Ross Newman
 */
public class V0_JUnit_PartsRobotCameraNormativeScenario extends TestCase {

	/**
	 * Tests the normative scenario for Parts Robot and Camera Agent
	 */
	static NestAgent nest;
	static PartsRobotAgent parts;
	static CameraAgent camera;

	public void testNormativeScenario() throws InterruptedException {
		nest = new NestAgent("Test Nest");
		parts = new PartsRobotAgent("Test PartsRobot");
		camera = new CameraAgent("Test Camera");
		List<Part> partsList = new ArrayList<Part>();
		// camera.setNest(nest);
		camera.setPartsRobot(parts);
		nest.setCamera(camera);

		for (int i = 0; i < 9; i++) {
			Part p = new Part();
			nest.msgHereIsPart(p);
			partsList.add(p);
		}

		assertEquals("Nest Agent should have 9 parts inside", 9, nest.count);
		camera.msgIAmFull(nest);
		assertEquals(
				"Camera Agent should have 1 nest agent in its NestAgents List",
				1, camera.nests.size());
		camera.pickAndExecuteAnAction();
		System.out.println("camera gui taking picture of nest");
		camera.msgTakePictureNestDone(partsList, nest);
		camera.pickAndExecuteAnAction();
		System.out
				.println("camera should send partsList to partrobot and remove nest from list of nests");
		assertEquals(
				"Camera Agent should have 0 nest agent in its NestAgents List",
				0, camera.nests.size());
		assertEquals(
				"PartsRobot should have a single nest in its GoodParts map", 1,
				parts.GoodParts.size());
		parts.pickAndExecuteAnAction();
		assertEquals("Nest Agent should have a part removed by Parts Robot", 8,
				nest.count);
		assertEquals(
				"Nest Agent's state should change from TakingParts to Not TakingParts",
				false, nest.takingParts);
	}
}
