import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
public class PaymentScheduler {
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
	}
	
	// Prompt for the users bill file
	public String promptForBillFilename() {
		System.out.print("Enter the name of your bills file: ");
		Scanner scan = new Scanner(System.in);
		String filename = scan.next();
		/* I learned that closing this scanner makes console input unavailable
		 * for the rest of the program.  This causes problems since we still need to
		 * read the payment periods.  Time for a small refactor, or maybe this won't
		 * be an issue after making a gui!
		 */
		//scan.close();
		return filename;
	}

	// Asks the user for the amount of payment periods
	public void promptForPaymentPeriods() {
		System.out.print("Enter the number of payment periods: ");
		Scanner scan = new Scanner(System.in);
		paymentPeriods = Integer.parseInt(scan.next());
		scan.close();
	}
	
	// Generate the most efficient schedule and store it
	public void generateSchedule() {
		// Sort the bills by due date first
		Collections.sort(bills, Bill.BillComparator);
		// Pass a copy of bills since it will get modified
		paymentSchedule = findOptimumSchedule(new ArrayList<Bill>(bills), paymentPeriods);
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
		// Instantiate the PaymentScheduler and attempt to load the file
		PaymentScheduler paymentScheduler = new PaymentScheduler();
		String filename = paymentScheduler.promptForBillFilename();
		try {
			paymentScheduler.setBills(BillFileParser.extractBills(filename));
			// Get payment periods
			paymentScheduler.promptForPaymentPeriods();
			// Generate and print the most efficient schedule
			paymentScheduler.generateSchedule();
			System.out.println(paymentScheduler.getSchedule().toString());
		} catch (FileNotFoundException e) {
			System.out.println("The bills file was not found: ");
			System.out.println(e.getMessage());
		}
	}
}
