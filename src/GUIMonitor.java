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
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		// TODO
		JButton addCustomerButton = new JButton("Send readings (Temporarily)");
		addCustomerButton.setFont(GUIMain.getUIMainFont());
		addCustomerButton.addActionListener(e -> {
			Controller.sendReadings();
			GUIMain.showMessageDialog("Successfully send!", "Done!", JOptionPane.INFORMATION_MESSAGE);
		});

		centerPanel.add(addCustomerButton);

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
