import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Title        Controller.java
 * Description  This class controls the energy system.
 */
class Controller {
	/**
	 * The instance of fileController.
	 */
	private static FileController fileController = new FileController();

	/**
	 * This function starts the system.
	 */
	static void start() {
		ManagerController.startManager();
		new GUI().welcome();
	}

	/**
	 * This function loads customers.
	 *
	 * @return True: Succeed. False: Failed.
	 */
	static boolean loadCustomers() {
		return fileController.getCustomersFromFile() != null;
	}

	/**
	 * This function gets the type of ID.
	 *
	 * @param ID The ID which need to be checked.
	 * @return Negative: Doesn't exist. Zero: Manager. Positive: Customer.
	 */
	static int getIDType(int ID) {
		if (ManagerController.isManager(ID))
			return 0;
		if (!checkID(ID)) {
			return -1;
		}
		return 1;
	}

	/**
	 * This function checks if the ID exist.
	 *
	 * @param ID The ID which need to be checked.
	 * @return True: Exist. False: Doesn't exist.
	 */
	static boolean checkID(long ID) {
		for (Customer customer : ManagerController.getCustomers())
			if (customer.getID() == ID)
				return true;
		return false;
	}

	/**
	 * This function checks the name and address of new customer.
	 *
	 * @param newCustomer The new customer which will be checked.
	 * @return True: If exist an old customer whose name and address are both same with new customer.
	 */
	static boolean checkDuplicated(Customer newCustomer) {
		String newName = newCustomer.getName();
		String newAddress = newCustomer.getAddress();
		for (Customer customer : ManagerController.getCustomers())
			if (customer.getName().equals(newName))
				if (customer.getAddress().equals(newAddress))
					return true;
		return false;
	}

	/**
	 * This function converts string to calendar.
	 *
	 * @param dateString The string contains the information of date.
	 * @return The date instance of the converted time.
	 */
	static Calendar stringToCalendar(String dateString) {
		Date newDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar date = Calendar.getInstance();
		date.setTime(newDate);
		return date;
	}

	/**
	 * This function converts calendar to string.
	 *
	 * @param date The calendar instance.
	 * @return The date string.
	 */
	static String calendarToString(Calendar date) {
		return String.valueOf(date.get(Calendar.YEAR)) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE);
	}
}
