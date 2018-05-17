import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIManager.java
 * Description  This class shows functions of manager.
 */
class GUIManager extends JPanel {
	GUIManager() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one to continue");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JButton addCustomerButton = new JButton("Add customer");
		addCustomerButton.setFont(GUIMain.getUIMainFont());
		addCustomerButton.addActionListener(e -> GUIMain.addCustomer());

		JButton removeCustomerButton = new JButton("Remove customer");
		removeCustomerButton.setFont(GUIMain.getUIMainFont());
		removeCustomerButton.addActionListener(e -> GUIMain.removeCustomer());

		JButton tariffButton = new JButton("Tariff");
		tariffButton.setFont(GUIMain.getUIMainFont());
		tariffButton.addActionListener(e -> GUIMain.tariff());

		JButton viewButton = new JButton("View");
		viewButton.setFont(GUIMain.getUIMainFont());
		viewButton.addActionListener(e -> GUIMain.view());

		centerPanel.add(addCustomerButton);
		centerPanel.add(removeCustomerButton);
		centerPanel.add(tariffButton);
		centerPanel.add(viewButton);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> {
			Controller.stopManagerTimer();
			GUIMain.showWelcome();
		});

		// Only for demo
		JButton billsButton = new JButton("Bills (Only for demo)");
		billsButton.setFont(GUIMain.getUIMainFont());
		billsButton.addActionListener(e -> {
			Controller.bills();
			GUIMain.showMessageDialog("Generate and send bills successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(billsButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
