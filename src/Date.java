/*
 * A date has m/d/y and is used for storing due dates.
 * The object makes comparison of two dates much easier.
 */
public class Date implements Comparable<Date> {
	private int month;
	private int day;
	private int year;

	public Date(int month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}

	@Override
	public int compareTo(Date other) {
		// Year check
		if (this.year != other.getYear()) {
			// Years not equal, return 1 or -1
			return this.year < other.getYear() ? -1 : 1;
		}
		// Month check
		if (this.month != other.getMonth()) {
			// Months not equal, return 1 or -1
			return this.month < other.getMonth() ? -1 : 1;
		}
		// Day check
		if (this.day != other.getDay()) {
			// Days not equal, return 1 or -1
			return this.day < other.getDay() ? -1 : 1;
		}
		// All values are equal to reach this return, return 0
		return 0;
	}
	
	// Getters
	public int getYear() {
		return year;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	// Easy printing
	@Override
	public String toString() {
		return String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);
	}
}
