
/**
 * Title        MeterGas.java
 * Description  This class defines a gas meter.
 */
class MeterGas extends Meter {
	private static final String METER_SUFFIX = "gas";

	MeterGas(int customerID) {
		super(customerID, 1800, METER_SUFFIX); // 1800 seconds = 30 minutes
	}
}
