import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIAddCustomer.java
 * Description  This class help to add a new customer.
 */
class GUIAddCustomer extends JPanel {
	GUIAddCustomer() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("New customer's information");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 25, 25));

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(GUIMain.getUIMainFont());

		JTextField nameTextField = new JTextField();

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setFont(GUIMain.getUIMainFont());

		JTextField addressTextField = new JTextField();

		centerPanel.add(nameLabel);
		centerPanel.add(nameTextField);
		centerPanel.add(addressLabel);
		centerPanel.add(addressTextField);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showManager());

		JButton addButton = new JButton("Add");
		addButton.setFont(GUIMain.getUIMainFont());
		addButton.addActionListener(e -> {
			String name = nameTextField.getText();
			String address = addressTextField.getText();
			if (name.equals("") || address.equals("")) {
				GUIMain.showMessageDialog("Name or address can not be blank!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (GUIMain.showConfirmDialog("Confirm to add new customer?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			if (!Controller.addCustomer(name, address)) {
				GUIMain.showMessageDialog("Customer already exists!", "Whoops!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			GUIMain.showMessageDialog("Add successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			GUIMain.showManager();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(addButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
