import javax.swing.*;
import java.awt.*;

/**
 * Title        GUIMain.java
 * Description  This class controls the UI.
 */
class GUIMain extends JFrame {
	/**
	 * The cardLayout instance which manages all UI of energy system.
	 */
	private static CardLayout energyCardLayout;

	/**
	 * The panel which use the card layout.
	 */
	private static JPanel energyPanel;

	/**
	 * The main font of UI.
	 */
	private static Font UIMainFont;

	/**
	 * Constructor function of GUIMain.
	 * This function creates a JFrame to contain a JPanel which uses CardLayout to display all things.
	 */
	GUIMain() {
		super("Energy management and monitoring system");

		//For better looks.
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
			e.printStackTrace();
		}

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(960, 540);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		energyCardLayout = new CardLayout();
		energyPanel = new JPanel(energyCardLayout);

		this.setContentPane(energyPanel);

		UIMainFont = new Font("Segoe UI", Font.PLAIN, 25);

		this.setVisible(true);
	}

	/**
	 * This function creates a welcome panel and adds it to CardLayout.
	 */
	static void welcome() {
		energyPanel.add(new GUIWelcome(), "GUIWelcome");
		showWelcome();
	}

	/**
	 * This function shows the welcome panel.
	 */
	static void showWelcome() {
		energyCardLayout.show(energyPanel, "GUIWelcome");
	}

	/**
	 * This function creates a manager panel and adds it to CardLayout.
	 */
	static void manager() {
		energyPanel.add(new GUIManager(), "GUIManager");
		showManager();
	}

	/**
	 * This function shows the manager panel.
	 */
	static void showManager() {
		energyCardLayout.show(energyPanel, "GUIManager");
	}

	/**
	 * This function creates a addCustomer panel and adds it to CardLayout.
	 */
	static void addCustomer() {
		energyPanel.add(new GUIAddCustomer(), "GUIAddCustomer");
		showAddCustomer();
	}

	/**
	 * This function shows the addCustomer panel.
	 */
	static void showAddCustomer() {
		energyCardLayout.show(energyPanel, "GUIAddCustomer");
	}

	/**
	 * This function creates a removeCustomer panel and adds it to CardLayout.
	 */
	static void removeCustomer() {
		energyPanel.add(new GUIRemoveCustomer(), "GUIRemoveCustomer");
		showRemoveCustomer();
	}

	/**
	 * This function shows the removeCustomer panel.
	 */
	static void showRemoveCustomer() {
		energyCardLayout.show(energyPanel, "GUIRemoveCustomer");
	}

	/**
	 * This function creates a tariff panel and adds it to CardLayout.
	 */
	static void tariff() {
		energyPanel.add(new GUITariff(), "GUITariff");
		showTariff();
	}

	/**
	 * This function shows the tariff panel.
	 */
	static void showTariff() {
		energyCardLayout.show(energyPanel, "GUITariff");
	}

	/**
	 * This function creates a updateTariff panel and adds it to CardLayout.
	 */
	static void updateTariff() {
		energyPanel.add(new GUIUpdateTariff(), "GUIUpdateTariff");
		showUpdateTariff();
	}

	/**
	 * This function shows the updateTariff panel.
	 */
	static void showUpdateTariff() {
		energyCardLayout.show(energyPanel, "GUIUpdateTariff");
	}

	/**
	 * This function creates a monitor panel and adds it to CardLayout.
	 */
	static void monitor() {
		energyPanel.add(new GUIMonitor(), "GUIMonitor");
		showMonitor();
	}

	/**
	 * This function shows the monitor panel.
	 */
	static void showMonitor() {
		energyCardLayout.show(energyPanel, "GUIMonitor");
	}

	/**
	 * This function shows a input dialog on screen.
	 *
	 * @param text        The prompt message.
	 * @param title       The window title.
	 * @param messageType The type of this message.
	 * @return The input string.
	 */
	static String showInputDialog(String text, String title, int messageType) {
		JLabel promptLabel = new JLabel(text);
		promptLabel.setFont(getUIMainFont());
		return JOptionPane.showInputDialog(null, promptLabel, title, messageType);
	}

	/**
	 * This function shows a message dialog on screen.
	 *
	 * @param text        The message.
	 * @param title       The window title.
	 * @param messageType The type of this message.
	 */
	static void showMessageDialog(String text, String title, int messageType) {
		JLabel promptLabel = new JLabel(text, JLabel.CENTER);
		promptLabel.setFont(getUIMainFont());
		JOptionPane.showMessageDialog(null, promptLabel, title, messageType);
	}

	/**
	 * This function shows a confirm dialog on screen.
	 *
	 * @param text       The message.
	 * @param title      The window title.
	 * @param optionType The type of this message.
	 */
	static int showConfirmDialog(String text, String title, int optionType) {
		JLabel promptLabel = new JLabel(text, JLabel.CENTER);
		promptLabel.setFont(getUIMainFont());
		return JOptionPane.showConfirmDialog(null, promptLabel, title, optionType);
	}

	/**
	 * Getter function of main font of UI.
	 *
	 * @return The main font.
	 */
	static Font getUIMainFont() {
		return UIMainFont;
	}
}
