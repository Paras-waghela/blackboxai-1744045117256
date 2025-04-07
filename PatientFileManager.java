import java.rmi.*;
import java.util.List;

public interface PatientFileManager extends Remote {
    boolean createFile(PatientFile file) throws RemoteException;
    List<String> listFiles() throws RemoteException;
    byte[] downloadFile(String filename) throws RemoteException;
}