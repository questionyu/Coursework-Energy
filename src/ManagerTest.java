import junit.framework.TestCase;

/**
 * Title        ManagerTest.java
 * Description  Manager test class.
 */
public class ManagerTest extends TestCase {
	/**
	 * Tested manager.
	 */
	private Manager manager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		FileController fileController = new FileController();
		manager = new Manager(fileController.getCustomersFromFile(), 5.0, 7.0);
	}

	/**
	 * Test getCustomers.
	 */
	public void testGetCustomers() {
		assertNotNull(manager.getCustomers());
	}

	/**
	 * Test getMonitors.
	 */
	public void testGetMonitors() {
		assertNotNull(manager.getMonitors());
	}

	/**
	 * Test getPriceElectricity.
	 */
	public void testGetPriceElectricity() {
		assertEquals(1.0, manager.getPriceElectricity());
	}

	/**
	 * Test getPriceGas.
	 */
	public void testGetPriceGas() {
		assertEquals(1.2, manager.getPriceGas());
	}

	/**
	 * Test getTariffElectricity.
	 */
	public void testGetTariffElectricity() {
		assertEquals(5.0, manager.getTariffElectricity());
	}

	/**
	 * Test getTariffGas.
	 */
	public void testGetTariffGas() {
		assertEquals(7.0, manager.getTariffGas());
	}

	/**
	 * Test setTariffElectricity.
	 */
	public void testSetTariffElectricity() {
		manager.setTariffElectricity(6.0);
		assertEquals(6.0, manager.getTariffElectricity());
	}

	/**
	 * Test setTariffGas.
	 */
	public void testSetTariffGas() {
		manager.setTariffGas(8.0);
		assertEquals(8.0, manager.getTariffGas());
	}
}