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
	 * This function will get the readings data by day. (no more than 7 days)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByDay() {
		ArrayList<Readings> readings = monitor.getReadingsByDay();
		if (readings.size() == 0)
			return new String[][]{};
		String[][] readingsData = new String[readings.size() - 1][];
		for (int i = 0; i < readingsData.length; i++) {
			Readings newR = readings.get(i + 1);
			Readings oldR = readings.get(i);
			readingsData[i] = new String[]{Controller.calendarToString(newR.getDate()),
					"" + Math.round(newR.getElectricity() * 100) / 100.0,
					"" + Math.round(newR.getGas() * 100) / 100.0,
					"" + Math.round(((newR.getElectricity() - oldR.getElectricity()) * (ManagerController.getPriceElectricity() + ManagerController.getTariffElectricity() / 100) + (newR.getGas() - oldR.getGas()) * (ManagerController.getPriceGas() + ManagerController.getTariffGas() / 100)) * 100) / 100.0};
		}
		return readingsData;
	}

	/**
	 * This function will get the readings data by week. (no more than 4 weeks)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByWeek() {
		ArrayList<Readings> readings = monitor.getReadingsByWeek();
		if (readings.size() == 0)
			return new String[][]{};
		Calendar oldDate = Calendar.getInstance();
		oldDate.add(Calendar.WEEK_OF_YEAR, -4);
		oldDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		oldDate.set(Calendar.HOUR_OF_DAY, 0);
		oldDate.set(Calendar.MINUTE, 0);
		oldDate.set(Calendar.SECOND, 0);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.WEEK_OF_YEAR, -3);
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		ArrayList<double[]> wtfReadings = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			int tempWeek = 0;
			double tempElectricity = 0, tempGas = 0;
			for (Readings singleReadings : readings)
				if (singleReadings.getDate().after(oldDate) && singleReadings.getDate().before(date)) {
					// Make the wtfReadings has the last day's readings of every week.
					tempWeek = date.get(Calendar.WEEK_OF_YEAR);
					tempElectricity = Math.max(tempElectricity, singleReadings.getElectricity());
					tempGas = Math.max(tempGas, singleReadings.getGas());
				}
			if (tempWeek > 0 || tempElectricity > 0 || tempGas > 0)
				wtfReadings.add(new double[]{date.get(Calendar.WEEK_OF_YEAR), tempElectricity, tempGas});
			date.add(Calendar.WEEK_OF_YEAR, 1);
			oldDate.add(Calendar.WEEK_OF_YEAR, 1);
		}
		String[][] readingsData = new String[wtfReadings.size() - 1][];
		for (int i = 0; i < readingsData.length; i++)
			readingsData[i] = new String[]{"" + (int) wtfReadings.get(i + 1)[0],
					"" + Math.round(wtfReadings.get(i + 1)[1] * 100) / 100.0,
					"" + Math.round(wtfReadings.get(i + 1)[2] * 100) / 100.0,
					"" + Math.round(((wtfReadings.get(i + 1)[1] - wtfReadings.get(i)[1]) * (ManagerController.getPriceElectricity() + ManagerController.getTariffElectricity() / 100) + (wtfReadings.get(i + 1)[2] - wtfReadings.get(i)[2]) * (ManagerController.getPriceGas() + ManagerController.getTariffGas() / 100)) * 100) / 100.0};
		return readingsData;
	}

	/**
	 * This function will get the readings data by month. (no more than 3 months)
	 *
	 * @return The readings data.
	 */
	static String[][] getReadingsDataByMonth() {
		ArrayList<Readings> readings = monitor.getReadingsByMonth();
		if (readings.size() == 0)
			return new String[][]{};
		Calendar oldDate = Calendar.getInstance();
		oldDate.add(Calendar.MONTH, -3);
		oldDate.set(Calendar.DAY_OF_MONTH, 1);
		oldDate.set(Calendar.HOUR_OF_DAY, 0);
		oldDate.set(Calendar.MINUTE, 0);
		oldDate.set(Calendar.SECOND, 0);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -2);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		ArrayList<double[]> wtfReadings = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int tempMonth = -1;
			double tempElectricity = 0, tempGas = 0;
			for (Readings singleReadings : readings)
				if (singleReadings.getDate().after(oldDate) && singleReadings.getDate().before(date)) {
					// Make the wtfReadings has the last day's readings of every month.
					tempMonth = date.get(Calendar.MONTH);
					tempElectricity = Math.max(tempElectricity, singleReadings.getElectricity());
					tempGas = Math.max(tempGas, singleReadings.getGas());
				}
			if (tempMonth > -1 || tempElectricity > 0 || tempGas > 0)
				wtfReadings.add(new double[]{date.get(Calendar.MONTH), tempElectricity, tempGas});
			date.add(Calendar.MONTH, 1);
			oldDate.add(Calendar.MONTH, 1);
		}
		String[][] readingsData = new String[wtfReadings.size() - 1][];
		for (int i = 0; i < readingsData.length; i++)
			readingsData[i] = new String[]{"" + (int) (wtfReadings.get(i + 1)[0] + 1),
					"" + Math.round(wtfReadings.get(i + 1)[1] * 100) / 100.0,
					"" + Math.round(wtfReadings.get(i + 1)[2] * 100) / 100.0,
					"" + Math.round(((wtfReadings.get(i + 1)[1] - wtfReadings.get(i)[1]) * (ManagerController.getPriceElectricity() + ManagerController.getTariffElectricity() / 100) + (wtfReadings.get(i + 1)[2] - wtfReadings.get(i)[2]) * (ManagerController.getPriceGas() + ManagerController.getTariffGas() / 100)) * 100) / 100.0};
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
