import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainForm extends JFrame {
    private JPanel dashboard;
    private CardLayout cardLayout;
    private JButton btnCreate, btnView, btnDownload;

    public MainForm() {
        setTitle("Hospital File Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {}

        // Initialize components
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        dashboard = new JPanel(cardLayout);
        
        // Create styled buttons
        btnCreate = createStyledButton("Create Patient File");
        btnView = createStyledButton("View Patient Files");
        btnDownload = createStyledButton("Download Patient File");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void setupLayout() {
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(btnCreate);
        buttonPanel.add(btnView);
        buttonPanel.add(btnDownload);

        // Add components to frame
        add(buttonPanel, BorderLayout.NORTH);
        add(dashboard, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnCreate.addActionListener(e -> {
            CreatePatientDialog dialog = new CreatePatientDialog(this);
            dialog.setVisible(true);
        });

        btnView.addActionListener(e -> {
            FileBrowserPanel fileBrowser = new FileBrowserPanel();
            dashboard.add(fileBrowser, "fileBrowser");
            cardLayout.show(dashboard, "fileBrowser");
        });

        btnDownload.addActionListener(e -> {
            FileBrowserPanel fileBrowser = new FileBrowserPanel();
            dashboard.add(fileBrowser, "fileBrowser");
            cardLayout.show(dashboard, "fileBrowser");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm form = new MainForm();
            form.setVisible(true);
        });
    }
}