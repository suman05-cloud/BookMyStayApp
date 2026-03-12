import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp - Use Case 3: Centralized Room Inventory Management
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory Status\n");

        RoomInventory inventory = new RoomInventory();
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single") + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double") + "\n");

        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite"));
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
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }
}
