import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/*
 * This class extends a JPanel and has button for load file and generate schedule
 */
public class SideBar extends JPanel {
	// Reference to the frame so we can load bills and then pass them
	PaymentScheduler parent;
	// Text field that displays the current loaded file
	JTextField loadedFile;
	

	public SideBar(PaymentScheduler parent) {
		this.parent = parent;
		// Layout, change this to make prettier
		this.setLayout(new GridLayout(3,1));
		
		// Create buttons for loading bills and generating a schedule
		JButton loadButton = new JButton("Load a bill file");
		loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadButton.addActionListener(new LoadListener());
		
		JButton generateScheduleButton = new JButton("Generate bill schedule");
		generateScheduleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		generateScheduleButton.addActionListener(new GenerateListener());
		
		// Init the loaded file text
		loadedFile = new JTextField();
		loadedFile.setText("No file loaded");
		loadedFile.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadedFile.setAlignmentY(Component.CENTER_ALIGNMENT);
		loadedFile.setEditable(false);
		
		// Add buttons/textField to this panel
		this.add(loadButton);
		this.add(loadedFile);
		this.add(generateScheduleButton);
	}
	
	// Listener definition for the load button.
	private class LoadListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Use an open file prompt to get file
			JFileChooser fileChooser = new JFileChooser();
			int selection = fileChooser.showOpenDialog(null);

			if (selection == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				// Attempt to load the bills from the file
				try {
					// Send the loaded bills to the parent frame
					parent.setBills(BillFileParser.extractBills(file.getAbsolutePath()));
					loadedFile.setText(file.getName());
				} catch (FileNotFoundException e) {
					System.out.println("The bills file was not found: ");
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	// Listener definition for the generate button
	private class GenerateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Generate a schedule if bills have been loaded
			if (parent.getBills().size() != 0) {
				parent.generateSchedule();
			}
		}
	}
}
