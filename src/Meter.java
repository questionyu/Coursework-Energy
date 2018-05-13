import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Title        Meter.java
 * Description  This class defines a meter.
 */
abstract class Meter {
	/**
	 *
	 */
	static final int ELECTRICITY_METER = 0;

	/**
	 *
	 */
	static final int GAS_METER = 1;

	/**
	 * The customer's ID.
	 */
	private int customerID;

	/**
	 * The period of updating readings.
	 */
	private int period;

	/**
	 * The type of meter.
	 */
	private int meterType;

	/**
	 * The reading of electricity.
	 */
	private static double electricityReading;

	/**
	 * The reading of gas.
	 */
	private static double gasReading;

	/**
	 * Recording timer.
	 */
	private Timer recordingTimer;

	/**
	 * Recording task.
	 */
	private RecordingTask recordingTask;

	/**
	 * The constructor function of meter.
	 *
	 * @param customerID The customer's ID.
	 * @param period     The period of updating readings.(Unit: second)
	 * @param meterType  The type of meter.
	 */
	Meter(int customerID, int period, int meterType) {
		this.customerID = customerID;
		this.period = period;
		this.meterType = meterType;
	}

	/**
	 * This function returns a reading.
	 *
	 * @return Reading.
	 */
	double getReading() {
		double result = 0;
		try {
			File file = new File("./readings/" + customerID + suffix + ".txt");
			FileInputStream fileInputStream = new FileInputStream(file);
			Scanner fileScanner = new Scanner(fileInputStream);

			result = Double.parseDouble(fileScanner.nextLine());
			fileScanner.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This function returns a random reading.
	 *
	 * @return Random number smaller than 1
	 */
	private double getRandomReadings() {
		return Math.random();
	}

	/**
	 * Let the meter start the timer.
	 */
	void startRecording() { // This only can run once.
		electricityReading = 0;
		gasReading = 0;
		recordingTimer = new Timer();
		recordingTask = new RecordingTask();
		recordingTimer.schedule(recordingTask, 0, period * 1000); // Let meter run the recordingTask periodically.
	}

	/**
	 * This function will stop recording before some customer be removed.
	 */
	void stopRecording() {
		recordingTask.cancel();
		recordingTimer.cancel();
		recordingTimer.purge();
	}

	/**
	 * This class will record the readings.
	 */
	private class RecordingTask extends TimerTask {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			if (meterType == ELECTRICITY_METER)
				electricityReading += getRandomReadings();
			else if (meterType == GAS_METER)
				gasReading += getRandomReadings();
		}
	}
}
