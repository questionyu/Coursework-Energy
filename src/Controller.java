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
import java.util.ArrayList;
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
	 * A private blank constructor. Prevent other class creating a instance of Controller.
	 */
	private Controller() {
	}

	/**
	 * This function creates the object of manager.
	 */
	static void startManager() {
		double tariff[] = getTariffFromFile();
		manager = new Manager(getCustomerFromFile(), tariff[0], tariff[1]);
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
		return getCustomerFromFile() != null;
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
		Customer newCustomer = new Customer(name, address, generateRandomID());
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
		writeCustomerToFile(manager.getCustomers());
	}

	/**
	 * This function removes a customer.
	 */
	static void removeCustomer(Customer customer) {
		manager.removeCustomer(customer);
		// TODO 移除readings文件
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
	 * This function will let monitors to send readings.
	 */
	static void sendReadings() {
		// TODO Temporarily codes
		for (Monitor monitor : manager.getMonitors())
			monitor.sendReadings();
	}

	/**
	 * This function will let manager to generate bills.
	 */
	static void generateBills() {
		// TODO Temporarily codes
		manager.generateBills();
	}

	/**
	 * This function read films form xml and save to an ArrayList, and return it.
	 *
	 * @return the ArrayList which contains all customers.
	 */
	private static ArrayList<Customer> getCustomerFromFile() {
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("Customers.xml"));
			NodeList customerList = doc.getElementsByTagName("customer");

			for (int i = 0; i < customerList.getLength(); i++) {
				Node customerNode = customerList.item(i);
				if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
					Element customerElement = (Element) customerNode;

					int ID = Integer.parseInt(customerElement.getElementsByTagName("ID").item(0).getTextContent());
					String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
					String address = customerElement.getElementsByTagName("address").item(0).getTextContent();

					customers.add(new Customer(name, address, ID));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	/**
	 * This function saves ArrayList of customers to xml file.
	 *
	 * @param customerList The ArrayList which contains all customers which you want to save to file.
	 */
	private static void writeCustomerToFile(ArrayList<Customer> customerList) {
		Document doc;
		Element customers;
		Element customer;
		Element ID;
		Element name;
		Element address;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			doc = dbBuilder.newDocument();

			customers = doc.createElement("customers");
			for (Customer singleCustomer : customerList) {
				customer = doc.createElement("customer");

				ID = doc.createElement("ID");
				ID.appendChild(doc.createTextNode("" + singleCustomer.getID()));

				name = doc.createElement("name");
				name.appendChild(doc.createTextNode(singleCustomer.getName()));

				address = doc.createElement("address");
				address.appendChild(doc.createTextNode(singleCustomer.getAddress()));

				customer.appendChild(ID);
				customer.appendChild(name);
				customer.appendChild(address);

				customers.appendChild(customer);
			}
			doc.appendChild(customers);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("Customers.xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set the indent. For better looks.
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
