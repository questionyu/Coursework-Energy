import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Title        ManagerController.java
 * Description  This class controls the manager.
 */
class ManagerController {
	/**
	 * The instance of fileController.
	 */
	private static FileController fileController = new FileController();

	/**
	 * The instance of IDGenerator.
	 */
	private static IDGenerator IDGenerator = new IDGenerator();

	/**
	 * The instance of manager.
	 */
	private static Manager manager;

	/**
	 * This function creates the object of manager.
	 */
	static void startManager() {
		double tariff[] = fileController.getTariffFromFile();
		manager = new Manager(fileController.getCustomersFromFile(), tariff[0], tariff[1]);
	}

	/**
	 * Only for demo. Generate and send bills.
	 */
	static void bills() {
		generateBills();
		sendBills();
	}

	/**
	 * This function will check the ID if is manager.
	 *
	 * @param ID The ID which will be checked.
	 * @return True: Is manager. False: Not.
	 */
	static boolean isManager(long ID) {
		return ID == Manager.MANAGER_ID;
	}

	/**
	 * This function adds a customer.
	 *
	 * @param name    The name of new customer.
	 * @param address The address of new customer.
	 * @param email   The email address of new customer.
	 * @return True: Succeed. False: Failed(Already exists).
	 */
	static boolean addCustomer(String name, String address, String email) {
		Customer newCustomer = new Customer(name, address, email, IDGenerator.get(Customer.CUSTOMER_ID_LENGTH), 0);
		if (Controller.checkDuplicated(newCustomer))
			return false;
		while (Controller.checkID(newCustomer.getID()))
			newCustomer.setID(IDGenerator.get(Customer.CUSTOMER_ID_LENGTH));
		manager.addCustomer(newCustomer);
		saveCustomer();
		return true;
	}

	/**
	 * This function removes a customer.
	 *
	 * @param ID Customer's ID.
	 */
	static void removeCustomer(Long ID) {
		for (Customer customer : getCustomers())
			if (customer.getID() == ID) {
				manager.removeCustomer(customer);
				break;
			}
		saveCustomer();
	}

	/**
	 * This function saves the customers.
	 */
	static void saveCustomer() {
		fileController.writeCustomersToFile(manager.getCustomers());
	}

	/**
	 * This function will start timer.
	 */
	static void startManagerTimer() {
		manager.startTimer();
	}

	/**
	 * This function will stop timer.
	 */
	static void stopManagerTimer() {
		manager.stopTimer();
	}

	/**
	 * This function will update the tariff.
	 *
	 * @param newTariffElectricity The new tariff of electricity.
	 * @param newTariffGas         The new tariff of gas.
	 */
	static void updateTariff(double newTariffElectricity, double newTariffGas) {
		manager.setTariffElectricity(newTariffElectricity);
		manager.setTariffGas(newTariffGas);
		try {
			File file = new File("./tariff.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(Double.toString(newTariffElectricity) + "\n");
			fileWriter.write(Double.toString(newTariffGas) + "\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function will get the readings and bill of all customers.
	 *
	 * @return The readings and bills.
	 */
	static String[][] getReadingsAndBills() {
		if (getMonitors().size() == 0)
			return new String[][]{};
		String[][] data = new String[getMonitors().size()][];
		for (int i = 0; i < data.length; i++) {
			Monitor monitor = getMonitors().get(i);
			double[] bill = generateBill(monitor.getCustomer());
			data[i] = new String[]{monitor.getCustomer().getName(), "" + bill[0], "" + bill[1], "" + bill[2]};
		}
		return data;
	}

	/**
	 * This function will calculate readings and bill for specified customer.
	 *
	 * @param customer The customer whose bill will be generated.
	 * @return Bill information.
	 */
	private static double[] generateBill(Customer customer) { // TODO 1,2月的情况需要考虑
		ArrayList<Readings> readings = fileController.getReadingsFromFile(customer.getID(), "receivedReadings");
		Calendar now = Calendar.getInstance();
		int thisMonth = now.get(Calendar.MONTH);
		ArrayList<Readings> lastMonthReadings = new ArrayList<>();
		ArrayList<Readings> theMonthBeforeLastReadings = new ArrayList<>();
		for (Readings singleReadings : readings) {
			if (singleReadings.getDate().get(Calendar.MONTH) == (thisMonth - 1))
				lastMonthReadings.add(singleReadings);
			if (singleReadings.getDate().get(Calendar.MONTH) == (thisMonth - 2))
				theMonthBeforeLastReadings.add(singleReadings);
		}
		double electricityReadings;
		double gasReadings;
		if (lastMonthReadings.size() == 0)
			return new double[]{0, 0, 0};
		Readings lastMonthReading = lastMonthReadings.get(lastMonthReadings.size() - 1);
		if (theMonthBeforeLastReadings.size() == 0) {
			electricityReadings = lastMonthReading.getElectricity();
			gasReadings = lastMonthReading.getGas();
		} else {
			Readings theMonthBeforeLastReading = theMonthBeforeLastReadings.get(theMonthBeforeLastReadings.size() - 1);
			electricityReadings = lastMonthReading.getElectricity() - theMonthBeforeLastReading.getElectricity();
			gasReadings = lastMonthReading.getGas() - theMonthBeforeLastReading.getGas();
		}
		double total = electricityReadings * (getPriceElectricity() + getTariffElectricity() / 100) + gasReadings * (getPriceGas() + getTariffGas() / 100);
		return new double[]{electricityReadings, gasReadings, total};
	}

	/**
	 * This function will generate bills.
	 */
	private static void generateBills() {
		for (Customer customer : getCustomers()) {
			try {
				File billFile = new File("./bills/" + customer.getID() + ".txt");
				FileWriter billFileWriter = new FileWriter(billFile);

				billFileWriter.write(generateBillString(customer));

				billFileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function will generate a bill string for specified customer.
	 *
	 * @param customer The customer whose bill will be generated.
	 * @return Bill information.
	 */
	private static String generateBillString(Customer customer) {
		Calendar now = Calendar.getInstance();
		double[] bill = generateBill(customer);
		return "Name: " + customer.getName() + "\n" +
				"Address: " + customer.getAddress() + "\n" +
				"Time: " + now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "\n" +
				"Electricity readings: " + bill[0] + "\n" +
				"Gas readings: " + bill[1] + "\n" +
				"Total bill: " + bill[2];
	}

	/**
	 * This function will send bills.
	 */
	private static void sendBills() {
		for (Customer customer : manager.getCustomers()) {
			try {
				File sourceFile = new File("./bills/" + customer.getID() + ".txt");
				File newFile = new File("./receivedBills/" + customer.getID() + ".txt");
				Files.copy(sourceFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Getter function of monitors.
	 *
	 * @return All monitors of the system.
	 */
	static ArrayList<Monitor> getMonitors() {
		return manager.getMonitors();
	}

	/**
	 * Getter function of customers.
	 *
	 * @return All customers of the system.
	 */
	static ArrayList<Customer> getCustomers() {
		return manager.getCustomers();
	}

	/**
	 * This function will get the electricity price from manager.
	 *
	 * @return The electricity price.
	 */
	static double getPriceElectricity() {
		return manager.getPriceElectricity();
	}

	/**
	 * This function will get the gas price from manager.
	 *
	 * @return The gas price.
	 */
	static double getPriceGas() {
		return manager.getPriceGas();
	}

	/**
	 * This function will get the electricity tariff from manager.
	 *
	 * @return The electricity tariff.
	 */
	static double getTariffElectricity() {
		return manager.getTariffElectricity();
	}

	/**
	 * This function will get the gas tariff from manager.
	 *
	 * @return The gas tariff.
	 */
	static double getTariffGas() {
		return manager.getTariffGas();
	}
}
