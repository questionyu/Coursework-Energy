import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Title        ReadingsTest.java
 * Description  Readings test class.
 */
public class ReadingsTest extends TestCase {
	/**
	 * Tested readings.
	 */
	private Readings readings;

	/**
	 * Tested date.
	 */
	private Calendar date;

	/**
	 * Set up function.
	 */
	public void setUp() throws Exception {
		super.setUp();
		date = Calendar.getInstance();
		readings = new Readings(date, 12.0, 10.0);
	}

	/**
	 * Test getDate.
	 */
	public void testGetDate() {
		assertEquals(date, readings.getDate());
	}

	/**
	 * Test getElectricity.
	 */
	public void testGetElectricity() {
		assertEquals(12.0, readings.getElectricity());
	}

	/**
	 * Test getGas.
	 */
	public void testGetGas() {
		assertEquals(10.0, readings.getGas());
	}

	/**
	 * Test setElectricity.
	 */
	public void testSetElectricity() {
		readings.setElectricity(9.0);
		assertEquals(9.0, readings.getElectricity());
	}

	/**
	 * Test setGas.
	 */
	public void testSetGas() {
		readings.setGas(8.0);
		assertEquals(8.0, readings.getGas());
	}
}