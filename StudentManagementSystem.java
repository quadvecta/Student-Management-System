import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Student class to represent a student
class Student {
    private String name;
    private int id;
    private String grade;

    public Student(String name, int id, String grade) {
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Grade: " + grade;
    }
}

// Main GUI class
public class StudentManagementSystem extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextArea displayArea;

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10)); // Add padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margins

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Placeholder text fields
        JTextField nameField = createTextField("Enter Name");
        JTextField idField = createTextField("Enter ID");
        JTextField gradeField = createTextField("Enter Grade");

        // Add components to the input panel
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(Box.createVerticalStrut(10)); // Add spacing
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(Box.createVerticalStrut(10)); // Add spacing
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);

        // Buttons
        JButton addButton = createButton("Add Student");
        JButton viewButton = createButton("View Students");
        JButton deleteButton = createButton("Delete Student");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Add spacing
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Modern font
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(450, 150)); // Set preferred size

        // Add panels to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String idText = idField.getText();
                String grade = gradeField.getText();

                if (name.isEmpty() || idText.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idText);
                    students.add(new Student(name, id, grade));
                    displayArea.append("Added: " + students.get(students.size() - 1) + "\n");

                    nameField.setText("");
                    gradeField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("");
                if (students.isEmpty()) {
                    displayArea.append("No students added yet.\n");
                } else {
                    for (Student student : students) {
                        displayArea.append(student.toString() + "\n");
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idField.getText();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter an ID to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idToDelete = Integer.parseInt(idText);
                    boolean removed = students.removeIf(student -> student.getId() == idToDelete);
                    if (removed) {
                        displayArea.append("Deleted student with ID: " + idToDelete + "\n");
                    } else {
                        JOptionPane.showMessageDialog(null, "Student with ID " + idToDelete + " not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Helper method to create a styled button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(70, 130, 180)); // SteelBlue color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
        return button;
    }

    // Helper method to create a placeholder text field
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // Light gray border
                BorderFactory.createEmptyBorder(5, 10, 5, 10))); // Add padding
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementSystem().setVisible(true);
            }
        });
    }
}
