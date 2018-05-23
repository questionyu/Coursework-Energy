/**
 * Title        IDGenerator.java
 * Description  This class will generate an ID.
 */
class IDGenerator implements IDInterface {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long get(int length) {
		if (length < 10)
			return (int) (Math.pow(10, length - 1) * (1 + Math.random() * 9));
		else {
			StringBuilder ticketNo = new StringBuilder();
			ticketNo.append((int) (1 + Math.random() * 9));
			for (int i = 1; i < length; i++) {
				ticketNo.append((int) (Math.random() * 10));
			}
			return Long.parseLong(ticketNo.toString());
		}
	}
}
