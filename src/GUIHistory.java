import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUIHistory extends JPanel implements ActionListener {
	/**
	 * The cardLayout instance which manages history panels.
	 */
	private static CardLayout historyCardLayout;

	/**
	 * The panel which use the card layout.
	 */
	private static JPanel historyPanel;

	GUIHistory() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

		JLabel promptLabel = new JLabel("Historical information");
		promptLabel.setFont(GUIMain.getUIMainFont());

		//Radio buttons.
		JRadioButton day = new JRadioButton("By day");
		day.setFont(GUIMain.getUIMainFont());
		day.setActionCommand("day");
		day.setSelected(true);

		JRadioButton week = new JRadioButton("By week");
		week.setFont(GUIMain.getUIMainFont());
		week.setActionCommand("week");

		JRadioButton month = new JRadioButton("By month");
		month.setFont(GUIMain.getUIMainFont());
		month.setActionCommand("month");

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(week);
		group.add(day);
		group.add(month);

		//Register a listener for the radio buttons.
		week.addActionListener(this);
		day.addActionListener(this);
		month.addActionListener(this);

		//Add radio buttons to a panel.
		JPanel radioPanel = new JPanel(new GridLayout(1, 0));
		radioPanel.add(day);
		radioPanel.add(week);
		radioPanel.add(month);

		northPanel.add(promptLabel);
		northPanel.add(Box.createHorizontalGlue());
		northPanel.add(radioPanel);

		// BorderLayout.CENTER
		historyCardLayout = new CardLayout();
		historyPanel = new JPanel(historyCardLayout);

		String[] columnNames = {"Date", "Electricity", "Gas", "Cost"};
		String[][] readingsDataByDay = Controller.getReadingsDataByDay();
		String[][] readingsDataByWeek = Controller.getReadingsDataByWeek();
		String[][] readingsDataByMonth = Controller.getReadingsDataByMonth();

		JTable tableByDay = new JTable(readingsDataByDay, columnNames);
		tableByDay.setFont(new Font(null, Font.PLAIN, 15));

		JTable tableByWeek = new JTable(readingsDataByWeek, columnNames);
		tableByDay.setFont(new Font(null, Font.PLAIN, 15));

		JTable tableByMonth = new JTable(readingsDataByMonth, columnNames);
		tableByDay.setFont(new Font(null, Font.PLAIN, 15));

		historyPanel.add(new JScrollPane(tableByDay), "day");
		historyPanel.add(new JScrollPane(tableByWeek), "week");
		historyPanel.add(new JScrollPane(tableByMonth), "month");

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showMore());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		add(northPanel, BorderLayout.NORTH);
		add(historyPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "day":
				historyCardLayout.show(historyPanel, "day");
				break;
			case "week":
				historyCardLayout.show(historyPanel, "week");
				break;
			case "month":
				historyCardLayout.show(historyPanel, "month");
				break;
		}
	}
}
