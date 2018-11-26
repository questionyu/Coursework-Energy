import junit.framework.TestCase;

/**
 * Title        CustomerTest.java
 * Description  Customer test class.
 */
public class CustomerTest extends TestCase {
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
		customer = new Customer("testName", "testAddress", "test@example.com", 11223344, 500.0);
	}

	/**
	 * Test getID.
	 */
	public void testGetID() {
		assertEquals(11223344, customer.getID());
	}

	/**
	 * Test setID.
	 */
	public void testSetID() {
		customer.setID(44332211);
		assertEquals(44332211, customer.getID());
	}

	/**
	 * Test getName.
	 */
	public void testGetName() {
		assertEquals("testName", customer.getName());
	}

	/**
	 * Test getAddress.
	 */
	public void testGetAddress() {
		assertEquals("testAddress", customer.getAddress());
	}

	/**
	 * Test getEmail.
	 */
	public void testGetEmail() {
		assertEquals("test@example.com", customer.getEmail());
	}

	/**
	 * Test getBudget.
	 */
	public void testGetBudget() {
		assertEquals(500.0, customer.getBudget());
	}

	/**
	 * Test setBudget.
	 */
	public void testSetBudget() {
		customer.setBudget(300.0);
		assertEquals(300.0, customer.getBudget());
	}
}