import javax.swing.*;
import java.awt.*;

class GUIMore extends JPanel {
	GUIMore() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Select one to continue");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JButton viewButton = new JButton("View");
		viewButton.setFont(GUIMain.getUIMainFont());
		viewButton.addActionListener(e -> GUIMain.history());

		JButton budgetButton = new JButton("Budget");
		budgetButton.setFont(GUIMain.getUIMainFont());
		budgetButton.addActionListener(e -> GUIMain.budget());

		JButton tariffButton = new JButton("Tariff");
		tariffButton.setFont(GUIMain.getUIMainFont());
		tariffButton.addActionListener(e -> GUIMain.checkTariff());

		centerPanel.add(viewButton);
		centerPanel.add(budgetButton);
		centerPanel.add(tariffButton);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showMonitor());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
