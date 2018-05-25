import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * Title        GUI.java
 * Description  This class controls the UI.
 */
class GUI extends JFrame {
	/**
	 * The cardLayout instance which manages all UI of energy system.
	 */
	private CardLayout energyCardLayout;

	/**
	 * The panel which use the card layout.
	 */
	private JPanel energyPanel;

	/**
	 * Prompt font.
	 */
	private Font promptFont;

	/**
	 * The main font of UI.
	 */
	private Font mainFont;

	/**
	 * Window's width.
	 */
	private int width = 960;

	/**
	 * Window's height.
	 */
	private int height = 540;

	/**
	 * Constructor function of
	 * This function creates a JFrame to contain a JPanel which uses CardLayout to display all things.
	 */
	GUI() {
		super("Energy management and monitoring system");

		//For better looks.
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		energyCardLayout = new CardLayout();
		energyPanel = new JPanel(energyCardLayout);

		this.setContentPane(energyPanel);

		promptFont = new Font("Curlz MT", Font.PLAIN, 64);
		mainFont = new Font("Agency FB", Font.PLAIN, 32);

		this.setVisible(true);
	}

	/**
	 * Create a panel which shows a welcome and two buttons.
	 */
	private JPanel GUIWelcome() {
		JPanel panel = new JPanel(null);

		JLabel welcomeLabel = new JLabel("Welcome!", JLabel.CENTER);
		welcomeLabel.setFont(new Font("Curlz MT", Font.BOLD, 96));
		welcomeLabel.setBounds(width / 4, height / 7, width / 2, height / 5);

		JLabel IDLabel = new JLabel("ID: ");
		IDLabel.setFont(mainFont);
		IDLabel.setBounds((int) (0.4 * width), (int) (0.45 * height), (int) (0.05 * width), height / 12);

		JTextField IDTextField = new JTextField();
		IDTextField.setFont(mainFont);
		IDTextField.setBounds((int) (0.45 * width), (int) (0.45 * height), (int) (0.15 * width), height / 12);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(mainFont);
		loginButton.setBounds((int) (0.45 * width), (int) (0.7 * height), (int) (0.1 * width), height / 10);
		loginButton.addActionListener(e -> {
			String stringID = IDTextField.getText();
			if (stringID.equals(""))
				return;
			long ID;
			try {
				ID = Long.parseLong(stringID);
			} catch (Exception exception) {
				showMessageDialog("ID must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Controller.getIDType(ID) < 0) {
				showMessageDialog("ID does not exist!", "Whoops!", JOptionPane.ERROR_MESSAGE);
			} else if (Controller.getIDType(ID) == 0) {
				ManagerController.startManagerTimer();
				manager();
			} else {
				MonitorController.login(ID);
				monitor();
			}
		});

		panel.add(welcomeLabel);

		panel.add(IDLabel);
		panel.add(IDTextField);

		panel.add(loginButton);

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
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		back.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				ManagerController.stopManagerTimer();
				showWelcome();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH));
				panel.updateUI();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
				panel.updateUI();
			}
		});

		JLabel promptLabel = new JLabel("Select one to continue", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		JButton addCustomerButton = new JButton("Add customer");
		addCustomerButton.setFont(mainFont);
		addCustomerButton.setBounds((int) (0.25 * width), (int) (0.6 * height), (int) (0.25 * width), height / 10);
		addCustomerButton.addActionListener(e -> addCustomer());

		JButton removeCustomerButton = new JButton("Remove customer");
		removeCustomerButton.setFont(mainFont);
		removeCustomerButton.setBounds((int) (0.5 * width), (int) (0.6 * height), (int) (0.25 * width), height / 10);
		removeCustomerButton.addActionListener(e -> removeCustomer());

		JButton tariffButton = new JButton("Tariff");
		tariffButton.setFont(mainFont);
		tariffButton.setBounds((int) (0.25 * width), (int) (0.75 * height), (int) (0.25 * width), height / 10);
		tariffButton.addActionListener(e -> tariff());

		JButton viewButton = new JButton("View");
		viewButton.setFont(mainFont);
		viewButton.setBounds((int) (0.5 * width), (int) (0.75 * height), (int) (0.25 * width), height / 10);
		viewButton.addActionListener(e -> view());

		panel.add(back);
		panel.add(promptLabel);

		panel.add(addCustomerButton);
		panel.add(removeCustomerButton);
		panel.add(tariffButton);
		panel.add(viewButton);

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
	 * This function add mouse listener to one JLabel.
	 *
	 * @param panel     This panel will show the JLabel.
	 * @param backImage Back image.
	 * @param back      Back JLabel.
	 */
	private void clickShowManager(JPanel panel, ImageIcon backImage, JLabel back) {
		back.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				showManager();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH));
				panel.updateUI();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
				panel.updateUI();
			}
		});
	}

	/**
	 * Create an add customer panel.
	 */
	private JPanel GUIAddCustomer() {
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		clickShowManager(panel, backImage, back);

		JLabel promptLabel = new JLabel("New customer's information", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(mainFont);
		nameLabel.setBounds((int) (0.3 * width), (int) (0.4 * height), (int) (0.15 * width), height / 12);

		JTextField nameTextField = new JTextField();
		nameTextField.setFont(mainFont);
		nameTextField.setBounds((int) (0.45 * width), (int) (0.4 * height), (int) (0.25 * width), height / 12);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setFont(mainFont);
		addressLabel.setBounds((int) (0.3 * width), (int) (0.5 * height), (int) (0.15 * width), height / 12);

		JTextField addressTextField = new JTextField();
		addressTextField.setFont(mainFont);
		addressTextField.setBounds((int) (0.45 * width), (int) (0.5 * height), (int) (0.25 * width), height / 12);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(mainFont);
		emailLabel.setBounds((int) (0.3 * width), (int) (0.6 * height), (int) (0.15 * width), height / 12);

		JTextField emailTextField = new JTextField();
		emailTextField.setFont(mainFont);
		emailTextField.setBounds((int) (0.45 * width), (int) (0.6 * height), (int) (0.25 * width), height / 12);

		JButton addButton = new JButton("Add");
		addButton.setFont(mainFont);
		addButton.setBounds((int) (0.45 * width), (int) (0.7 * height), (int) (0.1 * width), height / 10);
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
			if (!ManagerController.addCustomer(name, address, email)) {
				showMessageDialog("Customer already exists!", "Whoops!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			showMessageDialog("Add successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			showManager();
		});

		panel.add(back);
		panel.add(promptLabel);

		panel.add(nameLabel);
		panel.add(nameTextField);
		panel.add(addressLabel);
		panel.add(addressTextField);
		panel.add(emailLabel);
		panel.add(emailTextField);

		panel.add(addButton);

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
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		clickShowManager(panel, backImage, back);

		JLabel promptLabel = new JLabel("Select one customer to remove", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		String[] columnNames = {"Name", "Address", "Email", "Button"};
		String[][] customerList = ManagerController.getCustomerList();
		Object[][] data = new Object[customerList.length][4];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = customerList[i][0];
			data[i][1] = customerList[i][1];
			data[i][2] = customerList[i][2];
		}
		DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(defaultTableModel);
		table.setFont(mainFont);
		table.setRowHeight(42);
		for (int i = 0; i < data.length; i++) {
			table.getColumnModel().getColumn(3).setCellEditor(new ButtonInTable(customerList[i][3]));
			table.getColumnModel().getColumn(3).setCellRenderer(new ButtonInTable(customerList[i][3]));
		}
		JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, (int) (0.35 * height), width, (int) (0.6 * height));

		panel.add(back);
		panel.add(promptLabel);

		panel.add(scrollPane);

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
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		clickShowManager(panel, backImage, back);

		JLabel promptLabel = new JLabel("Tariff information", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		JLabel tariffElectricityLabel = new JLabel("Electricity tariff:");
		tariffElectricityLabel.setFont(mainFont);
		tariffElectricityLabel.setBounds((int) (0.3 * width), (int) (0.5 * height), (int) (0.2 * width), height / 12);

		JLabel electricityLabel = new JLabel(ManagerController.getTariffElectricity() + " p/KWh", JLabel.RIGHT);
		electricityLabel.setFont(mainFont);
		electricityLabel.setBounds((int) (0.5 * width), (int) (0.5 * height), (int) (0.2 * width), height / 12);

		JLabel tariffGasLabel = new JLabel("Gas tariff:");
		tariffGasLabel.setFont(mainFont);
		tariffGasLabel.setBounds((int) (0.3 * width), (int) (0.6 * height), (int) (0.2 * width), height / 12);

		JLabel gasLabel = new JLabel(ManagerController.getTariffGas() + " p/KWh", JLabel.RIGHT);
		gasLabel.setFont(mainFont);
		gasLabel.setBounds((int) (0.5 * width), (int) (0.6 * height), (int) (0.2 * width), height / 12);

		JButton updateButton = new JButton("Update");
		updateButton.setFont(mainFont);
		updateButton.setBounds((int) (0.4 * width), (int) (0.7 * height), (int) (0.2 * width), height / 10);
		updateButton.addActionListener(e -> updateTariff());

		panel.add(back);
		panel.add(promptLabel);

		panel.add(tariffElectricityLabel);
		panel.add(electricityLabel);
		panel.add(tariffGasLabel);
		panel.add(gasLabel);

		panel.add(updateButton);

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
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		clickShowManager(panel, backImage, back);

		JLabel promptLabel = new JLabel("New tariff", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		JLabel tariffElectricityLabel = new JLabel("Electricity:");
		tariffElectricityLabel.setFont(mainFont);
		tariffElectricityLabel.setBounds((int) (0.3 * width), (int) (0.4 * height), (int) (0.15 * width), height / 12);

		JTextField tariffElectricityTextField = new JTextField();
		tariffElectricityTextField.setFont(mainFont);
		tariffElectricityTextField.setBounds((int) (0.45 * width), (int) (0.4 * height), (int) (0.15 * width), height / 12);

		JLabel unitElectricityLabel = new JLabel("p/KWh");
		unitElectricityLabel.setFont(mainFont);
		unitElectricityLabel.setBounds((int) (0.6 * width), (int) (0.4 * height), (int) (0.1 * width), height / 12);

		JLabel tariffGasLabel = new JLabel("Gas:");
		tariffGasLabel.setFont(mainFont);
		tariffGasLabel.setBounds((int) (0.3 * width), (int) (0.5 * height), (int) (0.15 * width), height / 12);

		JTextField tariffGasTextField = new JTextField();
		tariffGasTextField.setFont(mainFont);
		tariffGasTextField.setBounds((int) (0.45 * width), (int) (0.5 * height), (int) (0.15 * width), height / 12);

		JLabel unitGasLabel = new JLabel("p/KWh");
		unitGasLabel.setFont(mainFont);
		unitGasLabel.setBounds((int) (0.6 * width), (int) (0.5 * height), (int) (0.1 * width), height / 12);

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(mainFont);
		confirmButton.setBounds((int) (0.4 * width), (int) (0.7 * height), (int) (0.2 * width), height / 10);
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
			ManagerController.updateTariff(tariffElectricity, tariffGas);
			showMessageDialog("Update successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			showManager();
		});

		panel.add(back);
		panel.add(promptLabel);

		panel.add(tariffElectricityLabel);
		panel.add(tariffElectricityTextField);
		panel.add(unitElectricityLabel);
		panel.add(tariffGasLabel);
		panel.add(tariffGasTextField);
		panel.add(unitGasLabel);

		panel.add(confirmButton);

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
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		clickShowManager(panel, backImage, back);

		JLabel promptLabel = new JLabel("Readings and bills", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		String[] columnNames = {"Name", "Electricity", "Gas", "Bill"};
		String[][] data = ManagerController.getReadingsAndBills();

		JTable table = new JTable(data, columnNames);
		table.setFont(mainFont);
		table.setRowHeight(42);
		JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, (int) (0.35 * height), width, (int) (0.6 * height));

		panel.add(back);
		panel.add(promptLabel);

		panel.add(scrollPane);

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
		JPanel panel = new JPanel(null);

		JLabel promptLabel = new JLabel("Monitor", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		JLabel electricityLabel = new JLabel("Electricity:");
		electricityLabel.setFont(mainFont);
		electricityLabel.setBounds((int) (0.3 * width), (int) (0.4 * height), (int) (0.15 * width), height / 12);

		JLabel electricityReading = new JLabel((int) MonitorController.getReading()[0] + " KWh", JLabel.RIGHT);
		electricityReading.setFont(mainFont);
		electricityReading.setBounds((int) (0.45 * width), (int) (0.4 * height), (int) (0.1 * width), height / 12);

		JLabel electricityCost = new JLabel(Math.round(MonitorController.getCosts()[0] * 100) / 100.0 + " £", JLabel.RIGHT);
		electricityCost.setFont(mainFont);
		electricityCost.setBounds((int) (0.55 * width), (int) (0.4 * height), (int) (0.15 * width), height / 12);

		JLabel gasLabel = new JLabel("Gas:");
		gasLabel.setFont(mainFont);
		gasLabel.setBounds((int) (0.3 * width), (int) (0.5 * height), (int) (0.15 * width), height / 12);

		JLabel gasReading = new JLabel((int) MonitorController.getReading()[1] + " KWh", JLabel.RIGHT);
		gasReading.setFont(mainFont);
		gasReading.setBounds((int) (0.45 * width), (int) (0.5 * height), (int) (0.1 * width), height / 12);

		JLabel gasCost = new JLabel(Math.round(MonitorController.getCosts()[1] * 100) / 100.0 + " £", JLabel.RIGHT);
		gasCost.setFont(mainFont);
		gasCost.setBounds((int) (0.55 * width), (int) (0.5 * height), (int) (0.15 * width), height / 12);

		JLabel budgetLabel = new JLabel("Budget:");
		budgetLabel.setFont(mainFont);
		budgetLabel.setBounds((int) (0.3 * width), (int) (0.6 * height), (int) (0.15 * width), height / 12);

		JLabel budget = new JLabel(Math.round(MonitorController.getBudget() * 100) / 100.0 + " £", JLabel.RIGHT);
		budget.setFont(mainFont);
		budget.setBounds((int) (0.45 * width), (int) (0.6 * height), (int) (0.1 * width), height / 12);

		JProgressBar progressBar = new JProgressBar();
		if (MonitorController.getBudget() > MonitorController.getCosts()[0] + MonitorController.getCosts()[1])
			progressBar.setValue((int) ((MonitorController.getCosts()[0] + MonitorController.getCosts()[1]) / MonitorController.getBudget() * 100));
		else
			progressBar.setString("Over budget");
		progressBar.setFont(mainFont);
		progressBar.setStringPainted(true);
		progressBar.setBounds((int) (0.59 * width), (int) (0.6 * height), (int) (0.14 * width), height / 12);

		Timer timer = new Timer(5000, e -> {
			System.out.println((int) ((MonitorController.getCosts()[0] + MonitorController.getCosts()[1]) / MonitorController.getBudget() * 100));
			electricityReading.setText(((int) MonitorController.getReading()[0]) + " KWh");
			electricityCost.setText(Math.round(MonitorController.getCosts()[0] * 100) / 100.0 + " £");
			gasReading.setText(((int) MonitorController.getReading()[1]) + " KWh");
			gasCost.setText(Math.round(MonitorController.getCosts()[1] * 100) / 100.0 + " £");
			if (MonitorController.getBudget() > MonitorController.getCosts()[0] + MonitorController.getCosts()[1])
				progressBar.setValue((int) ((MonitorController.getCosts()[0] + MonitorController.getCosts()[1]) / MonitorController.getBudget() * 100));
			else
				progressBar.setString("Over budget");
			panel.revalidate();
			panel.repaint();
		});

		JButton moreButton = new JButton("More");
		moreButton.setFont(mainFont);
		moreButton.setBounds((int) (0.4 * width), (int) (0.7 * height), (int) (0.2 * width), height / 10);
		moreButton.addActionListener(e -> {
			timer.stop();
			more();
		});

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		back.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				timer.stop();
				MonitorController.logout();
				showWelcome();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH));
				panel.updateUI();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
				panel.updateUI();
			}
		});

		panel.add(back);
		panel.add(promptLabel);

		panel.add(electricityLabel);
		panel.add(electricityReading);
		panel.add(electricityCost);
		panel.add(gasLabel);
		panel.add(gasReading);
		panel.add(gasCost);
		panel.add(budgetLabel);
		panel.add(budget);
		panel.add(progressBar);

		panel.add(moreButton);

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
	 * This function add mouse listener to one JLabel.
	 *
	 * @param panel     This panel will show the JLabel.
	 * @param backImage Back image.
	 * @param back      Back JLabel.
	 */
	private void clickShowMonitor(JPanel panel, ImageIcon backImage, JLabel back) {
		back.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				monitor();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH));
				panel.updateUI();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				backImage.setImage(backImage.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
				panel.updateUI();
			}
		});
	}

	/**
	 * Create a more panel.
	 */
	private JPanel GUIMore() {
		JPanel panel = new JPanel(null);

		ImageIcon backImage = new ImageIcon("./images/back.png");
		JLabel back = new JLabel(backImage, JLabel.CENTER);
		back.setBounds((int) (0.01 * width), (int) (0.02 * height), 64, 64);
		clickShowMonitor(panel, backImage, back);

		JLabel promptLabel = new JLabel("Select one to continue", JLabel.CENTER);
		promptLabel.setFont(promptFont);
		promptLabel.setBounds(0, height / 8, width, height / 5);

		JButton viewButton = new JButton("View");
		viewButton.setFont(mainFont);
		viewButton.setBounds((int) (0.25 * width), (int) (0.6 * height), (int) (0.25 * width), height / 10);
		viewButton.addActionListener(e -> history());

		JButton budgetButton = new JButton("Budget");
		budgetButton.setFont(mainFont);
		budgetButton.setBounds((int) (0.5 * width), (int) (0.6 * height), (int) (0.25 * width), height / 10);
		budgetButton.addActionListener(e -> budget());

		JButton tariffButton = new JButton("Tariff");
		tariffButton.setFont(mainFont);
		tariffButton.setBounds((int) (0.25 * width), (int) (0.75 * height), (int) (0.25 * width), height / 10);
		tariffButton.addActionListener(e -> checkTariff());

		panel.add(back);
		panel.add(promptLabel);

		panel.add(viewButton);
		panel.add(budgetButton);
		panel.add(tariffButton);

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
		promptLabel.setFont(mainFont);

		//Radio buttons.
		JRadioButton day = new JRadioButton("By day");
		day.setFont(mainFont);
		day.setActionCommand("day");
		day.setSelected(true);

		JRadioButton week = new JRadioButton("By week");
		week.setFont(mainFont);
		week.setActionCommand("week");

		JRadioButton month = new JRadioButton("By month");
		month.setFont(mainFont);
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
		String[][] readingsDataByDay = MonitorController.getReadingsDataByDay();
		String[][] readingsDataByWeek = MonitorController.getReadingsDataByWeek();
		String[][] readingsDataByMonth = MonitorController.getReadingsDataByMonth();

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
		backButton.setFont(mainFont);
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
		promptLabel.setFont(mainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel oldBudgetLabel = new JLabel("Old budget:");
		oldBudgetLabel.setFont(mainFont);

		JLabel newBudgetLabel = new JLabel("New budget:");
		newBudgetLabel.setFont(mainFont);

		JLabel oldBudget = new JLabel(MonitorController.getBudget() + " £");
		oldBudget.setFont(mainFont);

		JTextField newBudget = new JTextField();

		centerPanel.add(oldBudgetLabel);
		centerPanel.add(oldBudget);
		centerPanel.add(newBudgetLabel);
		centerPanel.add(newBudget);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(mainFont);
		backButton.addActionListener(e -> showMore());

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(mainFont);
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
			MonitorController.updateBudget(budget);
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
		promptLabel.setFont(mainFont);

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel electricity = new JLabel("Electricity:");
		electricity.setFont(mainFont);

		JLabel gas = new JLabel("Gas:");
		gas.setFont(mainFont);

		JLabel electricityTariff = new JLabel(ManagerController.getTariffElectricity() + " p/KWh");
		electricityTariff.setFont(mainFont);

		JLabel gasTariff = new JLabel(ManagerController.getTariffGas() + " p/KWh");
		gasTariff.setFont(mainFont);

		centerPanel.add(electricity);
		centerPanel.add(electricityTariff);
		centerPanel.add(gas);
		centerPanel.add(gasTariff);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(mainFont);
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
	 * This function shows a message dialog on screen.
	 *
	 * @param text        The message.
	 * @param title       The window title.
	 * @param messageType The type of this message.
	 */
	private void showMessageDialog(String text, String title, int messageType) {
		JLabel promptLabel = new JLabel(text, JLabel.CENTER);
		promptLabel.setFont(mainFont);
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
		promptLabel.setFont(mainFont);
		return JOptionPane.showConfirmDialog(null, promptLabel, title, optionType);
	}

	/**
	 * ButtonInTable class.
	 */
	class ButtonInTable extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The button.
		 */
		private JButton button;

		/**
		 * Constructor function of ButtonInTable.
		 *
		 * @param actionCommand The command string.
		 */
		ButtonInTable(String actionCommand) {
			button = new JButton("Remove");
			button.setFont(mainFont);
			button.setActionCommand(actionCommand);
			button.addActionListener(e -> {
				try {
					if (showConfirmDialog("Confirm to remove this customer?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
						return;
					ManagerController.removeCustomer(Long.parseLong(e.getActionCommand()));
					showMessageDialog("Remove successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
					showManager();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getCellEditorValue() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			return button;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			return button;
		}
	}
}
