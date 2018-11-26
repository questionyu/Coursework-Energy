import junit.framework.TestCase;

/**
 * Title        ManagerControllerTest.java
 * Description  ManagerController test class.
 */
public class ManagerControllerTest extends TestCase {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ManagerController.startManager();
	}

	/**
	 * Test isManager.
	 */
	public void testIsManager() {
		assertTrue(ManagerController.isManager(Manager.MANAGER_ID));
		assertFalse(ManagerController.isManager(12345678));
	}

	/**
	 * Test addCustomer.
	 */
	public void testAddCustomer() {
		assertTrue(ManagerController.addCustomer("testName", "testAddress", "test@example.com"));
		ManagerController.removeCustomer(ManagerController.getCustomers().get(ManagerController.getCustomers().size() - 1).getID());
	}

	/**
	 * Test getCustomerList.
	 */
	public void testGetCustomerList() {
		assertNotNull(ManagerController.getCustomerList());
	}

	/**
	 * Test getReadingsAndBills.
	 */
	public void testGetReadingsAndBills() {
		assertNotNull(ManagerController.getReadingsAndBills());
	}

	/**
	 * Test getMonitors.
	 */
	public void testGetMonitors() {
		assertNotNull(ManagerController.getMonitors());
	}

	/**
	 * Test getCustomers.
	 */
	public void testGetCustomers() {
		assertNotNull(ManagerController.getCustomers());
	}

	/**
	 * Test getPriceElectricity.
	 */
	public void testGetPriceElectricity() {
		assertEquals(1.0, ManagerController.getPriceElectricity());
	}

	/**
	 * Test getPriceGas.
	 */
	public void testGetPriceGas() {
		assertEquals(1.2, ManagerController.getPriceGas());
	}

	/**
	 * Test getTariffElectricity.
	 */
	public void testGetTariffElectricity() {
		assertEquals(14.6, ManagerController.getTariffElectricity());
	}

	/**
	 * Test getTariffGas.
	 */
	public void testGetTariffGas() {
		assertEquals(3.88, ManagerController.getTariffGas());
	}
}