class IDGenerator {
	/**
	 * This function generates a random ID.
	 */
	static int get() {
		if (Customer.CUSTOMER_ID_LENGTH < 10)
			return (int) (Math.pow(10, Customer.CUSTOMER_ID_LENGTH - 1) * (1 + Math.random() * 9));
		else {
			StringBuilder ticketNo = new StringBuilder();
			ticketNo.append((int) (1 + Math.random() * 9));
			for (int i = 1; i < Customer.CUSTOMER_ID_LENGTH; i++) {
				ticketNo.append((int) (Math.random() * 10));
			}
			return Integer.parseInt(ticketNo.toString());
		}
	}
}
