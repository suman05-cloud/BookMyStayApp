/**
 * ===========================================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ===========================================================================
 * Use Case 5: Booking Request (FIFO)
 * 
 * Description:
 * This class demonstrates the intake and 
 * ordering of booking requests.
 * 
 * No room allocation or inventory 
 * update is performed here.
 * 
 * @version 5.0
 */
public class UseCase5BookingRequestQueue {

    /**
     * Application entry point.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Display application header
        System.out.println("Booking Request Queue\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        System.out.println("\nProcessing queued booking requests in FIFO order:");

        // Display queued booking requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.processNextRequest();
            System.out.println("Processing: Guest: " + request.getGuestName() + ", Room Type: " + request.getRoomType());
        }
    }
}
