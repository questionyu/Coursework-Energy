import junit.framework.TestCase;

/**
 * Title        IDGeneratorTest.java
 * Description  IDGenerator test class.
 */
public class IDGeneratorTest extends TestCase {
	/**
	 * Tested IDGenerator.
	 */
	private IDGenerator testGenerator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		testGenerator = new IDGenerator();
	}

	/**
	 * Test get.
	 */
	public void testGet() {
		long ID = testGenerator.get(8);
		assertTrue(ID > 9999999);
		assertTrue(ID < 100000000);
	}
}