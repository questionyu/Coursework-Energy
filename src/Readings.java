import java.util.Calendar;

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

	Readings(Calendar date, double electricity, double gas) {
		this.date = date;
		this.electricity = electricity;
		this.gas = gas;
	}

	Calendar getDate() {
		return date;
	}

	double getElectricity() {
		return electricity;
	}

	double getGas() {
		return gas;
	}

	void setElectricity(double electricity) {
		this.electricity = electricity;
	}

	void setGas(double gas) {
		this.gas = gas;
	}
}
