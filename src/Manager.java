import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Title        Manager.java
 * Description  This class defines the manager system.
 */
public class Manager {
	/**
	 * Manager ID.
	 */
	private static final int MANAGER_ID = 7355608; // A random number.

	/**
	 * All customers of the system.
	 */
	private ArrayList<Customer> customers;

	/**
	 * All monitors of the system.
	 */
	private ArrayList<Monitor> monitors;

	/**
	 * Electricity price. (Unit: Pound)
	 */
	private double priceElectricity = 1;

	/**
	 * Gas price. (Unit: Pound)
	 */
	private double priceGas = 1.2;

	/**
	 * Electricity tariff. (Unit: Penny)
	 */
	private double tariffElectricity;

	/**
	 * Gas tariff. (Unit: Penny)
	 */
	private double tariffGas;

	/**
	 * Constructor function of manager.
	 *
	 * @param customers         All customers of the system.
	 * @param tariffElectricity Electricity tariff.
	 * @param tariffGas         Gas tariff.
	 */
	Manager(ArrayList<Customer> customers, double tariffElectricity, double tariffGas) {
		this.customers = customers;
		this.tariffElectricity = tariffElectricity;
		this.tariffGas = tariffGas;
		monitors = new ArrayList<>();
		for (Customer customer : customers)
			monitors.add(new Monitor(customer));
	}

	/**
	 * This function will check the ID if is manager.
	 *
	 * @param ID The ID which will be checked.
	 * @return True: Is manager. False: Not.
	 */
	boolean isManager(int ID) {
		return ID == MANAGER_ID;
	}

	/**
	 * This function adds a new customer.
	 *
	 * @param customer The new customer.
	 */
	void addCustomer(Customer customer) {
		customers.add(customer);
		monitors.add(new Monitor(customer));
	}

	/**
	 * This function removes a customer.
	 *
	 * @param customer The customer which will be removed.
	 */
	void removeCustomer(Customer customer) {
		for (Monitor monitor : monitors)
			if (monitor.getCustomer().equals(customer)) {
				monitor.stopRecording();
				monitor.deleteReadings();
				monitors.remove(monitor);
			}
		customers.remove(customer);
	}

	/**
	 * This function will generate bills.
	 */
	private void generateBills() {
		for (Customer customer : customers) {
			try {
				File billFile = new File("./bills/" + customer.getID() + ".txt");
				FileWriter billFileWriter = new FileWriter(billFile);

				billFileWriter.write(generateBill(customer));

				billFileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function will generate a bill for specified customer.
	 *
	 * @param customer The customer whose bill will be generated.
	 * @return Bill information.
	 */
	private String generateBill(Customer customer) {
		try {
			File readingsFile = new File("./receivedReadings/" + customer.getID() + ".txt");
			FileInputStream readingsInputStream = new FileInputStream(readingsFile);
			Scanner readingsScanner = new Scanner(readingsInputStream);

			double electricityReadings = Double.parseDouble(readingsScanner.nextLine());
			double gasReadings = Double.parseDouble(readingsScanner.nextLine());

			readingsScanner.close();
			readingsInputStream.close();

			return "Name: " + customer.getName() + "\n" +
					"Address: " + customer.getAddress() + "\n" +
					"Electricity readings: " + electricityReadings + "\n" +
					"Gas readings: " + gasReadings + "\n" +
					"Total bill: " + calculateBill(electricityReadings, gasReadings);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "System error! Please contact manager.";
	}

	/**
	 * This function will calculate bill of some customer.
	 *
	 * @param electricityReadings Electricity readings.
	 * @param gasReadings         Gas readings.
	 * @return Bill.
	 */
	private double calculateBill(double electricityReadings, double gasReadings) {
		return electricityReadings * (priceElectricity + tariffElectricity / 100) + gasReadings * (priceGas + tariffGas / 100);
	}

	/**
	 * This function will send bills.
	 */
	void sendBills() {
		generateBills();
	}

	public void getReadings() {
		// TODO
	}

	public void getBills() {
		// TODO
	}

	/**
	 * Getter function of customers.
	 *
	 * @return All customers of the system.
	 */
	ArrayList<Customer> getCustomers() {
		return customers;
	}

	/**
	 * Getter function of monitors.
	 *
	 * @return All monitors of the system.
	 */
	ArrayList<Monitor> getMonitors() {
		return monitors;
	}

	/**
	 * Getter function of priceElectricity.
	 *
	 * @return The electricity price.
	 */
	double getPriceElectricity() {
		return priceElectricity;
	}

	/**
	 * Getter function of priceGas.
	 *
	 * @return The gas price.
	 */
	double getPriceGas() {
		return priceGas;
	}

	/**
	 * Setter function of tariffElectricity.
	 *
	 * @param tariffElectricity New tariff of electricity.
	 */
	void setTariffElectricity(double tariffElectricity) {
		this.tariffElectricity = tariffElectricity;
	}

	/**
	 * Setter function of tariffGas.
	 *
	 * @param tariffGas New tariff of gas.
	 */
	void setTariffGas(double tariffGas) {
		this.tariffGas = tariffGas;
	}

	/**
	 * Getter function of tariffElectricity.
	 *
	 * @return The electricity tariff.
	 */
	double getTariffElectricity() {
		return tariffElectricity;
	}

	/**
	 * Getter function of tariffGas.
	 *
	 * @return The gas tariff.
	 */
	double getTariffGas() {
		return tariffGas;
	}
}
