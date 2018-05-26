import junit.framework.TestCase;

/**
 * Title        MeterGasTest.java
 * Description  MeterGas test class.
 */
public class MeterGasTest extends TestCase {
	/**
	 * Tested meter.
	 */
	private MeterGas meter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		meter = new MeterGas();
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