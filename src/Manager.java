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
	static final int MANAGER_ID = 7355608; // A random number.

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
				monitor.deleteReadings();
				monitors.remove(monitor);
				customers.remove(customer);
				return;
			}
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
			if (now.get(Calendar.DAY_OF_MONTH) == 1)
				ManagerController.bills();
		}
	}
}
