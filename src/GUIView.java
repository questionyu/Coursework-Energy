import javax.swing.*;
import java.awt.*;

class GUIView extends JPanel {
	GUIView() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Readings and bills");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		String[] columnNames = {"Name", "Electricity", "Gas", "Bill"};
		String[][] readingsData = new String[Controller.getMonitors().size()][];
		for (int i = 0; i < readingsData.length; i++) {
			Monitor monitor = Controller.getMonitors().get(i);
			readingsData[i] = new String[]{monitor.getCustomer().getName(), "" + "1", "" + "2", "3"}; // Temporarily
		}

		JTable table = new JTable(readingsData, columnNames);
		table.setFont(new Font(null, Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(table);

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
}
