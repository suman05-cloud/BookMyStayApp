import java.util.HashMap;
import java.util.Map;

/**
 * ===========================================================================
 * CLASS - RoomInventory
 * ===========================================================================
 * Use Case 3: Centralized Room Inventory Management
 * 
 * Description:
 * This class acts as the single source of truth
 * for room availability in the hotel.
 * 
 * Room pricing and characteristics are obtained
 * from Room objects, not duplicated here.
 * 
 * This avoids multiple sources of truth and
 * keeps responsibilities clearly separated.
 * 
 * @version 3.1
 */
public class RoomInventory {

    /**
     * Stores available room count for each room type.
     * 
     * Key -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory
     * with default availability values.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     * 
     * This method centralizes inventory setup
     * instead of using scattered variables.
     */
    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    /**
     * Returns the current availability for a specific room type.
     * 
     * @param roomType The type of room
     * @return Number of rooms available
     */
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    /**
     * Updates the availability for a specific room type.
     * 
     * @param roomType The type of room
     * @param count The new availability count
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    /**
     * Reduces availability by 1 when a room is booked.
     * 
     * @param roomType The type of room
     */
    public void bookRoom(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            updateAvailability(roomType, current - 1);
        }
    }
}
