import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Title        GUIRemoveCustomer.java
 * Description  This class help to remove a customer.
 */
class GUIRemoveCustomer extends JPanel {
	GUIRemoveCustomer() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one customer to remove");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel userListPanel = new JPanel();
		userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

		for (Customer customer : Controller.getCustomers()) {
			JButton customerButton = new JButton("\"" + customer.getName() + "\"" + customer.getAddress());
			customerButton.setFont(GUIMain.getUIMainFont());
			customerButton.addMouseListener(new mouseAdapter(customer));
			userListPanel.add(customerButton);
		}
		JScrollPane scrollPane = new JScrollPane(userListPanel);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showManager());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		add(promptLabel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
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
			if (GUIMain.showConfirmDialog("Confirm to remove this customer?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			Controller.removeCustomer(customer);
			GUIMain.showMessageDialog("Removed successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			GUIMain.showManager();
		}
	}
}
