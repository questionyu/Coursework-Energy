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
		JLabel promptLabel = new JLabel("Monitor");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(3, 3, 5, 5));

		JLabel electricityLabel = new JLabel("Electricity:");
		electricityLabel.setFont(GUIMain.getUIMainFont());

		JLabel gasLabel = new JLabel("Gas:");
		gasLabel.setFont(GUIMain.getUIMainFont());

		JLabel budgetLabel = new JLabel("Budget:");
		budgetLabel.setFont(GUIMain.getUIMainFont());

		JLabel electricityReading = new JLabel(((int) Controller.getReading()[0]) + " KWh");
		electricityReading.setFont(GUIMain.getUIMainFont());

		JLabel electricityCost = new JLabel(Controller.getCosts()[0] + " £");
		electricityCost.setFont(GUIMain.getUIMainFont());

		JLabel gasReading = new JLabel(((int) Controller.getReading()[1]) + " KWh");
		gasReading.setFont(GUIMain.getUIMainFont());

		JLabel gasCost = new JLabel(Controller.getCosts()[1] + " £");
		gasCost.setFont(GUIMain.getUIMainFont());

		JLabel budget = new JLabel(Controller.getBudget() + " £");
		budget.setFont(GUIMain.getUIMainFont());

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
			this.revalidate();
			this.repaint();
		});

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> {
			timer.stop();
			Controller.logout();
			GUIMain.showWelcome();
		});

		JButton moreButton = new JButton("More");
		moreButton.setFont(GUIMain.getUIMainFont());
		moreButton.addActionListener(e -> {
			timer.stop();
			GUIMain.more();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(moreButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		timer.start();
	}
}
