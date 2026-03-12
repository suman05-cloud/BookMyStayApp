import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * BookMyStayApp - Use Case 6: Reservation Confirmation & Room Allocation
 * 
 * This class handles the end-to-end flow from welcome message to
 * fair room allocation and inventory updates.
 * 
 * @author Suman Patari
 * @version 6.0
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        // Initialize Services and Inventory
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // intake requests (Simulating UC5)
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single")); // Changed to Match snapshot output
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process and Allocate (UC6)
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.processNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}

/**
 * Use Case 6: Room Allocation Service
 * 
 * Responsible for assigning unique room IDs and updating inventory.
 */
class RoomAllocationService {
    /** Stores all allocated room IDs to prevent duplicates. */
    private Set<String> allocatedRoomIds;

    /** Stores assigned room IDs grouped by type. */
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        this.allocatedRoomIds = new HashSet<>();
        this.assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning a unique room ID
     * and updating the inventory.
     * 
     * @param reservation booking request
     * @param inventory centralized inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        int availability = inventory.getAvailability(roomType);

        if (availability > 0) {
            String roomId = generateRoomId(roomType);
            
            // Record the allocation
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);
            
            // Update Inventory
            inventory.updateAvailability(roomType, availability - 1);
            
            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + 
                               ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " + reservation.getGuestName() + 
                               " - No " + roomType + " rooms available.");
        }
    }

    /**
     * Generates a unique room ID for the given room type.
     * Format: Type-Number (e.g., Single-1)
     */
    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        String roomId = roomType + "-" + count;
        
        // Ensure absolute uniqueness (defensive check)
        while (allocatedRoomIds.contains(roomId)) {
            count++;
            roomId = roomType + "-" + count;
        }
        return roomId;
    }
}

/** Domain Model and Core Services below - (From prior Use Cases) **/

abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }
}

class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super(3, 750, 5000.0); } }

class RoomInventory {
    private Map<String, Integer> roomAvailability;
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }
    public int getAvailability(String roomType) { return roomAvailability.getOrDefault(roomType, 0); }
    public void updateAvailability(String roomType, int count) { roomAvailability.put(roomType, count); }
}

class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) { this.guestName = guestName; this.roomType = roomType; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();
    public void addRequest(Reservation r) { requestQueue.add(r); }
    public Reservation processNextRequest() { return requestQueue.poll(); }
    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}
