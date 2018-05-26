import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Title        ControllerTest.java
 * Description  Controller test class.
 */
public class ControllerTest extends TestCase {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ManagerController.startManager();
	}

	/**
	 * Test loadCustomers.
	 */
	public void testLoadCustomers() {
		assertTrue(Controller.loadCustomers());
	}

	/**
	 * Test getIDType.
	 */
	public void testGetIDType() {
		assertEquals(0, Controller.getIDType(7355608));
		assertEquals(-1, Controller.getIDType(1234));
		assertEquals(1, Controller.getIDType(12345678));
	}

	/**
	 * Test checkID.
	 */
	public void testCheckID() {
		assertTrue(Controller.checkID(12345678));
		assertFalse(Controller.checkID(1234));
	}

	/**
	 * Test checkDuplicated.
	 */
	public void testCheckDuplicated() {
		assertTrue(Controller.checkDuplicated(new Customer("Jim", "New York", "jim@example.com", 11223344, 500.0)));
		assertFalse(Controller.checkDuplicated(new Customer("JimTest", "New York Test", "jim@example.com", 11223344, 500.0)));
	}

	/**
	 * Test stringToCalendar.
	 */
	public void testStringToCalendar() {
		String dateString = "2018-6-25";
		Calendar testDate = Controller.stringToCalendar(dateString);
		assertEquals(2018, testDate.get(Calendar.YEAR));
		assertEquals(6, testDate.get(Calendar.MONTH) + 1);
		assertEquals(25, testDate.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Test calendarToString.
	 */
	public void testCalendarToString() {
		Calendar testDate = Calendar.getInstance();
		testDate.set(Calendar.YEAR, 2018);
		testDate.set(Calendar.MONTH, 6 - 1);
		testDate.set(Calendar.DAY_OF_MONTH, 25);
		assertEquals("2018-6-25", Controller.calendarToString(testDate));
	}
}