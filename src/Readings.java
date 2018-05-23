import java.util.Calendar;

/**
 * Title        Readings.java
 * Description  This class defines the readings.
 */
class Readings {
	/**
	 * The date of readings.
	 */
	private Calendar date;

	/**
	 * The accumulative readings of electricity.
	 */
	private double electricity;

	/**
	 * The accumulative readings of gas.
	 */
	private double gas;

	/**
	 * Constructor function of readings.
	 *
	 * @param date        Readings' date.
	 * @param electricity Electricity reading.
	 * @param gas         Gas reading.
	 */
	Readings(Calendar date, double electricity, double gas) {
		this.date = date;
		this.electricity = electricity;
		this.gas = gas;
	}

	/**
	 * Getter function of date.
	 *
	 * @return Readings' date.
	 */
	Calendar getDate() {
		return date;
	}

	/**
	 * Getter function of electricity reading.
	 *
	 * @return Electricity reading.
	 */
	double getElectricity() {
		return electricity;
	}

	/**
	 * Getter function of gas reading.
	 *
	 * @return Gas reading.
	 */
	double getGas() {
		return gas;
	}

	/**
	 * Setter function of electricity reading.
	 *
	 * @param electricity Electricity reading.
	 */
	void setElectricity(double electricity) {
		this.electricity = electricity;
	}

	/**
	 * Setter function of gas reading.
	 *
	 * @param gas Gas reading.
	 */
	void setGas(double gas) {
		this.gas = gas;
	}
}
