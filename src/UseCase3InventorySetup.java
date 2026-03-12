/**
 * ===========================================================================
 * MAIN CLASS - UseCase3InventorySetup
 * ===========================================================================
 * Use Case 3: Centralized Room Inventory Management
 * 
 * Description:
 * This class demonstrates how room availability
 * is managed using a centralized inventory.
 * 
 * Room objects are used to retrieve pricing
 * and room characteristics.
 * 
 * No booking or search logic is introduced here.
 * 
 * @version 3.1
 */
public class UseCase3InventorySetup {

    /**
     * Application entry point.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory Status\n");

        // Initialize Centralized Inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize Room Domain Objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display Single Room details from Inventory
        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single") + "\n");

        // Display Double Room details from Inventory
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double") + "\n");

        // Display Suite Room details from Inventory
        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite"));
    }
}
