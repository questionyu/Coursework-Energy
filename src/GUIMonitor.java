import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIMonitor.java
 * Description  This class shows functions of monitor.
 */
class GUIMonitor extends JPanel {
	GUIMonitor() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one to continue");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(3, 2, 5, 5));

		JLabel electricityLabel = new JLabel("Electricity:");
		electricityLabel.setFont(GUIMain.getUIMainFont());
		JLabel gasLabel = new JLabel("Gas:");
		gasLabel.setFont(GUIMain.getUIMainFont());
		JLabel budgetLabel = new JLabel("Budget:");
		budgetLabel.setFont(GUIMain.getUIMainFont());

		JLabel electricityReading = new JLabel(Controller.getLoggedMonitor().getReadings()[0] + " KWh");
		electricityReading.setFont(GUIMain.getUIMainFont());
		JLabel gasReading = new JLabel(Controller.getLoggedMonitor().getReadings()[1] + " KWh");
		gasReading.setFont(GUIMain.getUIMainFont());
		JLabel budget = new JLabel(Controller.getLoggedCustomer().getBudget() + " £");
		budget.setFont(GUIMain.getUIMainFont());

		JPanel budgetPanel = new JPanel();
		budgetPanel.setBackground(Color.CYAN);
		budgetPanel.add(budget);

		centerPanel.add(electricityLabel);
		centerPanel.add(electricityReading);
		centerPanel.add(gasLabel);
		centerPanel.add(gasReading);
		centerPanel.add(budgetLabel);
		centerPanel.add(budgetPanel);

		// TODO
		JButton addCustomerButton = new JButton("Send readings (Temporarily)");
		addCustomerButton.setFont(GUIMain.getUIMainFont());
		addCustomerButton.addActionListener(e -> {
			Controller.sendReadings();
			GUIMain.showMessageDialog("Successfully send!", "Done!", JOptionPane.INFORMATION_MESSAGE);
		});

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showWelcome());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
