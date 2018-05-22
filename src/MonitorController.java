import java.util.ArrayList;

/**
 * Title        Controller.java
 * Description  This class controls the monitor.
 */
class MonitorController {
	/**
	 * The monitor of logged customer;
	 */
	private static Monitor monitor;

	/**
	 * Only for demo. Send readings.
	 */
	static void sendReadings() {
		monitor.sendReadings();
	}

	/**
	 * This function will record the logged customer and start the monitor.
	 *
	 * @param ID The ID of customer.
	 */
	static void login(int ID) {
		for (Monitor logMonitor : ManagerController.getMonitors())
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
		ManagerController.saveCustomer();
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
	 * This function will get the readings data by day. (7 days)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByDay() {
		return Controller.getReadingsData(monitor.getReadingsByDay());
	}

	/**
	 * This function will get the readings data by week. (4 weeks)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByWeek() {
		return Controller.getReadingsData(monitor.getReadingsByWeek());
	}

	/**
	 * This function will get the readings data by month. (3 months)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByMonth() {
		return Controller.getReadingsData(monitor.getReadingsByMonth());
	}

	/**
	 * This function will get the costs of logged monitor.
	 *
	 * @return The costs.
	 */
	static double[] getCosts() {
		return monitor.getCosts();
	}
}
