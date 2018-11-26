import javax.swing.*;

/**
 * Title        Main.java
 * Description  This class starts the whole system.
 */
public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			if (!Controller.loadCustomers()) {
				System.out.println("Load customers failed!");
				System.exit(1);
			}
			Controller.start();
		});
	}
}
