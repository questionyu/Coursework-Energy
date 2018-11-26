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
import java.util.Calendar;
import java.util.Scanner;

/**
 * Title        FileController.java
 * Description  This class controls the files.
 */
class FileController implements FileInterface {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] getTariffFromFile() {
		double[] tariff = new double[2];
		try {
			File file = new File("./tariff.txt");
			if (file.createNewFile()) {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(0 + "\n");
				fileWriter.write(0 + "\n");
				fileWriter.close();
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
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Customer> getCustomersFromFile() {
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

					long ID = Long.parseLong(customerElement.getElementsByTagName("ID").item(0).getTextContent());
					String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
					String address = customerElement.getElementsByTagName("address").item(0).getTextContent();
					String email = customerElement.getElementsByTagName("email").item(0).getTextContent();
					double budget = Double.parseDouble(customerElement.getElementsByTagName("budget").item(0).getTextContent());

					customers.add(new Customer(name, address, email, ID, budget));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return customers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeCustomersToFile(ArrayList<Customer> customersList) {
		Document doc;
		Element customers;
		Element customer;
		Element ID;
		Element name;
		Element address;
		Element email;
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

				email = doc.createElement("email");
				email.appendChild(doc.createTextNode(singleCustomer.getEmail()));

				budget = doc.createElement("budget");
				budget.appendChild(doc.createTextNode("" + singleCustomer.getBudget()));

				customer.appendChild(ID);
				customer.appendChild(name);
				customer.appendChild(address);
				customer.appendChild(email);
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
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Readings> getReadingsFromFile(long ID, String folder) {
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

					Calendar date = Controller.stringToCalendar(readingsElement.getElementsByTagName("date").item(0).getTextContent());
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
	 * {@inheritDoc}
	 */
	@Override
	public void writeReadingsToFile(long ID, ArrayList<Readings> readingsList) {
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
				date.appendChild(doc.createTextNode(Controller.calendarToString(singleReading.getDate())));

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
