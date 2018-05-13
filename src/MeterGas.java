
/**
 * Title        MeterGas.java
 * Description  This class defines a gas meter.
 */
class MeterGas extends Meter {
	MeterGas(int customerID) {
		super(customerID, 1800, Meter.GAS_METER); // 1800 seconds = 30 minutes
	}
}
