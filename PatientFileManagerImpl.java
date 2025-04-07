import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.nio.file.*;

public class PatientFileManagerImpl extends UnicastRemoteObject implements PatientFileManager {
    private static final String STORAGE_DIR = "patient_files/";
    private static final long serialVersionUID = 1L;

    public PatientFileManagerImpl() throws RemoteException {
        super();
        // Create storage directory if it doesn't exist
        new File(STORAGE_DIR).mkdirs();
    }

    @Override
    public boolean createFile(PatientFile file) throws RemoteException {
        try {
            String filename = STORAGE_DIR + file.getName() + "_" + System.currentTimeMillis() + ".dat";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(file);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error creating patient file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> listFiles() throws RemoteException {
        List<String> files = new ArrayList<>();
        File folder = new File(STORAGE_DIR);
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }

    @Override
    public byte[] downloadFile(String filename) throws RemoteException {
        try {
            Path path = Paths.get(STORAGE_DIR + filename);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Error downloading file: " + e.getMessage());
            return null;
        }
    }
}