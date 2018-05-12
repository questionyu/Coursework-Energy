import javax.swing.*;
import java.awt.*;

class GUIBudget extends JPanel {
	GUIBudget() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Budget information");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel oldBudgetLabel = new JLabel("Old budget:");
		oldBudgetLabel.setFont(GUIMain.getUIMainFont());

		JLabel newBudgetLabel = new JLabel("New budget:");
		newBudgetLabel.setFont(GUIMain.getUIMainFont());

		JLabel oldBudget = new JLabel(Controller.getLoggedCustomer().getBudget() + " £");
		oldBudget.setFont(GUIMain.getUIMainFont());

		JTextField newBudget = new JTextField();

		centerPanel.add(oldBudgetLabel);
		centerPanel.add(oldBudget);
		centerPanel.add(newBudgetLabel);
		centerPanel.add(newBudget);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showMore());

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(GUIMain.getUIMainFont());
		confirmButton.addActionListener(e -> {
			String newBudgetString = newBudget.getText();
			if (newBudgetString.equals("")) {
				GUIMain.showMessageDialog("Budget can not be blank!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			double budget;
			try {
				budget = Double.parseDouble(newBudgetString);
			}catch (Exception exception) {
				GUIMain.showMessageDialog("Budget must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (budget < 0) {
				GUIMain.showMessageDialog("Budget must be a positive number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (GUIMain.showConfirmDialog("Confirm to update Budget?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			Controller.updateBudget(budget);
			GUIMain.showMessageDialog("Update successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			GUIMain.showMore();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(confirmButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
