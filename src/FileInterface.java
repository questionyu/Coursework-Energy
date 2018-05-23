import java.util.ArrayList;

/**
 * Title        FileInterface.java
 * Description  File controller interface.
 */
interface FileInterface {
	/**
	 * This function gets tariff from file.
	 *
	 * @return An array about tariff.
	 */
	double[] getTariffFromFile();

	/**
	 * This function read customers from xml and save to an ArrayList, and return it.
	 *
	 * @return The ArrayList which contains all customers.
	 */
	ArrayList<Customer> getCustomersFromFile();

	/**
	 * This function saves ArrayList of customers to xml file.
	 *
	 * @param customersList The ArrayList which contains all customers which you want to save to file.
	 */
	void writeCustomersToFile(ArrayList<Customer> customersList);

	/**
	 * This function read readings from xml and save to an ArrayList, and return it.
	 *
	 * @param ID     The ID of customer.
	 * @param folder The folder which contains readings files.
	 * @return The ArrayList which contains readings.
	 */
	ArrayList<Readings> getReadingsFromFile(long ID, String folder);

	/**
	 * This function saves readings of monitors to xml file.
	 *
	 * @param ID           The ID of customer of monitor.
	 * @param readingsList The readings.
	 */
	void writeReadingsToFile(long ID, ArrayList<Readings> readingsList);
}
