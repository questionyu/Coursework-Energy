import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIUpdateTariff.java
 * Description  This class shows a panel to update tariff.
 */
class GUIUpdateTariff extends JPanel {
	GUIUpdateTariff() {
		super(new BorderLayout());

		// BorderLayout.NORTH
		JLabel promptLabel = new JLabel("New tariff");
		promptLabel.setFont(GUIMain.getUIMainFont());

		// BorderLayout.CENTER
		JPanel centerPanel = new JPanel(new GridLayout(2, 2, 25, 25));

		JLabel tariffElectricityLabel = new JLabel("Electricity:");
		tariffElectricityLabel.setFont(GUIMain.getUIMainFont());

		JLabel tariffGasLabel = new JLabel("Gas:");
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

		JButton confirmButton = new JButton("Confirm");
		confirmButton.setFont(GUIMain.getUIMainFont());
		confirmButton.addActionListener(e -> {
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
			if (tariffElectricity < 0 || tariffGas < 0) {
				GUIMain.showMessageDialog("Tariff must be a positive number!", "Whoops!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (GUIMain.showConfirmDialog("Confirm to update tariff?", "Confirmation", JOptionPane.YES_NO_OPTION) != 0)
				return;
			Controller.updateTariff(tariffElectricity, tariffGas);
			GUIMain.showMessageDialog("Update successfully!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			GUIMain.showTariff();
		});

		southPanel.add(backButton);
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(confirmButton);

		add(promptLabel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}
