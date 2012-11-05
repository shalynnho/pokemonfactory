package factory.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This test suite will run all JUnit tests for agents in the Kitting Cell. The
 * following test classes will be run in this suite:
 * @author dpaje
 */
@RunWith(Suite.class)
@SuiteClasses({ V0_JUnit_KitRobotAgent_Test_NormativeScenario.class, })
public class KittingAreaTestSuite {

	/**
	 * Empty method; runs tests
	 */
	@Test
	public void runTestSuite() {
	}

}
