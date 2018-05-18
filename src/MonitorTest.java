import junit.framework.TestCase;

import java.io.File;

public class MonitorTest extends TestCase {

	private Monitor monitor;
	private Customer customer;

	public void setUp() throws Exception {
		super.setUp();
		Controller.startManager();
		customer = new Customer("testName", "testAddress", "example@test.com", 11223344, 150);
		monitor = new Monitor(customer);
		monitor.loadReadings();
	}

	public void testGetReading() {
		assertEquals((double) 0, monitor.getReading()[0]);
		assertEquals((double) 0, monitor.getReading()[1]);
	}

	public void testGetReadings() {
		assertEquals(1, monitor.getReadings().size());
		assertEquals((double) 0, monitor.getReadings().get(0).getElectricity());
		assertEquals((double) 0, monitor.getReadings().get(0).getGas());
	}

	public void testGetReadingsByDay() {
		assertEquals(1, monitor.getReadingsByDay().size());
		assertEquals((double) 0, monitor.getReadingsByDay().get(0).getElectricity());
		assertEquals((double) 0, monitor.getReadingsByDay().get(0).getGas());
	}

	public void testGetReadingsByWeek() {
		assertEquals(1, monitor.getReadingsByWeek().size());
		assertEquals((double) 0, monitor.getReadingsByWeek().get(0).getElectricity());
		assertEquals((double) 0, monitor.getReadingsByWeek().get(0).getGas());
	}

	public void testGetReadingsByMonth() {
		assertEquals(1, monitor.getReadingsByMonth().size());
		assertEquals((double) 0, monitor.getReadingsByMonth().get(0).getElectricity());
		assertEquals((double) 0, monitor.getReadingsByMonth().get(0).getGas());
	}

	public void testGetCosts() {
		assertEquals((double) 0, monitor.getCosts()[0]);
		assertEquals((double) 0, monitor.getCosts()[1]);
	}

	public void testSendReadings() {
		monitor.sendReadings();
		File file = new File("./receivedReadings/11223344.xml");
		assertTrue(file.exists());
		file.delete();
	}

	public void testDeleteReadings() {
		monitor.deleteReadings();
		File file = new File("./readings/11223344.xml");
		assertFalse(file.exists());
	}

	public void testGetCustomer() {
		assertEquals(customer, monitor.getCustomer());
	}

	public void testGetBudget() {
		assertEquals((double) 150, monitor.getBudget());
	}

	public void testSetBudget() {
		monitor.setBudget(100);
		assertEquals((double) 100, monitor.getBudget());
	}

	public void testGetID() {
		assertEquals(11223344, monitor.getID());
	}

	public void tearDown() {
		File file = new File("./readings/11223344.xml");
		file.delete();
	}
}