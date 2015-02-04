import java.util.ArrayList;

/*
 * A packet is a collection of bills to pay at once.
 * The associated cost is the sum of all the bill amounts.
 */
public class Packet {
	
	private ArrayList<Bill> bills;
	
	// Default to add bills later
	public Packet() {
		bills = new ArrayList<Bill>();
	}
	
	// Parameterized to add a list of bill at once
	public Packet(ArrayList<Bill> bills) {
		this.bills = bills;
	}
	
	// Copy constructor
	public Packet(Packet other) {
		this.bills = new ArrayList<Bill>(other.getBills());
	}
	
	public void addBill(Bill b) {
		bills.add(b);
	}
	
	// The cost of a packet is the sum of the bill amounts
	public float getCost() {
		float sum = 0;
		// Iterate over bills
		for (Bill b : bills) {
			sum += b.getAmountDue();
		}
		return sum;
	}
	
	// Getters
	public ArrayList<Bill> getBills() {
		return bills;
	}
	
	// Easy printing
	@Override
	public String toString() {
		String packetString = "[\n";
		for (Bill b : bills) {
			packetString += (b.toString() + "\n");
		}
		packetString += "]";
		return packetString;
	}
}
