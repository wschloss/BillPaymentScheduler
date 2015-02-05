import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * The PaymentScheduler is used to prompt for the users bills and the amount
 * of payment periods to pay them all. Then, it generates the best way to pay
 * bills by grouping them into 'packets' which are to be paid all at once.
 * I have defined the best schedule as the one which minimizes the maximum
 * cost of a packet in the schedule.
 * 
 * I would phrase the problem as the following:
 * 
 * Given a list of bills to pay 'b', partition the bills into 'k' groups such that
 * the maximum cost of a group is minimized and the bills are all paid on time.
 * 
 * Definitions I am using:
 * 
 * cost(packet) = sum(billAmounts)
 * cost(schedule) = max( cost(packetsInSchedule) )
 */
public class PaymentScheduler extends JFrame {
	// Frame dimensions
	public static final int DEFAULT_SIZE_X = 900;
	public static final int DEFAULT_SIZE_Y = 600;
	
	// Gui panels, sidebar has buttons for loading/generating
	// logPanel is used to display results
	private SideBar sideBar;
	private LogPanel logPanel;
	
	// Store list of bill objects and the best schedule
	private ArrayList<Bill> bills;
	private Schedule paymentSchedule;
	// paymentPeriods is the number of packets the schedule can have
	private int paymentPeriods;

	public PaymentScheduler() {
		// Initialize the bills list
		bills = new ArrayList<Bill>();
		// Init payment periods to zero for safety
		paymentPeriods = 0;
		
		// Gui initialization
		this.setSize(DEFAULT_SIZE_X, DEFAULT_SIZE_Y);
		this.setResizable(false);
		sideBar = new SideBar(this);
		logPanel = new LogPanel();
		this.add(sideBar, BorderLayout.WEST);
		this.add(logPanel, BorderLayout.CENTER);
	}

	// Asks the user for the amount of payment periods with a popup
	public int promptForPaymentPeriods() {
		// Display an option pane, get the users input (number of payment periods) as a string
		String s = (String)JOptionPane.showInputDialog(
							this,
							"Enter the number of payment periods",
							"Generate a bill schedule",
							JOptionPane.PLAIN_MESSAGE);
		return Integer.parseInt(s);
	}
	
	// Generate the most efficient schedule and store it
	public void generateSchedule() {
		// Sort the bills by due date first
		Collections.sort(bills, Bill.BillComparator);
		// Ask the user for the number of periods with a popup
		paymentPeriods = promptForPaymentPeriods();
		// Pass a copy of bills to the algorithm since it will get modified
		paymentSchedule = findOptimumSchedule(new ArrayList<Bill>(bills), paymentPeriods);
		//Print the schedule to the log pane
		logPanel.setLogText(paymentSchedule.toString());
	}
	
	// Algorithm that finds the schedule with the minimum cost
	public Schedule findOptimumSchedule(ArrayList<Bill> billList, int paymentPeriods) {
		// Recursive relation can be defined as:
		// best(all, kPeriods) = packet(last j bills) + best(all - last j, kPeriods - 1)
		// Iterating over j can find the optimum schedule
		
		Schedule best = new Schedule();
		
		// Base Cases
		
		// Must pay all in one period
		if (paymentPeriods == 1) {
			best.addPacket(new Packet(billList));
			return best;
		}
		// Packet's to make today
		Packet p = new Packet();
		// Best is to pay one each period if billList.size is equal to number of periods
		if (billList.size() == paymentPeriods) {
			for (int i = 0; i < paymentPeriods; i++) {
				p = new Packet();
				p.addBill(billList.get(i));
				best.addPacket(p);
			}
			return best;
		}
		
		int maxIterations = billList.size() - paymentPeriods + 1;
		// Pay at least one today, all the way to pay one on all previous days
		for (int i = 0; i < maxIterations; i++) {
			// Add another bill (the last one) to the packet to pay now
			p.addBill(billList.remove(billList.size() - 1));
			// Recursive call to find full schedule
			Schedule attempt = findOptimumSchedule(new ArrayList<Bill>(billList), paymentPeriods - 1).addPacket(new Packet(p));
			if (attempt.cost() < best.cost())
				best = attempt;
		}
		
		return best;
	}
	
	// Getters
	public Schedule getSchedule() {
		return paymentSchedule;
	}
	
	public ArrayList<Bill> getBills() {
		return bills;
	}
	
	//Setters
	public void setBills(ArrayList<Bill> bills) {
		this.bills = bills;
	}

	public static void main(String[] args) {
		// Init the gui as a runnable
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PaymentScheduler gui = new PaymentScheduler();
				// Set to exit on close and display the gui
				gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
				gui.setVisible(true);
			}
		});
	}
}
