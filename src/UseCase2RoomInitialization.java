/**
 * UseCase2RoomInitialization demonstrates room initialization and availability tracking.
 * This class creates room objects and displays their properties and current availability.
 * 
 * @author Suman Patari
 * @version 2.1
 */
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        System.out.println("Hotel Room Initialization\n");

        // Static availability tracking using simple variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Creating room objects using Polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display Single Room details and availability
        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailability + "\n");

        // Display Double Room details and availability
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailability + "\n");

        // Display Suite Room details and availability
        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailability);
    }
}
