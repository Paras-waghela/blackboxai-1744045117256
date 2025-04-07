import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreatePatientDialog extends JDialog {
    private JTextField txtName, txtAge;
    private JComboBox<String> cmbGender;
    private JTextArea txtDiagnosis, txtTreatment;
    private JButton btnSave, btnCancel;
    private PatientFileManager remoteService;

    public CreatePatientDialog(JFrame owner) {
        super(owner, "Create New Patient File", true);
        setSize(500, 400);
        setLocationRelativeTo(owner);
        
        remoteService = RMIHelper.getRemoteService();
        if (remoteService == null) {
            dispose();
            return;
        }

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        txtName = new JTextField(20);
        txtAge = new JTextField(3);
        
        String[] genders = {"Male", "Female", "Other"};
        cmbGender = new JComboBox<>(genders);
        
        txtDiagnosis = new JTextArea(5, 30);
        txtDiagnosis.setLineWrap(true);
        txtDiagnosis.setWrapStyleWord(true);
        
        txtTreatment = new JTextArea(5, 30);
        txtTreatment.setLineWrap(true);
        txtTreatment.setWrapStyleWord(true);
        
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    private void setupLayout() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        // Age and Gender
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtAge, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbGender, gbc);

        // Diagnosis
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Diagnosis:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(txtDiagnosis), gbc);

        // Treatment
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Treatment:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(txtTreatment), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnSave.addActionListener(e -> savePatient());
        btnCancel.addActionListener(e -> dispose());
    }

    private void savePatient() {
        try {
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            String gender = (String) cmbGender.getSelectedItem();
            String diagnosis = txtDiagnosis.getText().trim();
            String treatment = txtTreatment.getText().trim();

            if (name.isEmpty() || diagnosis.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Name and Diagnosis are required fields", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            PatientFile patient = new PatientFile(name, age, gender, diagnosis, treatment);
            boolean success = remoteService.createFile(patient);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Patient file created successfully", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to create patient file", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid age", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}