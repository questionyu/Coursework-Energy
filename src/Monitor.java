import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
	 * This function will get the readings.
	 *
	 * @return The readings of this monitor.
	 */
	double[] getReadings() {
		double readings[] = new double[2];
		readings[0] = meterElectricity.getReading();
		readings[1] = meterGas.getReading();
		return readings;
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
	void sendReadings() { //TODO
		try {
			File electricityReadingsFile = new File("./readings/" + customer.getID() + "electricity.txt");
			File gasReadingsFile = new File("./readings/" + customer.getID() + "gas.txt");
			FileInputStream electricityInputStream = new FileInputStream(electricityReadingsFile);
			FileInputStream gasInputStream = new FileInputStream(gasReadingsFile);
			Scanner electricityScanner = new Scanner(electricityInputStream);
			Scanner gasScanner = new Scanner(gasInputStream);

			String electricityReadings = electricityScanner.nextLine();
			String gasReadings = gasScanner.nextLine();

			electricityScanner.close();
			gasScanner.close();
			electricityInputStream.close();
			gasInputStream.close();

			File sendReadingsFile = new File("./receivedReadings/" + customer.getID() + ".txt");
			FileWriter sendReadingsFileWriter = new FileWriter(sendReadingsFile);

			sendReadingsFileWriter.write(electricityReadings + "\n");
			sendReadingsFileWriter.write(gasReadings + "\n");

			sendReadingsFileWriter.close();
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
	}

	/**
	 * This function will stop recording.
	 */
	void stopRecording() {
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
}
