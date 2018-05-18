
/**
 * Title        Customer.java
 * Description  This class defines a customer.
 */
class Customer {
	/**
	 * The length of customer's ID.
	 */
	static final int CUSTOMER_ID_LENGTH = 8;

	/**
	 * The ID of customer.
	 */
	private int ID;

	/**
	 * The name of customer.
	 */
	private String name;

	/**
	 * The address of customer.
	 */
	private String address;

	/**
	 * The email address of customer.
	 */
	private String email;

	/**
	 * The budget which be set by customer.
	 */
	private double budget;

	/**
	 * Constructor function of customer.
	 *
	 * @param name    The name of customer.
	 * @param address The address of customer.
	 * @param email   The email address of customer.
	 * @param ID      The specified ID of customer.
	 * @param budget  The budget of customer.
	 */
	Customer(String name, String address, String email, int ID, double budget) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.ID = ID;
		this.budget = budget;
	}

	/**
	 * The getter function of ID.
	 *
	 * @return Customer's ID.
	 */
	int getID() {
		return ID;
	}

	/**
	 * The setter function of ID.
	 *
	 * @param ID The new ID.
	 */
	void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * The getter function of name.
	 *
	 * @return Customer's name.
	 */
	String getName() {
		return name;
	}

	/**
	 * The getter function of address.
	 *
	 * @return Customer's address.
	 */
	String getAddress() {
		return address;
	}

	/**
	 * Getter function of email.
	 *
	 * @return Customer's email address.
	 */
	String getEmail() {
		return email;
	}

	/**
	 * The getter function of budget.
	 *
	 * @return The budget which be set by customer.
	 */
	double getBudget() {
		return budget;
	}

	/**
	 * The setter function of budget.
	 *
	 * @param budget The new budget.
	 */
	void setBudget(double budget) {
		this.budget = budget;
	}
}
