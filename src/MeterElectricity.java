
/**
 * Title        MeterElectricity.java
 * Description  This class defines an electricity meter.
 */
class MeterElectricity extends Meter {
	private static final String METER_SUFFIX = "electricity";

	MeterElectricity(int customerID) {
		super(customerID, 30, METER_SUFFIX); // 30 seconds
	}
}
