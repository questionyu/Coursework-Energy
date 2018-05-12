import javax.swing.*;
import java.awt.*;

/**
 * Title        GUITariff.java
 * Description  This class shows information about tariff.
 */
class GUITariff extends JPanel {
	GUITariff() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("Tariff information");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 25, 25));

		JLabel tariffElectricityLabel = new JLabel("Electricity tariff:");
		tariffElectricityLabel.setFont(GUIMain.getUIMainFont());

		JLabel electricityLabel = new JLabel(Controller.getTariffElectricity() + " p/KWh");
		electricityLabel.setFont(GUIMain.getUIMainFont());

		JLabel tariffGasLabel = new JLabel("Gas tariff:");
		tariffGasLabel.setFont(GUIMain.getUIMainFont());

		JLabel gasLabel = new JLabel(Controller.getTariffGas() + " p/KWh");
		gasLabel.setFont(GUIMain.getUIMainFont());

		centerPanel.add(tariffElectricityLabel);
		centerPanel.add(electricityLabel);
		centerPanel.add(tariffGasLabel);
		centerPanel.add(gasLabel);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showManager());

		JButton updateButton = new JButton("Update");
		updateButton.setFont(GUIMain.getUIMainFont());
		updateButton.addActionListener(e -> GUIMain.updateTariff());

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(updateButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
