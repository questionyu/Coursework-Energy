import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIView.java
 * Description  This class views the readings of customers for manager.
 */
class GUIView extends JPanel {
	GUIView() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Readings and bills");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		String[] columnNames = {"Name", "Electricity", "Gas", "Bill"};
		String[][] data = Controller.getReadingsAndBills();

		JTable table = new JTable(data, columnNames);
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
