import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * BookMyStayApp is the central entry point for the Hotel Booking System.
 * This class consolidates all use cases from Application Entry to Booking Requests.
 * 
 * @author Suman Patari
 * @version 5.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // --- Use Case 1: Welcome Message ---
        System.out.println("========================================");
        System.out.println("   Welcome to BookMyStay Hotel System   ");
        System.out.println("========================================");
        System.out.println("Application Name: BookMyStayApp");
        System.out.println("Version: 5.1");
        System.out.println("Status: Initialized Successfully");
        System.out.println("========================================\n");

        // --- Use Case 2 & 3: Room Initialization & Centralized Inventory ---
        System.out.println("--- Initializing Room Inventory ---");
        RoomInventory inventory = new RoomInventory();
        
        // Domain Objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // --- Use Case 4: Room Search & Availability Check ---
        System.out.println("\n--- Searching for Available Rooms ---");
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);

        // --- Use Case 5: Booking Request Intake (FIFO) ---
        System.out.println("--- Processing Booking Requests ---");
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guest booking intents
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Adding to queue (FIFO arrival order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        System.out.println("\nReady for processing queued requests in arrival order:");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.processNextRequest();
            System.out.println("Processing: Guest: " + request.getGuestName() + ", Room Type: " + request.getRoomType());
        }
        
        System.out.println("\nApplication maintenance completed.");
    }
}

/**
 * Abstract class representing a general Room in the hotel.
 */
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/** Represents a single room (1 bed). */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/** Represents a double room (2 beds). */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/** Represents a luxury suite (3 beds). */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/**
 * Acts as the single source of truth for room availability.
 */
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getRoomAvailability() {
        return new HashMap<>(roomAvailability);
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * Provides search functionality for guests to view available rooms.
 */
class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Room single, Room dbl, Room suite) {
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.getOrDefault("Single", 0) > 0) {
            System.out.println("Single Room: " + availability.get("Single") + " available @ " + 1500.0 + "/night");
        }
        if (availability.getOrDefault("Double", 0) > 0) {
            System.out.println("Double Room: " + availability.get("Double") + " available @ " + 2500.0 + "/night");
        }
        if (availability.getOrDefault("Suite", 0) > 0) {
            System.out.println("Suite Room: " + availability.get("Suite") + " available @ " + 5000.0 + "/night");
        }
        System.out.println();
    }
}

/** Represents a booking request (intent). */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/** Manages booking requests in FIFO order. */
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation request) {
        requestQueue.add(request);
        System.out.println("Queued: " + request.getGuestName() + " for " + request.getRoomType() + " room.");
    }

    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
