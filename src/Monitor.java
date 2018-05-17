import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Title        Monitor.java
 * Description  This class defines a monitor.
 */
class Monitor {
	/**
	 * The customer of monitor.
	 */
	private Customer customer;

	/**
	 * The electricity meter of monitor.
	 */
	private MeterElectricity meterElectricity;

	/**
	 * The gas meter of monitor.
	 */
	private MeterGas meterGas;

	/**
	 * The readings of monitor.
	 */
	private ArrayList<Readings> readings;

	/**
	 * Recording timer.
	 */
	private Timer recordingTimer;

	/**
	 * Recording task.
	 */
	private RecordingTask recordingTask;

	/**
	 * Sending timer.
	 */
	private Timer sendingTimer;

	/**
	 * Sending task.
	 */
	private SendingTask sendingTask;

	/**
	 * The constructor function of monitor.
	 *
	 * @param customer The customer of monitor.
	 */
	Monitor(Customer customer) {
		this.customer = customer;
		meterElectricity = new MeterElectricity();
		meterGas = new MeterGas();
	}

	/**
	 * This function will get the real-time reading.
	 *
	 * @return The reading of this monitor.
	 */
	double[] getReading() {
		double reading[] = new double[2];
		reading[0] = meterElectricity.getReading();
		reading[1] = meterGas.getReading();
		return reading;
	}

	/**
	 * This function will get the readings. (Historic readings)
	 *
	 * @return The readings of this monitor.
	 */
	ArrayList<Readings> getReadings() {
		return readings;
	}

	/**
	 * This function will return readings by day. (7 days)
	 *
	 * @return The readings of 7 days.
	 */
	ArrayList<Readings> getReadingsByDay() {
		if (readings.size() == 0)
			return readings;
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, -7);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		ArrayList<Readings> newReadings = new ArrayList<>();
		for (Readings singleReadings : readings)
			if (singleReadings.getDate().after(date))
				newReadings.add(singleReadings);
		return newReadings;
	}

	/**
	 * This function will return readings by week. (4 weeks)
	 *
	 * @return The readings of 4 weeks.
	 */
	ArrayList<Readings> getReadingsByWeek() {
		if (readings.size() == 0)
			return readings;
		Calendar date = Calendar.getInstance();
		date.add(Calendar.WEEK_OF_YEAR, -7);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		ArrayList<Readings> newReadings = new ArrayList<>();
		for (Readings singleReadings : readings)
			if (singleReadings.getDate().after(date))
				newReadings.add(singleReadings);
		return newReadings;
	}

	/**
	 * This function will return readings by month. (3 months)
	 *
	 * @return The readings of 3 months.
	 */
	ArrayList<Readings> getReadingsByMonth() {
		if (readings.size() == 0)
			return readings;
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -7);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		ArrayList<Readings> newReadings = new ArrayList<>();
		for (Readings singleReadings : readings)
			if (singleReadings.getDate().after(date))
				newReadings.add(singleReadings);
		return newReadings;
	}

	/**
	 * This function will get the costs.
	 *
	 * @return The costs of this monitor.
	 */
	double[] getCosts() {
		double costs[] = new double[2];
		costs[0] = meterElectricity.getReading() * (Controller.getPriceElectricity() + Controller.getTariffElectricity() / 100);
		costs[1] = meterGas.getReading() * (Controller.getPriceGas() + Controller.getTariffGas() / 100);
		return costs;
	}

	/**
	 * This function sends readings.
	 */
	void sendReadings() {
		try {
			File sourceFile = new File("./readings/" + customer.getID() + ".xml");
			File newFile = new File("./receivedReadings/" + customer.getID() + ".xml");
			Files.copy(sourceFile.toPath(), newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function will load readings.
	 */
	void loadReadings() {
		readings = Controller.getReadingsFromFile(getID());
		if (readings.size() == 0) {
			meterElectricity.setReading(0);
			meterGas.setReading(0);
			readings.add(new Readings(Calendar.getInstance(), 0, 0));
		} else {
			Readings latestReadings = readings.get(readings.size() - 1);
			meterElectricity.setReading(latestReadings.getElectricity());
			meterGas.setReading(latestReadings.getGas());

			Calendar now = Calendar.getInstance();
			Calendar dateCalendar = latestReadings.getDate();
			if (now.get(Calendar.YEAR) != dateCalendar.get(Calendar.YEAR) || now.get(Calendar.DAY_OF_YEAR) != dateCalendar.get(Calendar.DAY_OF_YEAR)) // 当天的记录是否存在
				readings.add(new Readings(Calendar.getInstance(), latestReadings.getElectricity(), latestReadings.getGas()));

		}

	}

	/**
	 * This function will start recording.
	 */
	void startRecording() {
		meterElectricity.startRecording();
		meterGas.startRecording();

		recordingTimer = new Timer();
		recordingTask = new RecordingTask();
		recordingTimer.schedule(recordingTask, 0, (10 * 60 * 1000)); // Let monitor run the recordingTask periodically.

		sendingTimer = new Timer();
		sendingTask = new SendingTask();
		sendingTimer.schedule(sendingTask, 0, (24 * 60 * 60 * 1000)); // Once a day.
	}

	/**
	 * This function will stop recording.
	 */
	void stopRecording() {
		sendingTask.cancel();
		sendingTimer.cancel();
		sendingTimer.purge();

		recordingTask.cancel();
		recordingTimer.cancel();
		recordingTimer.purge();

		meterElectricity.stopRecording();
		meterGas.stopRecording();
	}

	/**
	 * This function will save readings into file.
	 */
	void saveReadings() {
		Readings latestReadings = readings.get(readings.size() - 1);
		latestReadings.setElectricity(meterElectricity.getReading());
		latestReadings.setGas(meterGas.getReading());
		Controller.writeReadingsToFile(getID(), readings);
	}

	/**
	 * This function will delete reading of this monitor.
	 */
	void deleteReadings() { // TODO Need test
		try {
			File readingsFile = new File("./readings/" + customer.getID() + ".xml");
			readingsFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter function of customer.
	 *
	 * @return The customer of monitor.
	 */
	Customer getCustomer() {
		return customer;
	}

	/**
	 * Getter function of budget.
	 *
	 * @return The budget of customer.
	 */
	double getBudget() {
		return customer.getBudget();
	}

	/**
	 * The setter function of budget.
	 *
	 * @param budget The new budget.
	 */
	void setBudget(double budget) {
		customer.setBudget(budget);
	}

	/**
	 * Getter function of ID.
	 *
	 * @return The ID of customer.
	 */
	int getID() {
		return customer.getID();
	}

	/**
	 * This class will run the saveReadings function.
	 */
	private class RecordingTask extends TimerTask {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			saveReadings();
		}
	}

	/**
	 * This class will run the sendReadings function.
	 */
	private class SendingTask extends TimerTask {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			Calendar now = Calendar.getInstance();
			if (now.get(Calendar.DAY_OF_MONTH) == 1) // Send readings in the first day of the month.
				sendReadings();
		}
	}
}
