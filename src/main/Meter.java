import java.util.Timer;
import java.util.TimerTask;

/**
 * Title        Meter.java
 * Description  This class defines a meter.
 */
abstract class Meter {
	/**
	 * Electricity type.
	 */
	static final int ELECTRICITY_METER = 0;

	/**
	 * Gas type.
	 */
	static final int GAS_METER = 1;

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
	 * @param period    The period of updating readings.(Unit: second)
	 * @param meterType The type of meter.
	 */
	Meter(int period, int meterType) {
		this.period = period;
		this.meterType = meterType;
	}

	/**
	 * This function returns a reading.
	 *
	 * @return Reading.
	 */
	double getReading() {
		if (meterType == ELECTRICITY_METER)
			return electricityReading;
		else if (meterType == GAS_METER)
			return gasReading;
		else
			return 0;
	}

	/**
	 * This function will set the readings.
	 *
	 * @param reading The reading.
	 */
	void setReading(double reading) {
		if (meterType == ELECTRICITY_METER)
			electricityReading = reading;
		else if (meterType == GAS_METER)
			gasReading = reading;
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
	void startRecording() {
		recordingTimer = new Timer();
		recordingTask = new RecordingTask();
		recordingTimer.schedule(recordingTask, 0, (period * 1000)); // Let meter run the recordingTask periodically.
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
