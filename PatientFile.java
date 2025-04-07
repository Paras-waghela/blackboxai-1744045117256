import java.io.Serializable;

public class PatientFile implements Serializable {
    private String name;
    private int age;
    private String gender;
    private String diagnosis;
    private String treatment;
    
    // Constructor
    public PatientFile(String name, int age, String gender, String diagnosis, String treatment) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    
    @Override
    public String toString() {
        return "Patient: " + name + " (" + age + "), " + gender + 
               "\nDiagnosis: " + diagnosis + 
               "\nTreatment: " + treatment;
    }
}