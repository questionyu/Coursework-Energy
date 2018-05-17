import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Title        Controller.java
 * Description  This class controls the energy system.
 */
class Controller {
	/**
	 * The instance of manager.
	 */
	private static Manager manager;

	/**
	 * The monitor of logged customer;
	 */
	private static Monitor monitor;

	/**
	 * A private blank constructor. Prevent other class creating a instance of Controller.
	 */
	private Controller() {
	}

	/**
	 * This function creates the object of manager.
	 */
	static void startManager() {
		double tariff[] = getTariffFromFile();
		manager = new Manager(getCustomersFromFile(), tariff[0], tariff[1]);
	}

	/**
	 * This function starts the main GUI.
	 */
	static void startGUI() {
		new GUIMain();
		GUIMain.welcome();
	}

	/**
	 * This function loads customers.
	 *
	 * @return True: Succeed. False: Failed.
	 */
	static boolean loadCustomers() {
		return getCustomersFromFile() != null;
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
	 * This function adds a customer.
	 *
	 * @param name    The name of new customer.
	 * @param address The address of new customer.
	 * @return True: Succeed. False: Failed(Already exists).
	 */
	static boolean addCustomer(String name, String address) {
		Customer newCustomer = new Customer(name, address, generateRandomID(), 0);
		if (checkDuplicated(newCustomer))
			return false;
		while (checkID(newCustomer.getID()))
			newCustomer.setID(generateRandomID());
		manager.addCustomer(newCustomer);
		saveCustomer();
		return true;
	}

	/**
	 * This function saves the customers.
	 */
	private static void saveCustomer() {
		writeCustomersToFile(manager.getCustomers());
	}

	/**
	 * This function removes a customer.
	 */
	static void removeCustomer(Customer customer) {
		manager.removeCustomer(customer);
		saveCustomer();
	}

	/**
	 * This function checks the name and address of new customer.
	 *
	 * @param newCustomer The new customer which will be checked.
	 * @return True: If exist an old customer whose name and address are both same with new customer.
	 */
	private static boolean checkDuplicated(Customer newCustomer) {
		String newName = newCustomer.getName();
		String newAddress = newCustomer.getAddress();
		for (Customer customer : getCustomers())
			if (customer.getName().equals(newName))
				if (customer.getAddress().equals(newAddress))
					return true;
		return false;
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
	 * This function will record the logged customer and start the monitor.
	 *
	 * @param ID The ID of customer.
	 */
	static void login(int ID) {
		for (Monitor logMonitor : getMonitors())
			if (logMonitor.getID() == ID) {
				monitor = logMonitor;
				monitor.loadReadings();
				monitor.startRecording();
				return;
			}
	}

	/**
	 * This function will logout the logged customer and stop the monitor.
	 */
	static void logout() {
		monitor.stopRecording();
		monitor.saveReadings();
		monitor = null;
	}

	/**
	 * This function gets the type of ID.
	 *
	 * @param ID The ID which need to be checked.
	 * @return Negative: Doesn't exist. Zero: Manager. Positive: Customer.
	 */
	static int getIDType(int ID) {
		if (manager.isManager(ID))
			return 0;
		if (!checkID(ID)) {
			return -1;
		}
		return 1;
	}

	/**
	 * This function checks if the ID exist.
	 *
	 * @param ID The ID which need to be checked.
	 * @return True: Exist. False: Doesn't exist.
	 */
	private static boolean checkID(int ID) {
		for (Customer customer : getCustomers())
			if (customer.getID() == ID)
				return true;
		return false;
	}

	/**
	 * This function generates a random ID.
	 */
	private static int generateRandomID() {
		StringBuilder ticketNo = new StringBuilder();
		for (int i = 0; i < Customer.CUSTOMER_ID_LENGTH; i++) {
			ticketNo.append((int) (1 + Math.random() * 9)); // Generate a random integer [1, 9]
		}
		return Integer.parseInt(ticketNo.toString());
	}

	/**
	 * This function gets tariff from file.
	 *
	 * @return An array about tariff.
	 */
	private static double[] getTariffFromFile() {
		double tariff[] = new double[2];
		try {
			File file = new File("./tariff.txt");
			if (file.createNewFile()) {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(Double.toString(0) + "\n");
				fileWriter.write(Double.toString(0) + "\n");
				fileWriter.close();
				tariff[0] = 0;
				tariff[1] = 0;
			} else {
				FileInputStream fileInputStream = new FileInputStream(file);
				Scanner fileScanner = new Scanner(fileInputStream);

				double tariffElectricity = Double.parseDouble(fileScanner.nextLine());
				double tariffGas = Double.parseDouble(fileScanner.nextLine());
				tariff[0] = tariffElectricity;
				tariff[1] = tariffGas;

				fileScanner.close();
				fileInputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tariff;
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
	 * This function will get the budget of logged customer.
	 *
	 * @return The budget which be set by customer.
	 */
	static double getBudget() {
		return monitor.getBudget();
	}

	/**
	 * This function will update the budget.
	 *
	 * @param budget The new budget of customer.
	 */
	static void updateBudget(double budget) {
		monitor.setBudget(budget);
		saveCustomer();
	}

	/**
	 * This function will get the real-time reading of logged monitor.
	 *
	 * @return The reading.
	 */
	static double[] getReading() {
		return monitor.getReading();
	}

	/**
	 * This function will get the readings. (Historic readings)
	 *
	 * @return The readings of this monitor.
	 */
	static ArrayList<Readings> getReadings() {
		return monitor.getReadings();
	}

	/**
	 * This function will get the readings data.
	 *
	 * @return The readings data.
	 */
	private static String[][] getReadingsData(ArrayList<Readings> readings) {
		if (readings.size() == 0)
			return new String[][]{};
		String[][] readingsData = new String[readings.size()][];
		readingsData[0] = new String[]{calendarToString(readings.get(0).getDate()),
				"" + readings.get(0).getElectricity(),
				"" + readings.get(0).getGas(),
				"" + (readings.get(0).getElectricity() * (getPriceElectricity() + getTariffElectricity() / 100) + readings.get(0).getGas() * (getPriceGas() + getTariffGas() / 100))};
		for (int i = 1; i < readingsData.length; i++) {
			Readings singleReadings = readings.get(i);
			Readings lastReadings = readings.get(i - 1);
			readingsData[i] = new String[]{calendarToString(singleReadings.getDate()),
					"" + singleReadings.getElectricity(),
					"" + singleReadings.getGas(),
					"" + ((singleReadings.getElectricity() - lastReadings.getElectricity()) * (getPriceElectricity() + getTariffElectricity() / 100) + (singleReadings.getGas() - lastReadings.getGas()) * (getPriceGas() + getTariffGas() / 100))};
		}
		return readingsData;
	}

	/**
	 * This function will get the readings data by day. (7 days)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByDay() {
		return getReadingsData(monitor.getReadingsByDay());
	}

	/**
	 * This function will get the readings data by week. (4 weeks)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByWeek() {
		return getReadingsData(monitor.getReadingsByWeek());
	}

	/**
	 * This function will get the readings data by month. (3 months)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByMonth() {
		return getReadingsData(monitor.getReadingsByMonth());
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
			double[] bill = manager.generateBill(monitor.getCustomer());
			data[i] = new String[]{monitor.getCustomer().getName(), "" + bill[0], "" + bill[1], "" + bill[2]};
		}
		return data;
	}

	/**
	 * This function will get the costs of logged monitor.
	 *
	 * @return The costs.
	 */
	static double[] getCosts() {
		return monitor.getCosts();
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

	/**
	 * This function converts string to calendar.
	 *
	 * @param dateString The string contains the information of date.
	 * @return The date instance of the converted time.
	 */
	private static Calendar stringToCalendar(String dateString) {
		Date newDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar date = Calendar.getInstance();
		date.setTime(newDate);
		return date;
	}

	/**
	 * This function converts calendar to string.
	 *
	 * @param date The calendar instance.
	 * @return The date string.
	 */
	private static String calendarToString(Calendar date) {
		return String.valueOf(date.get(Calendar.YEAR)) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE);
	}

	/**
	 * This function read customers from xml and save to an ArrayList, and return it.
	 *
	 * @return The ArrayList which contains all customers.
	 */
	private static ArrayList<Customer> getCustomersFromFile() {
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			File file = new File("./Customers.xml");
			if (file.createNewFile()) {
				System.out.println("Customers file not exist. Created new one.");
				return customers;
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList customerList = doc.getElementsByTagName("customer");

			for (int i = 0; i < customerList.getLength(); i++) {
				Node customerNode = customerList.item(i);
				if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
					Element customerElement = (Element) customerNode;

					int ID = Integer.parseInt(customerElement.getElementsByTagName("ID").item(0).getTextContent());
					String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
					String address = customerElement.getElementsByTagName("address").item(0).getTextContent();
					double budget = Double.parseDouble(customerElement.getElementsByTagName("budget").item(0).getTextContent());

					customers.add(new Customer(name, address, ID, budget));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return customers;
	}

	/**
	 * This function saves ArrayList of customers to xml file.
	 *
	 * @param customersList The ArrayList which contains all customers which you want to save to file.
	 */
	private static void writeCustomersToFile(ArrayList<Customer> customersList) {
		Document doc;
		Element customers;
		Element customer;
		Element ID;
		Element name;
		Element address;
		Element budget;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			doc = dbBuilder.newDocument();

			customers = doc.createElement("customers");
			for (Customer singleCustomer : customersList) {
				customer = doc.createElement("customer");

				ID = doc.createElement("ID");
				ID.appendChild(doc.createTextNode("" + singleCustomer.getID()));

				name = doc.createElement("name");
				name.appendChild(doc.createTextNode(singleCustomer.getName()));

				address = doc.createElement("address");
				address.appendChild(doc.createTextNode(singleCustomer.getAddress()));

				budget = doc.createElement("budget");
				budget.appendChild(doc.createTextNode("" + singleCustomer.getBudget()));

				customer.appendChild(ID);
				customer.appendChild(name);
				customer.appendChild(address);
				customer.appendChild(budget);

				customers.appendChild(customer);
			}
			doc.appendChild(customers);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./Customers.xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the indent. For better looks.
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function read readings from xml and save to an ArrayList, and return it.
	 *
	 * @param ID     The ID of customer.
	 * @param folder The folder which contains readings files.
	 * @return The ArrayList which contains readings.
	 */
	static ArrayList<Readings> getReadingsFromFile(int ID, String folder) {
		ArrayList<Readings> readings = new ArrayList<>();
		try {
			File file = new File("./" + folder + "/" + ID + ".xml");
			if (folder.equals("readings") && file.createNewFile()) {
				System.out.println("Readings file not exist. Created new one.");
				return readings;
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList readingsList = doc.getElementsByTagName("reading");

			for (int i = 0; i < readingsList.getLength(); i++) {
				Node readingsNode = readingsList.item(i);
				if (readingsNode.getNodeType() == Node.ELEMENT_NODE) {
					Element readingsElement = (Element) readingsNode;

					Calendar date = stringToCalendar(readingsElement.getElementsByTagName("date").item(0).getTextContent());
					double electricity = Double.parseDouble(readingsElement.getElementsByTagName("electricity").item(0).getTextContent());
					double gas = Double.parseDouble(readingsElement.getElementsByTagName("gas").item(0).getTextContent());

					readings.add(new Readings(date, electricity, gas));
				}
			}
		} catch (Exception e) {
			return new ArrayList<>();
		}
		return readings;
	}

	/**
	 * This function saves readings of monitors to xml file.
	 *
	 * @param ID           The ID of customer of monitor.
	 * @param readingsList The readings.
	 */
	static void writeReadingsToFile(int ID, ArrayList<Readings> readingsList) {
		Document doc;
		Element readings;
		Element reading;
		Element date;
		Element electricity;
		Element gas;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			doc = dbBuilder.newDocument();

			readings = doc.createElement("readings");
			for (Readings singleReading : readingsList) {
				reading = doc.createElement("reading");

				date = doc.createElement("date");
				date.appendChild(doc.createTextNode(calendarToString(singleReading.getDate())));

				electricity = doc.createElement("electricity");
				electricity.appendChild(doc.createTextNode("" + singleReading.getElectricity()));

				gas = doc.createElement("gas");
				gas.appendChild(doc.createTextNode("" + singleReading.getGas()));

				reading.appendChild(date);
				reading.appendChild(electricity);
				reading.appendChild(gas);

				readings.appendChild(reading);
			}
			doc.appendChild(readings);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./readings/" + ID + ".xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the indent. For better looks.
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
