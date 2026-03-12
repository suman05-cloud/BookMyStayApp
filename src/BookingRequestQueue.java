import java.util.LinkedList;
import java.util.Queue;

/**
 * ===========================================================================
 * CLASS - BookingRequestQueue
 * ===========================================================================
 * Use Case 5: Booking Request (FIFO)
 * 
 * Description:
 * This class handles the intake of booking requests
 * using a FIFO (First-In-First-Out) queue.
 * 
 * @version 5.0
 */
public class BookingRequestQueue {

    /** Queue to store booking requests in arrival order. */
    private Queue<Reservation> requestQueue;

    /**
     * Initializes the booking request queue.
     */
    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a new booking request to the queue.
     * 
     * @param request The reservation request to add
     */
    public void addRequest(Reservation request) {
        requestQueue.add(request);
        System.out.println("Added request for: " + request.getGuestName() + " (" + request.getRoomType() + ")");
    }

    /**
     * Removes and returns the next request from the queue.
     * 
     * @return The next Reservation request
     */
    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    /**
     * Checks if there are pending requests.
     * 
     * @return true if queue is not empty
     */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
