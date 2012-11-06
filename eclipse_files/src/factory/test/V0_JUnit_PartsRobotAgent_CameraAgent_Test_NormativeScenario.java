package factory.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import factory.CameraAgent;
import factory.CameraAgent.NestStatus;
import factory.NestAgent;
import factory.PartsRobotAgent;
import factory.data.Part;

/**
 * This tests the Parts Robot and Camera in the normative scenario. The UUT is
 * the interaction between the Nest, Camera and Parts Robot. The camera captures
 * a pair of full nests and sends the partsrobot a list of good parts. Parts
 * Robot then finishes filling a kit and the camera is asked to inspect the kit.
 * @author Daniel Paje
 */
public class V0_JUnit_PartsRobotAgent_CameraAgent_Test_NormativeScenario extends
		TestCase {
	protected NestAgent nest;
	protected NestAgent nest2;
	protected PartsRobotAgent partsrobot;
	protected CameraAgent camera;

	protected Date date;

	private final URL URL = V0_JUnit_PartsRobotAgent_CameraAgent_Test_NormativeScenario.class
			.getResource(".");
	private final String FILEPATH = URL.toString().replace("file:", "");

	@Override
	protected void setUp() {
		nest = new NestAgent("nest");
		nest2 = new NestAgent("nest2");
		partsrobot = new PartsRobotAgent("partsrobot");
		camera = new CameraAgent("camera");

		camera.setNest(nest);
		camera.setNest(nest2);
		for (int i = 0; i < 6; i++) {
			camera.setNest(new NestAgent("nest" + i));
		}
		camera.setPartsRobot(partsrobot);

		nest.setCamera(camera);
		date = new Date();
	}

	@Override
	protected void tearDown() {
		nest = null;
		nest2 = null;
		partsrobot = null;
		camera = null;
		date = null;
	}

	public void testNormativeScenario() throws InterruptedException {

		List<Part> partsList = new ArrayList<Part>();

		for (int i = 0; i < 9; i++) {
			Part p = new Part();
			nest.msgHereIsPart(p);
			partsList.add(p);
		}

		List<CameraAgent.MyNest> MyNests = camera.getNests();
		CameraAgent.MyNest MyNest = null;
		CameraAgent.MyNest MyNest2 = null;

		for (CameraAgent.MyNest mn : MyNests) {
			if (mn.nest == nest) {
				MyNest = mn;
			} else if (mn.nest == nest2) {
				MyNest2 = mn;
			}
		}

		assertEquals("Nest should have 9 parts", 9, nest.currentParts.size());

		assertEquals("Camera should have 8 nests", 8, camera.getNests().size());

		// Start the test
		camera.msgIAmFull(nest);

		// Invoke Camera's scheduler
		camera.pickAndExecuteAnAction();

		// No rules should have passed as only 1 nest is full
		camera.msgIAmFull(nest2);

		camera.pickAndExecuteAnAction();

		// Camera's scheduler should have fired takePictureOfNest()
		assertEquals("Camera should have set nest's status to 'photographing'",
				NestStatus.PHOTOGRAPHING, MyNest.state);
		assertEquals(
				"Camera should have set nest2's status to 'photographing'",
				NestStatus.PHOTOGRAPHING, MyNest2.state);

		// CameraGraphics sends this when the camera has taken a photograph of
		// both nests.
		camera.msgTakePictureNestDone(nest);
		camera.msgTakePictureNestDone(nest2);

		assertEquals("Camera should have set nest's status to 'photographed'",
				NestStatus.PHOTOGRAPHED, MyNest.state);
		assertEquals("Camera should have set nest2's status to 'photographed'",
				NestStatus.PHOTOGRAPHED, MyNest2.state);

		camera.pickAndExecuteAnAction();

		// Camera's scheduler should have fired tellPartsRobot()
		assertEquals("Camera should have set nest's status to 'not ready'",
				NestStatus.NOT_READY, MyNest.state);
		assertEquals("Nest2's status should still be 'photographed'",
				NestStatus.PHOTOGRAPHED, MyNest2.state);

		camera.pickAndExecuteAnAction();

		// Camera's scheduler should have again fired tellPartsRobot()
		assertEquals("Nest2's status should still be 'not ready'",
				NestStatus.NOT_READY, MyNest2.state);
		assertEquals("Camera should have set nest2's status to 'not ready'",
				NestStatus.NOT_READY, MyNest2.state);

		// Camera now sleeps until the kit is assembled

	}
}
