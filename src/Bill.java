import java.util.Comparator;

/*
 * A bill has an amount due and a due date.  
 */
public class Bill {
	private String name;
	private float amountDue;
	private Date dueDate;

	public Bill(String name, float amountDue, Date dueDate) {
		this.name = name;
		this.amountDue = amountDue;
		this.dueDate = dueDate;
	}
	
	// Comparator to sort bills by due date
	public static Comparator<Bill> BillComparator = new Comparator<Bill>() {
		public int compare(Bill b1, Bill b2) {
			   return b1.getDueDate().compareTo(b2.getDueDate());
		}
	};

	// Getters
	public String getName() {
		return name;
	}
	
	public float getAmountDue() {
		return amountDue;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	// Easy printing
	@Override
	public String toString() {
		return name + ": " + String.valueOf(amountDue) + " due on " + dueDate.toString();
	}
}
