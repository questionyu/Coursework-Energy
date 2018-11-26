import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Title        FileControllerTest.java
 * Description  FileController test class.
 */
public class FileControllerTest extends TestCase {
	/**
	 * Tested fileController.
	 */
	private FileController fileController;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		fileController = new FileController();
	}

	/**
	 * Test getTariffFromFile.
	 */
	public void testGetTariffFromFile() {
		assertNotNull(fileController.getTariffFromFile());
	}

	/**
	 * Test getCustomersFromFile.
	 */
	public void testGetCustomersFromFile() {
		assertNotNull(fileController.getCustomersFromFile());
	}

	/**
	 * Test getReadingsFromFile.
	 */
	public void testGetReadingsFromFile() {
		assertNotNull(fileController.getReadingsFromFile(12345678, "readings"));
	}

	/**
	 * Test writeReadingsToFile.
	 */
	public void testWriteReadingsToFile() {
		ArrayList<Readings> testReadings = new ArrayList<>();
		testReadings.add(new Readings(Calendar.getInstance(), 0.0, 0.0));
		fileController.writeReadingsToFile(11223344, testReadings);
		File testFile = new File("./readings/11223344.xml");
		assertTrue(testFile.exists());
		testFile.delete();
	}
}