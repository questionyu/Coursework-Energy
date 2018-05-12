import javax.swing.*;
import java.awt.*;

class GUICheckTariff extends JPanel {
	GUICheckTariff() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Tariff information");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		JLabel electricity = new JLabel("Electricity:");
		electricity.setFont(GUIMain.getUIMainFont());

		JLabel gas = new JLabel("Gas:");
		gas.setFont(GUIMain.getUIMainFont());

		JLabel electricityTariff = new JLabel(Controller.getTariffElectricity() + " p/KWh");
		electricityTariff.setFont(GUIMain.getUIMainFont());

		JLabel gasTariff = new JLabel(Controller.getTariffGas() + " p/KWh");
		gasTariff.setFont(GUIMain.getUIMainFont());

		centerPanel.add(electricity);
		centerPanel.add(electricityTariff);
		centerPanel.add(gas);
		centerPanel.add(gasTariff);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showMore());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
