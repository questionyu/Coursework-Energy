import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * Title        GUI.java
 * Description  This class controls the UI.
 */
class GUIMain extends JFrame {
	/**
	 * The cardLayout instance which manages all UI of energy system.
	 */
	private CardLayout energyCardLayout;

	/**
	 * The panel which use the card layout.
	 */
	private JPanel energyPanel;

	/**
	 * The main font of UI.
	 */
	private Font UIMainFont;

	/**
	 * Constructor function of
	 * This function creates a JFrame to contain a JPanel which uses CardLayout to display all things.
	 */
	GUIMain() {
		super("Energy management and monitoring system");

		//For better looks.
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
			e.printStackTrace();
		}

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(960, 540);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		energyCardLayout = new CardLayout();
		energyPanel = new JPanel(energyCardLayout);

		this.setContentPane(energyPanel);

		UIMainFont = new Font("Segoe UI", Font.PLAIN, 25);

		this.setVisible(true);
	}

	/**
	 * Create a panel which shows a welcome and two buttons.
	 */
	private JPanel GUIWelcome() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.CENTER
		JLabel welcomeLabel = new JLabel("Welcome!", JLabel.CENTER);
		welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 50));

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();

		JButton loginButton = new JButton("Login");
		loginButton.setFont(UIMainFont);
		loginButton.addActionListener(e -> {
			String stringID = showInputDialog("Your ID:", "Input dialog", JOptionPane.QUESTION_MESSAGE);
			if (stringID == null || stringID.equals(""))
				return;
			int ID;
			try {
				ID = Integer.parseInt(stringID);
			} catch (Exception exception) {
				showMessageDialog("ID must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Controller.getIDType(ID) < 0) {
				showMessageDialog("ID does not exist!", "Whoops!", JOptionPane.ERROR_MESSAGE);
			} else if (Controller.getIDType(ID) == 0) {
				Controller.startManagerTimer();
				manager();
			} else {
				Controller.login(ID);
				monitor();
			}
		});

		southPanel.add(loginButton);

		panel.add(welcomeLabel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a welcome panel and adds it to CardLayout.
	 */
	void welcome() {
		energyPanel.add(GUIWelcome(), "GUIWelcome");
		showWelcome();
	}

	/**
	 * This function shows the welcome panel.
	 */
	private void showWelcome() {
		energyCardLayout.show(energyPanel, "GUIWelcome");
	}

	/**
	 * Create a manager panel.
	 */
	private JPanel GUIManager() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one to continue");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JButton addCustomerButton = new JButton("Add customer");
		addCustomerButton.setFont(UIMainFont);
		addCustomerButton.addActionListener(e -> addCustomer());

		JButton removeCustomerButton = new JButton("Remove customer");
		removeCustomerButton.setFont(UIMainFont);
		removeCustomerButton.addActionListener(e -> removeCustomer());

		JButton tariffButton = new JButton("Tariff");
		tariffButton.setFont(UIMainFont);
		tariffButton.addActionListener(e -> tariff());

		JButton viewButton = new JButton("View");
		viewButton.setFont(UIMainFont);
		viewButton.addActionListener(e -> view());

		centerPanel.add(addCustomerButton);
		centerPanel.add(removeCustomerButton);
		centerPanel.add(tariffButton);
		centerPanel.add(viewButton);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> {
			Controller.stopManagerTimer();
			showWelcome();
		});

		// Only for demo
		JButton billsButton = new JButton("Bills (Only for demo)");
		billsButton.setFont(UIMainFont);
		billsButton.addActionListener(e -> {
			Controller.bills();
			showMessageDialog("Generate and send bills successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(billsButton);

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a manager panel and adds it to CardLayout.
	 */
	private void manager() {
		energyPanel.add(GUIManager(), "GUIManager");
		showManager();
	}

	/**
	 * This function shows the manager panel.
	 */
	private void showManager() {
		energyCardLayout.show(energyPanel, "GUIManager");
	}

	/**
	 * Create an add customer panel.
	 */
	private JPanel GUIAddCustomer() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("New customer's information");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(3, 2, 25, 25));

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(UIMainFont);

		JTextField nameTextField = new JTextField();

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setFont(UIMainFont);

		JTextField addressTextField = new JTextField();

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(UIMainFont);

		JTextField emailTextField = new JTextField();

		centerPanel.add(nameLabel);
		centerPanel.add(nameTextField);
		centerPanel.add(addressLabel);
		centerPanel.add(addressTextField);
		centerPanel.add(emailLabel);
		centerPanel.add(emailTextField);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showManager());

		JButton addButton = new JButton("Add");
		addButton.setFont(UIMainFont);
		addButton.addActionListener(e -> {
			String name = nameTextField.getText();
			String address = addressTextField.getText();
			String email = emailTextField.getText();
			if (name.equals("") || address.equals("") || email.equals("")) {
				showMessageDialog("Name or address or email can not be blank!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
			if (!pattern.matcher(email).matches()) {
				showMessageDialog("Email address wrong!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (showConfirmDialog("Confirm to add new customer?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			if (!Controller.addCustomer(name, address, email)) {
				showMessageDialog("Customer already exists!", "Whoops!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			showMessageDialog("Add successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			showManager();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(addButton);

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a addCustomer panel and adds it to CardLayout.
	 */
	private void addCustomer() {
		energyPanel.add(GUIAddCustomer(), "GUIAddCustomer");
		showAddCustomer();
	}

	/**
	 * This function shows the addCustomer panel.
	 */
	private void showAddCustomer() {
		energyCardLayout.show(energyPanel, "GUIAddCustomer");
	}

	/**
	 * Create a remove customer panel.
	 */
	private JPanel GUIRemoveCustomer() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one customer to remove");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel userListPanel = new JPanel();
		userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

		for (Customer customer : Controller.getCustomers()) {
			JButton customerButton = new JButton("\"" + customer.getName() + "\"  " + customer.getAddress());
			customerButton.setFont(UIMainFont);
			customerButton.addMouseListener(new mouseAdapter(customer));
			userListPanel.add(customerButton);
		}
		JScrollPane scrollPane = new JScrollPane(userListPanel);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showManager());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a removeCustomer panel and adds it to CardLayout.
	 */
	private void removeCustomer() {
		energyPanel.add(GUIRemoveCustomer(), "GUIRemoveCustomer");
		showRemoveCustomer();
	}

	/**
	 * This function shows the removeCustomer panel.
	 */
	private void showRemoveCustomer() {
		energyCardLayout.show(energyPanel, "GUIRemoveCustomer");
	}

	/**
	 * Create a tariff panel.
	 */
	private JPanel GUITariff() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Tariff information");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 25, 25));

		JLabel tariffElectricityLabel = new JLabel("Electricity tariff:");
		tariffElectricityLabel.setFont(UIMainFont);

		JLabel electricityLabel = new JLabel(Controller.getTariffElectricity() + " p/KWh");
		electricityLabel.setFont(UIMainFont);

		JLabel tariffGasLabel = new JLabel("Gas tariff:");
		tariffGasLabel.setFont(UIMainFont);

		JLabel gasLabel = new JLabel(Controller.getTariffGas() + " p/KWh");
		gasLabel.setFont(UIMainFont);

		centerPanel.add(tariffElectricityLabel);
		centerPanel.add(electricityLabel);
		centerPanel.add(tariffGasLabel);
		centerPanel.add(gasLabel);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showManager());

		JButton updateButton = new JButton("Update");
		updateButton.setFont(UIMainFont);
		updateButton.addActionListener(e -> updateTariff());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(updateButton);

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a tariff panel and adds it to CardLayout.
	 */
	private void tariff() {
		energyPanel.add(GUITariff(), "GUITariff");
		showTariff();
	}

	/**
	 * This function shows the tariff panel.
	 */
	private void showTariff() {
		energyCardLayout.show(energyPanel, "GUITariff");
	}

	/**
	 * Create an update tariff panel.
	 */
	private JPanel GUIUpdateTariff() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("New tariff");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 25, 25));

		JLabel tariffElectricityLabel = new JLabel("Electricity:");
		tariffElectricityLabel.setFont(UIMainFont);

		JLabel tariffGasLabel = new JLabel("Gas:");
		tariffGasLabel.setFont(UIMainFont);

		JTextField tariffElectricityTextField = new JTextField();
		JTextField tariffGasTextField = new JTextField();

		centerPanel.add(tariffElectricityLabel);
		centerPanel.add(tariffElectricityTextField);
		centerPanel.add(tariffGasLabel);
		centerPanel.add(tariffGasTextField);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showManager());

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(UIMainFont);
		confirmButton.addActionListener(e -> {
			String tariffElectricityString = tariffElectricityTextField.getText();
			String tariffGasString = tariffGasTextField.getText();
			if (tariffElectricityString.equals("") || tariffGasString.equals("")) {
				showMessageDialog("Tariff can not be blank!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			double tariffElectricity, tariffGas;
			try {
				tariffElectricity = Double.parseDouble(tariffElectricityString);
				tariffGas = Double.parseDouble(tariffGasString);
			} catch (Exception exception) {
				showMessageDialog("Tariff must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (tariffElectricity < 0 || tariffGas < 0) {
				showMessageDialog("Tariff must be a positive number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (showConfirmDialog("Confirm to update tariff?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			Controller.updateTariff(tariffElectricity, tariffGas);
			showMessageDialog("Update successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			showManager();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(confirmButton);

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a updateTariff panel and adds it to CardLayout.
	 */
	private void updateTariff() {
		energyPanel.add(GUIUpdateTariff(), "GUIUpdateTariff");
		showUpdateTariff();
	}

	/**
	 * This function shows the updateTariff panel.
	 */
	private void showUpdateTariff() {
		energyCardLayout.show(energyPanel, "GUIUpdateTariff");
	}

	/**
	 * Create a view panel.
	 */
	private JPanel GUIView() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Readings and bills");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		String[] columnNames = {"Name", "Electricity", "Gas", "Bill"};
		String[][] data = Controller.getReadingsAndBills();

		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showManager());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a view panel and adds it to CardLayout.
	 */
	private void view() {
		energyPanel.add(GUIView(), "GUIView");
		showView();
	}

	/**
	 * This function shows the view panel.
	 */
	private void showView() {
		energyCardLayout.show(energyPanel, "GUIView");
	}

	/**
	 * Create a monitor panel.
	 */
	private JPanel GUIMonitor() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Monitor");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(3, 3, 5, 5));

		JLabel electricityLabel = new JLabel("Electricity:");
		electricityLabel.setFont(UIMainFont);

		JLabel gasLabel = new JLabel("Gas:");
		gasLabel.setFont(UIMainFont);

		JLabel budgetLabel = new JLabel("Budget:");
		budgetLabel.setFont(UIMainFont);

		JLabel electricityReading = new JLabel(((int) Controller.getReading()[0]) + " KWh");
		electricityReading.setFont(UIMainFont);

		JLabel electricityCost = new JLabel(Controller.getCosts()[0] + " £");
		electricityCost.setFont(UIMainFont);

		JLabel gasReading = new JLabel(((int) Controller.getReading()[1]) + " KWh");
		gasReading.setFont(UIMainFont);

		JLabel gasCost = new JLabel(Controller.getCosts()[1] + " £");
		gasCost.setFont(UIMainFont);

		JLabel budget = new JLabel(Controller.getBudget() + " £");
		budget.setFont(UIMainFont);

		JPanel alertPanel = new JPanel();
		if (Controller.getBudget() > Controller.getCosts()[0] + Controller.getCosts()[1])
			alertPanel.setBackground(Color.CYAN);
		else
			alertPanel.setBackground(Color.RED);

		centerPanel.add(electricityLabel);
		centerPanel.add(electricityReading);
		centerPanel.add(electricityCost);
		centerPanel.add(gasLabel);
		centerPanel.add(gasReading);
		centerPanel.add(gasCost);
		centerPanel.add(budgetLabel);
		centerPanel.add(budget);
		centerPanel.add(alertPanel);

		Timer timer = new Timer(5000, e -> {
			System.out.println(Controller.getReading()[0]);
			electricityReading.setText(((int) Controller.getReading()[0]) + " KWh");
			electricityCost.setText(Controller.getCosts()[0] + " £");
			gasReading.setText(((int) Controller.getReading()[1]) + " KWh");
			gasCost.setText(Controller.getCosts()[1] + " £");
			if (Controller.getBudget() > Controller.getCosts()[0] + Controller.getCosts()[1])
				alertPanel.setBackground(Color.CYAN);
			else
				alertPanel.setBackground(Color.RED);
			panel.revalidate();
			panel.repaint();
		});

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> {
			timer.stop();
			Controller.logout();
			showWelcome();
		});

		JButton moreButton = new JButton("More");
		moreButton.setFont(UIMainFont);
		moreButton.addActionListener(e -> {
			timer.stop();
			more();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(moreButton);

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		timer.start();
		return panel;
	}

	/**
	 * This function creates a monitor panel and adds it to CardLayout.
	 */
	private void monitor() {
		energyPanel.add(GUIMonitor(), "GUIMonitor");
		showMonitor();
	}

	/**
	 * This function shows the monitor panel.
	 */
	private void showMonitor() {
		energyCardLayout.show(energyPanel, "GUIMonitor");
	}

	/**
	 * Create a more panel.
	 */
	private JPanel GUIMore() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one to continue");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JButton viewButton = new JButton("View");
		viewButton.setFont(UIMainFont);
		viewButton.addActionListener(e -> history());

		JButton budgetButton = new JButton("Budget");
		budgetButton.setFont(UIMainFont);
		budgetButton.addActionListener(e -> budget());

		JButton tariffButton = new JButton("Tariff");
		tariffButton.setFont(UIMainFont);
		tariffButton.addActionListener(e -> checkTariff());

		// Only for demo
		JButton sendReadingsButton = new JButton("Send readings (Only for demo)");
		sendReadingsButton.setFont(UIMainFont);
		sendReadingsButton.addActionListener(e -> {
			Controller.sendReadings();
			showMessageDialog("Send successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
		});

		centerPanel.add(viewButton);
		centerPanel.add(budgetButton);
		centerPanel.add(tariffButton);
		centerPanel.add(sendReadingsButton);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> monitor());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a more panel and adds it to CardLayout.
	 */
	private void more() {
		energyPanel.add(GUIMore(), "GUIMore");
		showMore();
	}

	/**
	 * This function shows the more panel.
	 */
	private void showMore() {
		energyCardLayout.show(energyPanel, "GUIMore");
	}

	/**
	 * Create a history panel.
	 */
	private JPanel GUIHistory() {
		JPanel panel = new JPanel(new BorderLayout());
		CardLayout historyCardLayout = new CardLayout();
		JPanel historyPanel = new JPanel(historyCardLayout);

		// BorderLayout.NORTH
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

		JLabel promptLabel = new JLabel("Historical information");
		promptLabel.setFont(UIMainFont);

		//Radio buttons.
		JRadioButton day = new JRadioButton("By day");
		day.setFont(UIMainFont);
		day.setActionCommand("day");
		day.setSelected(true);

		JRadioButton week = new JRadioButton("By week");
		week.setFont(UIMainFont);
		week.setActionCommand("week");

		JRadioButton month = new JRadioButton("By month");
		month.setFont(UIMainFont);
		month.setActionCommand("month");

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(week);
		group.add(day);
		group.add(month);

		//Register a listener for the radio buttons.
		week.addActionListener(e -> historyCardLayout.show(historyPanel, e.getActionCommand()));
		day.addActionListener(e -> historyCardLayout.show(historyPanel, e.getActionCommand()));
		month.addActionListener(e -> historyCardLayout.show(historyPanel, e.getActionCommand()));

		//Add radio buttons to a panel.
		JPanel radioPanel = new JPanel(new GridLayout(1, 0));
		radioPanel.add(day);
		radioPanel.add(week);
		radioPanel.add(month);

		northPanel.add(promptLabel);
		northPanel.add(Box.createHorizontalGlue());
		northPanel.add(radioPanel);

		// BorderLayout.CENTER
		String[] columnNames = {"Date", "Electricity", "Gas", "Cost"};
		String[][] readingsDataByDay = Controller.getReadingsDataByDay();
		String[][] readingsDataByWeek = Controller.getReadingsDataByWeek();
		String[][] readingsDataByMonth = Controller.getReadingsDataByMonth();

		JTable tableByDay = new JTable(readingsDataByDay, columnNames);
		JTable tableByWeek = new JTable(readingsDataByWeek, columnNames);
		JTable tableByMonth = new JTable(readingsDataByMonth, columnNames);

		historyPanel.add(new JScrollPane(tableByDay), "day");
		historyPanel.add(new JScrollPane(tableByWeek), "week");
		historyPanel.add(new JScrollPane(tableByMonth), "month");

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showMore());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		panel.add(northPanel, BorderLayout.NORTH);
		panel.add(historyPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a history panel and adds it to CardLayout.
	 */
	private void history() {
		energyPanel.add(GUIHistory(), "GUIHistory");
		showHistory();
	}

	/**
	 * This function shows the history panel.
	 */
	private void showHistory() {
		energyCardLayout.show(energyPanel, "GUIHistory");
	}

	/**
	 * Create a budget panel.
	 */
	private JPanel GUIBudget() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Budget information");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel oldBudgetLabel = new JLabel("Old budget:");
		oldBudgetLabel.setFont(UIMainFont);

		JLabel newBudgetLabel = new JLabel("New budget:");
		newBudgetLabel.setFont(UIMainFont);

		JLabel oldBudget = new JLabel(Controller.getBudget() + " £");
		oldBudget.setFont(UIMainFont);

		JTextField newBudget = new JTextField();

		centerPanel.add(oldBudgetLabel);
		centerPanel.add(oldBudget);
		centerPanel.add(newBudgetLabel);
		centerPanel.add(newBudget);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showMore());

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(UIMainFont);
		confirmButton.addActionListener(e -> {
			String newBudgetString = newBudget.getText();
			if (newBudgetString.equals("")) {
				showMessageDialog("Budget can not be blank!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			double budget;
			try {
				budget = Double.parseDouble(newBudgetString);
			} catch (Exception exception) {
				showMessageDialog("Budget must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (budget < 0) {
				showMessageDialog("Budget must be a positive number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (showConfirmDialog("Confirm to update Budget?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			Controller.updateBudget(budget);
			showMessageDialog("Update successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			showMore();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(confirmButton);

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a budget panel and adds it to CardLayout.
	 */
	private void budget() {
		energyPanel.add(GUIBudget(), "GUIBudget");
		showBudget();
	}

	/**
	 * This function shows the budget panel.
	 */
	private void showBudget() {
		energyCardLayout.show(energyPanel, "GUIBudget");
	}

	/**
	 * Create a check tariff panel.
	 */
	private JPanel GUICheckTariff() {
		JPanel panel = new JPanel(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Tariff information");
		promptLabel.setFont(UIMainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel electricity = new JLabel("Electricity:");
		electricity.setFont(UIMainFont);

		JLabel gas = new JLabel("Gas:");
		gas.setFont(UIMainFont);

		JLabel electricityTariff = new JLabel(Controller.getTariffElectricity() + " p/KWh");
		electricityTariff.setFont(UIMainFont);

		JLabel gasTariff = new JLabel(Controller.getTariffGas() + " p/KWh");
		gasTariff.setFont(UIMainFont);

		centerPanel.add(electricity);
		centerPanel.add(electricityTariff);
		centerPanel.add(gas);
		centerPanel.add(gasTariff);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(UIMainFont);
		backButton.addActionListener(e -> showMore());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		panel.add(promptLabel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This function creates a checkTariff panel and adds it to CardLayout.
	 */
	private void checkTariff() {
		energyPanel.add(GUICheckTariff(), "GUICheckTariff");
		showCheckTariff();
	}

	/**
	 * This function shows the checkTariff panel.
	 */
	private void showCheckTariff() {
		energyCardLayout.show(energyPanel, "GUICheckTariff");
	}

	/**
	 * This function shows a input dialog on screen.
	 *
	 * @param text        The prompt message.
	 * @param title       The window title.
	 * @param messageType The type of this message.
	 * @return The input string.
	 */
	private String showInputDialog(String text, String title, int messageType) {
		JLabel promptLabel = new JLabel(text);
		promptLabel.setFont(UIMainFont);
		return JOptionPane.showInputDialog(null, promptLabel, title, messageType);
	}

	/**
	 * This function shows a message dialog on screen.
	 *
	 * @param text        The message.
	 * @param title       The window title.
	 * @param messageType The type of this message.
	 */
	private void showMessageDialog(String text, String title, int messageType) {
		JLabel promptLabel = new JLabel(text, JLabel.CENTER);
		promptLabel.setFont(UIMainFont);
		JOptionPane.showMessageDialog(null, promptLabel, title, messageType);
	}

	/**
	 * This function shows a confirm dialog on screen.
	 *
	 * @param text       The message.
	 * @param title      The window title.
	 * @param optionType The type of this message.
	 * @return An int indicating the option selected by the user.
	 */
	private int showConfirmDialog(String text, String title, int optionType) {
		JLabel promptLabel = new JLabel(text, JLabel.CENTER);
		promptLabel.setFont(UIMainFont);
		return JOptionPane.showConfirmDialog(null, promptLabel, title, optionType);
	}

	/**
	 * This class extends MouseAdapter. This class's instance record the customer of each button.
	 */
	class mouseAdapter extends MouseAdapter {
		/**
		 * The customer.
		 */
		private Customer customer;

		/**
		 * Constructor function. Create a mouse adapter instance.
		 *
		 * @param customer The customer.
		 */
		mouseAdapter(Customer customer) {
			this.customer = customer;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (showConfirmDialog("Confirm to remove this customer?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			Controller.removeCustomer(customer);
			showMessageDialog("Remove successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			showManager();
		}
	}
}
