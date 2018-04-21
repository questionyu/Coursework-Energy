import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Title        Monitor.java
 * Description  This class defines a monitor.
 */
public class Monitor {
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
	 * The constructor function of monitor.
	 *
	 * @param customer The customer of monitor.
	 */
	Monitor(Customer customer) {
		this.customer = customer;
		meterElectricity = new MeterElectricity(customer.getID());
		meterGas = new MeterGas(customer.getID());
	}

	/**
	 * This function sends readings.
	 */
	public void sendReadings() {
		try {
			File electricityReadingsFile = new File("./readings/" + customer.getID() + "electricity.txt");
			File gasReadingsFile = new File("./readings/" + customer.getID() + "gas.txt");
			FileInputStream electricityInputStream = new FileInputStream(electricityReadingsFile);
			FileInputStream gasInputStream = new FileInputStream(gasReadingsFile);
			Scanner electricityScanner = new Scanner(electricityInputStream);
			Scanner gasScanner = new Scanner(gasInputStream);

			double electricityReadings = Double.parseDouble(electricityScanner.nextLine());
			double gasReadings = Double.parseDouble(gasScanner.nextLine());

			electricityScanner.close();
			gasScanner.close();
			electricityInputStream.close();
			gasInputStream.close();

			File sendReadingsFile = new File("./receivedReadings/" + customer.getID() + ".txt");
			FileWriter sendReadingsFileWriter = new FileWriter(sendReadingsFile);

			sendReadingsFileWriter.write(Double.toString(electricityReadings) + "\n");
			sendReadingsFileWriter.write(Double.toString(gasReadings) + "\n");

			sendReadingsFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function will stop recording before some customer be removed.
	 */
	void stopRecording() {
		meterElectricity.stopRecording();
		meterGas.stopRecording();
	}

	/**
	 * The getter function of customer.
	 *
	 * @return The customer of monitor.
	 */
	Customer getCustomer() {
		return customer;
	}
}
