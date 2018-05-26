import junit.framework.TestCase;

import java.io.File;

/**
 * Title        MonitorTest.java
 * Description  Monitor test class.
 */
public class MonitorTest extends TestCase {
	/**
	 * Tested monitor.
	 */
	private Monitor monitor;

	/**
	 * Tested customer.
	 */
	private Customer customer;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ManagerController.startManager();
		customer = new Customer("testName", "testAddress", "example@test.com", 11223344, 150.0);
		monitor = new Monitor(customer);
		monitor.loadReadings();
	}

	/**
	 * Test getReading.
	 */
	public void testGetReading() {
		assertNotNull(monitor.getReading());
	}

	/**
	 * Test getReadings.
	 */
	public void testGetReadings() {
		assertNotNull(monitor.getReadings());
	}

	/**
	 * Test getReadingsByDay.
	 */
	public void testGetReadingsByDay() {
		assertNotNull(monitor.getReadingsByDay());
	}

	/**
	 * Test getReadingsByWeek.
	 */
	public void testGetReadingsByWeek() {
		assertNotNull(monitor.getReadingsByWeek());
	}

	/**
	 * Test getReadingsByMonth.
	 */
	public void testGetReadingsByMonth() {
		assertNotNull(monitor.getReadingsByMonth());
	}

	/**
	 * Test getCosts.
	 */
	public void testGetCosts() {
		assertNotNull(monitor.getCosts());
	}

	/**
	 * Test deleteReadings.
	 */
	public void testDeleteReadings() {
		monitor.deleteReadings();
		File file = new File("./readings/11223344.xml");
		assertFalse(file.exists());
	}

	/**
	 * Test saveReadings.
	 */
	public void testSaveReadings() {
		monitor.saveReadings();
		File file = new File("./readings/11223344.xml");
		assertTrue(file.exists());
	}

	/**
	 * Test getCustomer.
	 */
	public void testGetCustomer() {
		assertEquals(customer, monitor.getCustomer());
	}

	/**
	 * Test getBudget.
	 */
	public void testGetBudget() {
		assertEquals(150.0, monitor.getBudget());
	}

	/**
	 * Test setBudget.
	 */
	public void testSetBudget() {
		monitor.setBudget(100.0);
		assertEquals(100.0, monitor.getBudget());
	}

	/**
	 * Test getID.
	 */
	public void testGetID() {
		assertEquals(11223344, monitor.getID());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tearDown() {
		File file = new File("./readings/11223344.xml");
		file.delete();
	}
}