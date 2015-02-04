import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This class reads the bill file and constructs the bill objects appropriately
 */
public final class BillFileParser {
	// Takes a filename, and extracts all bill info to create a list of bill objects.
	// Thrown exception should be handled by the calling function to adhere to "catch late".
	public static ArrayList<Bill> extractBills(String filename) throws FileNotFoundException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		FileReader reader = new FileReader(filename); //Throws exception here
		// Wrap in scanner for convenient reading functions
		Scanner scan = new Scanner(reader);
		while (scan.hasNext()) {
			// Read three lines of bill data
			String name = scan.next();
			String date = scan.next();
			float amount = scan.nextFloat();
			// Create new bill by extracting the date string's info
			Bill bill = new Bill(name, amount, extractDateFromString(date));
			bills.add(bill);
		}
		scan.close();
		return bills;
	}

	// Helper function that takes a string 'm/d/y' and creates a date object
	public static Date extractDateFromString(String input) {
		// Split by the simplest regex I've ever had to come up with
		String tokens[] = input.split("/");
		int month = Integer.parseInt(tokens[0]);
		int day = Integer.parseInt(tokens[1]);
		int year = Integer.parseInt(tokens[2]);
		return new Date(month, day, year);
	}
}
