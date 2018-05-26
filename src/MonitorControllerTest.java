import junit.framework.TestCase;

import java.io.File;

/**
 * Title        MonitorControllerTest.java
 * Description  MonitorController test class.
 */
public class MonitorControllerTest extends TestCase {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ManagerController.startManager();
		MonitorController.login(87654321);
	}

	/**
	 * Test sendReadings.
	 */
	public void testSendReadings() {
		MonitorController.sendReadings();
		File file = new File("./receivedReadings/87654321.xml");
		assertTrue(file.exists());
		file.delete();
	}

	/**
	 * Test getBudget.
	 */
	public void testGetBudget() {
		assertEquals(100.0, MonitorController.getBudget());
	}

	/**
	 * Test updateBudget.
	 */
	public void testUpdateBudget() {
		MonitorController.updateBudget(150.0);
		assertEquals(150.0, MonitorController.getBudget());
		MonitorController.updateBudget(100.0);
	}

	/**
	 * Test getReading.
	 */
	public void testGetReading() {
		assertNotNull(MonitorController.getReading());
	}

	/**
	 * Test getReadings.
	 */
	public void testGetReadings() {
		assertNotNull(MonitorController.getReadings());
	}

	/**
	 * Test getReadingsDataByDay.
	 */
	public void testGetReadingsDataByDay() {
		assertNotNull(MonitorController.getReadingsDataByDay());
	}

	/**
	 * Test getReadingsDataByWeek.
	 */
	public void testGetReadingsDataByWeek() {
		assertNotNull(MonitorController.getReadingsDataByWeek());
	}

	/**
	 * Test getReadingsDataByMonth.
	 */
	public void testGetReadingsDataByMonth() {
		assertNotNull(MonitorController.getReadingsDataByMonth());
	}

	/**
	 * Test getCosts.
	 */
	public void testGetCosts() {
		assertNotNull(MonitorController.getCosts());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tearDown() {
		MonitorController.logout();
		File file = new File("./readings/87654321.xml");
		file.delete();
	}
}