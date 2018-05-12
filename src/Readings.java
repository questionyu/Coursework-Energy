import java.util.Calendar;

class Readings {
	private Calendar date;
	private double electricity;
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
}
