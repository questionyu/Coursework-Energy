
/**
 * Title        MeterElectricity.java
 * Description  This class defines an electricity meter.
 */
class MeterElectricity extends Meter {
	MeterElectricity(int customerID) {
		super(customerID, 30, Meter.ELECTRICITY_METER); // 30 seconds
	}
}
