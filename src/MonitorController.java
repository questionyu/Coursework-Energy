import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Title        MonitorController.java
 * Description  This class controls the monitor.
 */
class MonitorController {
	/**
	 * The monitor of logged customer;
	 */
	private static Monitor monitor;

	/**
	 * This function sends readings.
	 */
	static void sendReadings() {
		try {
			File sourceFile = new File("./readings/" + monitor.getID() + ".xml");
			File newFile = new File("./receivedReadings/" + monitor.getID() + ".xml");
			Files.copy(sourceFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function will record the logged customer and start the monitor.
	 *
	 * @param ID The ID of customer.
	 */
	static void login(long ID) {
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
		ArrayList<Readings> readings = monitor.getReadingsByDay();
		if (readings.size() == 0)
			return new String[][]{};
		String[][] readingsData = new String[7][];
		for (int i = 0; i < readingsData.length; i++) {
			Readings singleReadings = readings.get(i + 1);
			Readings lastReadings = readings.get(i);
			readingsData[i] = new String[]{Controller.calendarToString(singleReadings.getDate()),
					"" + singleReadings.getElectricity(),
					"" + singleReadings.getGas(),
					"" + ((singleReadings.getElectricity() - lastReadings.getElectricity()) * (ManagerController.getPriceElectricity() + ManagerController.getTariffElectricity() / 100) + (singleReadings.getGas() - lastReadings.getGas()) * (ManagerController.getPriceGas() + ManagerController.getTariffGas() / 100))};
		}
		return readingsData;
	}

	/**
	 * This function will get the readings data by week. (4 weeks)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByWeek() {
		ArrayList<Readings> readings = monitor.getReadingsByWeek();
		if (readings.size() == 0)
			return new String[][]{};
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		double[][] wtfReadings = new double[5][3];
		for (int i = 0; i < wtfReadings.length; i++)
			for (int j = 0; j < wtfReadings[i].length; j++)
				wtfReadings[i][j] = 0;
		for (int i = 0; i < wtfReadings.length; i++) {
			for (Readings singleReadings : readings)
				if (singleReadings.getDate().after(date)) {
					wtfReadings[wtfReadings.length - i - 1][0] = date.get(Calendar.WEEK_OF_YEAR);
					wtfReadings[wtfReadings.length - i - 1][1] += singleReadings.getElectricity();
					wtfReadings[wtfReadings.length - i - 1][2] += singleReadings.getGas();
				}
			date.add(Calendar.WEEK_OF_YEAR, -1);
		}
		String[][] readingsData = new String[4][];
		for (int i = 0; i < readingsData.length; i++)
			readingsData[i] = new String[]{"" + (int) wtfReadings[i + 1][0],
					"" + wtfReadings[i + 1][1],
					"" + wtfReadings[i + 1][2],
					"" + ((wtfReadings[i + 1][1] - wtfReadings[i][1]) * (ManagerController.getPriceElectricity() + ManagerController.getTariffElectricity() / 100) + (wtfReadings[i + 1][2] - wtfReadings[i][2]) * (ManagerController.getPriceGas() + ManagerController.getTariffGas() / 100))};
		return readingsData;
	}

	/**
	 * This function will get the readings data by month. (3 months)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByMonth() {
		ArrayList<Readings> readings = monitor.getReadingsByMonth();
		if (readings.size() == 0)
			return new String[][]{};
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		double[][] wtfReadings = new double[4][3];
		for (int i = 0; i < wtfReadings.length; i++)
			for (int j = 0; j < wtfReadings[i].length; j++)
				wtfReadings[i][j] = 0;
		for (int i = 0; i < wtfReadings.length; i++) {
			for (Readings singleReadings : readings)
				if (singleReadings.getDate().after(date)) {
					wtfReadings[wtfReadings.length - i - 1][0] = date.get(Calendar.MONTH);
					wtfReadings[wtfReadings.length - i - 1][1] += singleReadings.getElectricity();
					wtfReadings[wtfReadings.length - i - 1][2] += singleReadings.getGas();
				}
			date.add(Calendar.MONTH, -1);
		}
		String[][] readingsData = new String[3][];
		for (int i = 0; i < readingsData.length; i++)
			readingsData[i] = new String[]{"" + (int) (wtfReadings[i + 1][0] + 1),
					"" + wtfReadings[i + 1][1],
					"" + wtfReadings[i + 1][2],
					"" + ((wtfReadings[i + 1][1] - wtfReadings[i][1]) * (ManagerController.getPriceElectricity() + ManagerController.getTariffElectricity() / 100) + (wtfReadings[i + 1][2] - wtfReadings[i][2]) * (ManagerController.getPriceGas() + ManagerController.getTariffGas() / 100))};
		return readingsData;
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
