import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * BookMyStayApp - Use Case 5: Booking Request (FIFO)
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Welcome to BookMyStay Hotel System   ");
        System.out.println("========================================");
        System.out.println("Application Name: BookMyStayApp");
        System.out.println("Version: 5.1");
        System.out.println("Status: Initialized Successfully");
        System.out.println("========================================\n");

        RoomInventory inventory = new RoomInventory();
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        System.out.println("--- Searching for Available Rooms ---");
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);

        System.out.println("--- Processing Booking Requests ---");
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Double"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        System.out.println("\nProcessing queued requests:");
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.processNextRequest();
            System.out.println("Processing: Guest: " + request.getGuestName() + ", Room Type: " + request.getRoomType());
        }
    }
}

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
        System.out.println("Beds: " + numberOfBeds + ", Size: " + squareFeet + " sqft, Price: " + pricePerNight);
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
    public Map<String, Integer> getRoomAvailability() { return new HashMap<>(roomAvailability); }
}

class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Room single, Room dbl, Room suite) {
        Map<String, Integer> availability = inventory.getRoomAvailability();
        for (String type : availability.keySet()) {
            System.out.println(type + " Room: " + availability.get(type) + " available.");
        }
        System.out.println();
    }
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
    public void addRequest(Reservation r) { requestQueue.add(r); System.out.println("Queued: " + r.getGuestName()); }
    public Reservation processNextRequest() { return requestQueue.poll(); }
    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}
