import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Title        Manager.java
 * Description  This class defines the manager system.
 */
class Manager {
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
	 * Bills timer.
	 */
	private Timer billsTimer;

	/**
	 * Bills task.
	 */
	private BillsTask billsTask;

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
	 * This function will start timer.
	 */
	void startTimer() {
		billsTimer = new Timer();
		billsTask = new BillsTask();
		billsTimer.schedule(billsTask, 0, (24 * 60 * 60 * 1000)); // Once a day.
	}

	/**
	 * This function will stop timer.
	 */
	void stopTimer() {
		billsTask.cancel();
		billsTimer.cancel();
		billsTimer.purge();
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
	void generateBills() {
		for (Customer customer : customers) {
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
	private String generateBillString(Customer customer) {
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
	 * This function will calculate readings and bill for specified customer.
	 *
	 * @param customer The customer whose bill will be generated.
	 * @return Bill information.
	 */
	double[] generateBill(Customer customer) { // TODO 1,2月的情况需要考虑
		ArrayList<Readings> readings = Controller.getReadingsFromFile(customer.getID(), "receivedReadings");
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
		double total = electricityReadings * (priceElectricity + tariffElectricity / 100) + gasReadings * (priceGas + tariffGas / 100);
		return new double[]{electricityReadings, gasReadings, total};
	}

	/**
	 * This function will send bills.
	 */
	void sendBills() {
		for (Customer customer : customers) {
			try {
				File sourceFile = new File("./bills/" + customer.getID() + ".txt");
				File newFile = new File("./receivedBills/" + customer.getID() + ".txt");
				Files.copy(sourceFile.toPath(), newFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

	/**
	 * This class will run the generateBills function.
	 */
	private class BillsTask extends TimerTask {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			Calendar now = Calendar.getInstance();
			if (now.get(Calendar.DAY_OF_MONTH) == 1) {
				generateBills();
				sendBills();
			}
		}
	}
}
