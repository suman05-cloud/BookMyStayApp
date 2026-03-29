import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Goal: Introduce persistence and recovery concepts by ensuring that critical system state survives application restarts.
 */
public class BookMyShow {
    private static final String DATA_FILE = "system_state.ser";

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Welcome to BookMyShow Hotel System   ");
        System.out.println("=========================================");
        System.out.println("Use Case 12: Data Persistence & Recovery");
        System.out.println("=========================================\n");

        System.out.println("--- System Initialization ---");
        SystemState state = PersistenceService.loadState(DATA_FILE);

        if (state == null) {
            System.out.println("[Notice] No persisted state found. Initializing new system state.");
            state = new SystemState();
            state.initializeDefaultInventory();
        } else {
            System.out.println("[Success] System state recovered from " + DATA_FILE);
        }

        displaySystemStatus(state);

        // Process a new booking to show changes in state
        System.out.println("\n--- Processing New Booking ---");
        processNewBooking(state, "John Doe", "Suite");

        // Display status after booking
        displaySystemStatus(state);

        // Save state for next run
        System.out.println("\n--- System Shutdown ---");
        PersistenceService.saveState(state, DATA_FILE);
        System.out.println("System state saved successfully. Exiting...");
    }

    private static void displaySystemStatus(SystemState state) {
        System.out.println("\n--- Current System Status ---");
        System.out.println("Inventory Status:");
        state.getInventory().forEach((type, count) -> System.out.println(" - " + type + ": " + count + " available"));
        
        System.out.println("Booking History (Count: " + state.getBookingHistory().size() + "):");
        for (Reservation res : state.getBookingHistory()) {
            System.out.println(" - " + res);
        }
    }

    private static void processNewBooking(SystemState state, String guest, String type) {
        Map<String, Integer> inventory = state.getInventory();
        if (inventory.containsKey(type) && inventory.get(type) > 0) {
            inventory.put(type, inventory.get(type) - 1);
            Reservation res = new Reservation(guest, type);
            state.addBooking(res);
            System.out.println("[Success] Booking confirmed for " + guest + " (" + type + ")");
        } else {
            System.out.println("[Error] Room type '" + type + "' not available.");
        }
    }
}

/**
 * SystemState represents the data that needs to be persisted.
 */
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> inventory;
    private List<Reservation> bookingHistory;

    public SystemState() {
        this.inventory = new HashMap<>();
        this.bookingHistory = new ArrayList<>();
    }

    public void initializeDefaultInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public Map<String, Integer> getInventory() { return inventory; }
    public List<Reservation> getBookingHistory() { return bookingHistory; }
    public void addBooking(Reservation res) { bookingHistory.add(res); }
}

/**
 * Reservation record.
 */
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;
    private Date bookingDate;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.bookingDate = new Date();
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room: " + roomType + ", Date: " + bookingDate;
    }
}

/**
 * PersistenceService handles serialization and deserialization.
 */
class PersistenceService {
    public static void saveState(SystemState state, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(state);
            System.out.println("[Persistence] State successfully written to " + fileName);
        } catch (IOException e) {
            System.err.println("[Persistence Error] Failed to save state: " + e.getMessage());
        }
    }

    public static SystemState loadState(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SystemState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Persistence Error] Failed to load state (corrupted or incompatible): " + e.getMessage());
            return null;
        }
    }
}
