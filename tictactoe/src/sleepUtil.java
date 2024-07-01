

public class sleepUtil {

    public interface Sleeper {
        void run() throws InterruptedException;
    }

    // Utility method to sleep without handling exceptions directly
    public static void sleep(long millis) {
        safely(() -> Thread.sleep(millis));
    }

    // General utility method to handle the InterruptedException
    public static void safely(Sleeper sleeper) {
        try {
            sleeper.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupted status
            // You can handle the exception here or rethrow it as unchecked exception
            throw new RuntimeException(e);
        }
    }

}
