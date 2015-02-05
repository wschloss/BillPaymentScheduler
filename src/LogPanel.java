import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/*
 * This class extends a JPanel and displays any prompts to the user,
 * specifically the generated schedule.
 */
public class LogPanel extends JPanel {
	
	// The text pane where the schedule will be displayed
	private JTextPane log;

	public LogPanel() {
		// Change this to make it prettier
		this.setLayout(new GridLayout(1,1));
		
		// Init the text area of the panel
		log = new JTextPane();
		log.setAlignmentX(Component.CENTER_ALIGNMENT);
		log.setAlignmentY(Component.CENTER_ALIGNMENT);
		log.setEditable(false);
		
		// Add the text pane to this panel
		this.add(log);
	}

	// Appends the text to the log area
	public void appendToLog(String text) {
		log.setText(log.getText() + text);
	}
	
	// Sets the text in the log area
	public void setLogText(String text) {
		log.setText(text);
	}
}
