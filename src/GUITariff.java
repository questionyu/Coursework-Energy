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

		JLabel tariffElectricityLabel = new JLabel("New electricity tariff:");
		tariffElectricityLabel.setFont(GUIMain.getUIMainFont());

		JLabel tariffGasLabel = new JLabel("New Gas tariff:");
		tariffGasLabel.setFont(GUIMain.getUIMainFont());

		JTextField tariffElectricityTextField = new JTextField();
		JTextField tariffGasTextField = new JTextField();

		centerPanel.add(tariffElectricityLabel);
		centerPanel.add(tariffElectricityTextField);
		centerPanel.add(tariffGasLabel);
		centerPanel.add(tariffGasTextField);

		// BorderLayout.SOUTH
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.setFont(GUIMain.getUIMainFont());
		backButton.addActionListener(e -> GUIMain.showManager());

		JButton updateButton = new JButton("Update");
		updateButton.setFont(GUIMain.getUIMainFont());
		updateButton.addActionListener(e -> {
			String tariffElectricityString = tariffElectricityTextField.getText();
			String tariffGasString = tariffGasTextField.getText();
			if (tariffElectricityString.equals("") || tariffGasString.equals("")) {
				GUIMain.showMessageDialog("Tariff can not be blank!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			double tariffElectricity, tariffGas;
			try {
				tariffElectricity = Double.parseDouble(tariffElectricityString);
				tariffGas = Double.parseDouble(tariffGasString);
			}catch (Exception exception) {
				GUIMain.showMessageDialog("Tariff must be a number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Controller.updateTariff(tariffElectricity, tariffGas);
			GUIMain.showMessageDialog("Successfully updated!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			GUIMain.showManager();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(updateButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
