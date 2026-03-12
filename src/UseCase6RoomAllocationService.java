import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * ===========================================================================
 * MAIN CLASS - UseCase6RoomAllocation
 * ===========================================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * 
 * Description:
 * This class demonstrates how booking
 * requests are confirmed and rooms
 * are allocated safely.
 * 
 * It consumes booking requests in FIFO
 * order and updates inventory immediately.
 * 
 * @version 6.0
 */
public class UseCase6RoomAllocationService {

    /**
     * Application entry point.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        // Initialize Centralized Inventory and services
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // intake requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process and Allocate
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.processNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}

/**
 * ===========================================================================
 * CLASS - RoomAllocationService
 * ===========================================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * 
 * Description:
 * This class is responsible for confirming
 * booking requests and assigning rooms.
 * 
 * It ensures:
 * - Each room ID is unique
 * - Inventory is updated immediately
 * - No room is double-booked
 * 
 * @version 6.0
 */
class RoomAllocationService {

    /** Stores all allocated room IDs to prevent duplicate assignments. */
    private Set<String> allocatedRoomIds;

    /** Stores assigned room IDs by room type. */
    private Map<String, Set<String>> assignedRoomsByType;

    /**
     * Initializes allocation tracking structures.
     */
    public RoomAllocationService() {
        this.allocatedRoomIds = new HashSet<>();
        this.assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning
     * a unique room ID and updating inventory.
     * 
     * @param reservation booking request
     * @param inventory centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        int availability = inventory.getAvailability(roomType);

        if (availability > 0) {
            String roomId = generateRoomId(roomType);
            
            // Record the allocation
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);
            
            // Update Inventory (Atomic Logical Operation)
            inventory.updateAvailability(roomType, availability - 1);
            
            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + 
                               ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " + reservation.getGuestName() + 
                               " - No " + roomType + " rooms available.");
        }
    }

    /**
     * Generates a unique room ID
     * for the given room type.
     * 
     * @param roomType type of room
     * @return unique room ID
     */
    private String generateRoomId(String roomType) {
        int nextId = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        String roomId = roomType + "-" + nextId;
        
        // Defensive check for uniqueness
        while (allocatedRoomIds.contains(roomId)) {
            nextId++;
            roomId = roomType + "-" + nextId;
        }
        return roomId;
    }
}

// Support classes (from prior Use Cases) - Internal to this file for UC6
// (Using classes defined in the package scope or redefined here)
// Note: Since they are already in separate files and in this project, 
// I will redefine them as non-public here to make the file self-contained as requested.

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
