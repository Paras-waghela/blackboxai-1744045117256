import java.rmi.Naming;
import javax.swing.JOptionPane;

public class RMIHelper {
    public static PatientFileManager getRemoteService() {
        try {
            return (PatientFileManager) Naming.lookup("rmi://localhost:1099/PatientService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Server connection failed: " + e.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}