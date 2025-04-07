import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class FileBrowserPanel extends JPanel {
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private JButton btnRefresh, btnDownload;
    private PatientFileManager remoteService;

    public FileBrowserPanel() {
        remoteService = RMIHelper.getRemoteService();
        if (remoteService == null) {
            return;
        }

        initComponents();
        setupLayout();
        setupListeners();
        refreshFileList();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setVisibleRowCount(10);
        fileList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        btnRefresh = new JButton("Refresh");
        btnDownload = new JButton("Download Selected");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(fileList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnDownload);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnRefresh.addActionListener(e -> refreshFileList());
        btnDownload.addActionListener(e -> downloadSelectedFile());
    }

    private void refreshFileList() {
        try {
            listModel.clear();
            List<String> files = remoteService.listFiles();
            for (String file : files) {
                listModel.addElement(file);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error retrieving file list: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void downloadSelectedFile() {
        String selectedFile = fileList.getSelectedValue();
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a file to download",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Patient File");
        fileChooser.setSelectedFile(new File(selectedFile));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Patient Files (*.dat)", "dat"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();
            try {
                byte[] fileData = remoteService.downloadFile(selectedFile);
                if (fileData != null) {
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(fileData);
                    }
                    JOptionPane.showMessageDialog(this,
                        "File downloaded successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to download file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error downloading file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}