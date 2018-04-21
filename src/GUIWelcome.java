import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIWelcome.java
 * Description  This class shows a welcome and login window.
 */
class GUIWelcome extends JPanel {
	/**
	 * Constructor function. Create a panel which shows a welcome and two buttons.
	 */
	GUIWelcome() {
		super(new BorderLayout());

		// BorderLayout.CENTER
		JLabel welcomeLabel = new JLabel("Welcome!", JLabel.CENTER);
		welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 50));

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();

		JButton loginButton = new JButton("Login");
		loginButton.setFont(GUIMain.getUIMainFont());
		loginButton.addActionListener(e -> {
			String stringID = GUIMain.showInputDialog("Your ID:", "Input dialog", JOptionPane.QUESTION_MESSAGE);
			if (stringID == null || stringID.equals(""))
				return;
			int ID;
			try {
				ID = Integer.parseInt(stringID);
			} catch (Exception exception) {
				GUIMain.showMessageDialog("ID must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Controller.getIDType(ID) < 0) {
				GUIMain.showMessageDialog("ID does not exist!", "Whoops!", JOptionPane.ERROR_MESSAGE);
			} else if (Controller.getIDType(ID) == 0)
				GUIMain.manager();
			else
				GUIMain.monitor();
		});

		southPanel.add(loginButton);

		add(welcomeLabel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
