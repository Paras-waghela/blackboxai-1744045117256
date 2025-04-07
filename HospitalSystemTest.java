public class HospitalSystemTest {
    public static void main(String[] args) {
        // Start server in a separate thread
        new Thread(() -> {
            try {
                Server.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Start client GUI
        MainForm.main(args);
    }
}