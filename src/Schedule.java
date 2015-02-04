import java.util.ArrayList;

/*
 * A schedule object contains packets of bills that should be paid together.
 * A packet has an associated due date, which is equivalent to the first bill
 * in the packet due.
 */
public class Schedule {
	// Store list of bill packets
	private ArrayList<Packet> billPackets;

	public Schedule() {
		// Initialize the billPackets
		billPackets = new ArrayList<Packet>();
	}
	
	// Adds a new packet to the schedule, returns this schedule for chaining
	public Schedule addPacket(Packet packet) {
		billPackets.add(packet);
		return this;
	}
	
	// The cost of a schedule is the cost of the most expensive
	// packet.  The goal is to minimize this.
	// If no packets, returns large cost to avoid the best schedule
	// found as paying nothing
	public float cost() {
		if (billPackets.size() == 0)
			return Float.MAX_VALUE;
		
		float maxPacketCost = 0;
		for (Packet p : billPackets) {
			if(p.getCost() > maxPacketCost)
				maxPacketCost = p.getCost();
		}
		return maxPacketCost;
	}
	
	// Easy printing
	@Override
	public String toString() {
		String scheduleString = "";
		for (Packet p : billPackets) {
			scheduleString += ("\n" + p.toString() + "\n");
		}
		return scheduleString;
	}
}
