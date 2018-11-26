import junit.framework.TestCase;

/**
 * Title        MeterElectricityTest.java
 * Description  MeterElectricity test class.
 */
public class MeterElectricityTest extends TestCase {
	/**
	 * Tested meter.
	 */
	private MeterElectricity meter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		meter = new MeterElectricity();
		meter.setReading(10.0);
	}

	/**
	 * Test getReading.
	 */
	public void testGetReading() {
		assertEquals(10.0, meter.getReading());
	}

	/**
	 * Test setReading.
	 */
	public void testSetReading() {
		meter.setReading(5.0);
		assertEquals(5.0, meter.getReading());
	}
}