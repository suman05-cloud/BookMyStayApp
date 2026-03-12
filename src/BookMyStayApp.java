import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp - Use Case 4: Room Search & Availability Check
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Room Search Service Demo\n");

        RoomInventory inventory = new RoomInventory();
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();
        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
        System.out.println("Search completed. System state remains unchanged.");
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
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
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
    public Map<String, Integer> getRoomAvailability() {
        return new HashMap<>(roomAvailability);
    }
}

class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory, Room single, Room dbl, Room suite) {
        Map<String, Integer> availability = inventory.getRoomAvailability();
        if (availability.getOrDefault("Single", 0) > 0) {
            System.out.println("Single Room:");
            single.displayRoomDetails();
            System.out.println("Available: " + availability.get("Single") + "\n");
        }
        if (availability.getOrDefault("Double", 0) > 0) {
            System.out.println("Double Room:");
            dbl.displayRoomDetails();
            System.out.println("Available: " + availability.get("Double") + "\n");
        }
        if (availability.getOrDefault("Suite", 0) > 0) {
            System.out.println("Suite Room:");
            suite.displayRoomDetails();
            System.out.println("Available: " + availability.get("Suite") + "\n");
        }
    }
}
