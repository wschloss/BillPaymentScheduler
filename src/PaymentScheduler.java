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
	
	// Prompt for the users bills to pay
	public void promptForBills() {
		boolean done = false;
		Scanner scan = new Scanner(System.in);
		
		// Continue collecting info until user is done
		while (!done) {
		  System.out.print("Enter name of a new bill or 'done' to finish: ");
		  String name = scan.next();
		  // Stop collecting info if user is done
		  if (name.equals("done")) {
			  done = true;
			  break;
		  }
		  // Prompt for amount and date
		  System.out.print("Enter the amount due: ");
		  float amountDue = scan.nextFloat();
		  System.out.print("Enter the month due: ");
		  int month = scan.nextInt();
		  System.out.print("Enter the day due: ");
		  int day = scan.nextInt();
		  System.out.print("Enter the year due: ");
		  int year = scan.nextInt();
		  
		  // Add new bill to the list
		  bills.add( new Bill( name, amountDue, new Date(month, day, year) ) );
		}
		
		System.out.print("Enter the number of periods to pay the bills in: ");
		paymentPeriods = scan.nextInt();
	}
	
	// Generate the most efficient schedule and store it
	public void generateSchedule() {
		// Sort the bills by due date first
		Collections.sort(bills, Bill.BillComparator);
		// Pass a copy of bills since it will get modified
		paymentSchedule = findOptimumSchedule(new ArrayList<Bill>(bills), paymentPeriods);
	}
	
	// Algorithm that finds the schedule with the minimum cost
	public Schedule findOptimumSchedule(ArrayList<Bill> bills, int paymentPeriods) {
		// Recursive relation can be defined as:
		// best(all, kPeriods) = packet(last j bills) + best(all - last j, kPeriods - 1)
		// Iterating over j can find the optimum schedule
		
		Schedule best = new Schedule();
		
		// Base Cases
		
		// Must pay all in one period
		if (paymentPeriods == 1) {
			best.addPacket(new Packet(bills));
			return best;
		}
		
		// Best is to pay one each period if bills.size is equal to number of periods
		if (bills.size() == paymentPeriods) {
			for (int i = 0; i < paymentPeriods; i++) {
				Packet p = new Packet();
				p.addBill(bills.remove(bills.size() - 1));
				best.addPacket(p);
			}
			return best;
		}
		
		// Pay at least one today, all the way to pay one on all previous days
		for (int i = 1; i < bills.size() - paymentPeriods + 1; i++) {
			Packet p = new Packet();
			// Add the last j bills to this packet
			for (int j = 0; j < i; j++) {
		        // Move bill from array to packet.
				p.addBill(bills.remove(bills.size() - 1));
			}
			// Recursive call to find full schedule
			Schedule attempt = findOptimumSchedule(bills, paymentPeriods - 1).addPacket(p);
			if (attempt.cost() < best.cost())
				best = attempt;
		}
		
		return best;
	}
	
	// Getters
	public Schedule getSchedule() {
		return paymentSchedule;
	}

	public static void main(String[] args) {
		// Instantiate the PaymentScheduler and begin prompt
		PaymentScheduler paymentScheduler = new PaymentScheduler();
		paymentScheduler.promptForBills();
		// Generate and print the most efficient schedule
		paymentScheduler.generateSchedule();
		System.out.println(paymentScheduler.getSchedule().toString());
	}

}
