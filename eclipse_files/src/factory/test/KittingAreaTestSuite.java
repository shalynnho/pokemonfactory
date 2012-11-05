package factory.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This test suite will run all JUnit tests for agents in the Kitting Area
 * including the KitRobot, Stand and Conveyor agents.
 * @author dpaje
 */
@RunWith(Suite.class)
@SuiteClasses({ V0_JUnit_KitRobotAgentTestNormativeScenario.class, })
public class KittingAreaTestSuite {

	/**
	 * Empty method; runs tests
	 */
	@Test
	public void runTestSuite() {
	}

}
