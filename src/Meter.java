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
	 * The customer's ID.
	 */
	private int customerID;

	/**
	 * The period of updating readings.
	 */
	private int period;

	/**
	 * The recording files' suffix.
	 */
	private String suffix;

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
	 * @param suffix     The recording files' suffix.
	 */
	Meter(int customerID, int period, String suffix) {
		this.customerID = customerID;
		this.period = period;
		this.suffix = suffix;
		initReadings();
		startRecording();
	}

	/**
	 * This function will check the readings files if exist. If not then will create files.
	 */
	private void initReadings() {
		try {
			File file = new File("./readings/" + customerID + suffix + ".txt");
			if (file.createNewFile()) {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(Double.toString(0));
				fileWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	private void startRecording() { // This only can run once.
		recordingTimer = new Timer();
		recordingTask = new RecordingTask();
		recordingTimer.schedule(recordingTask, 0, period * 1000); // Let meter run the sleepTask periodically.
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
			try {
				File file = new File("./readings/" + customerID + suffix + ".txt");
				FileInputStream fileInputStream = new FileInputStream(file);
				Scanner fileScanner = new Scanner(fileInputStream);

				double result = Double.parseDouble(fileScanner.nextLine());
				fileScanner.close();
				fileInputStream.close();

				FileWriter fileWriter = new FileWriter(file);

				result += getRandomReadings();
				fileWriter.write(Double.toString(result));

				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
