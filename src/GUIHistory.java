import javax.swing.*;
import java.awt.*;

class GUIHistory extends JPanel {
	GUIHistory() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Historical information");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JScrollPane scrollPane = new JScrollPane();

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showMore());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		add(promptLabel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
