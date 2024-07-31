import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PollingSystem extends JFrame {
    private HashMap<String, Integer> voteCounts;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createPollButton;
    private JButton voteButton;
    private JButton viewResultsButton;
    private JLabel statusLabel;

    public PollingSystem() {
        voteCounts = new HashMap<>();

        setTitle("Online Polling System");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2,5,5));

        // User authentication components
        add(new JLabel("Username: "));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password: "));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
        add(loginButton);

        // Poll creation, voting, and viewing components
        createPollButton = new JButton("Create Poll");
        createPollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPoll();
            }
        });

        voteButton = new JButton("Vote");
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vote();
            }
        });

        viewResultsButton = new JButton("View Results");
        viewResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewResults();
            }
        });

        // Initially disable poll-related buttons
        createPollButton.setEnabled(false);
        voteButton.setEnabled(false);
        viewResultsButton.setEnabled(false);

        add(createPollButton);
        add(voteButton);
        add(viewResultsButton);

        statusLabel = new JLabel();
        add(statusLabel);
        
        setLocationRelativeTo(null);
	setVisible(true);
    }

    private void authenticateUser() {
        // Check if the entered username and password are valid
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (isValidUser(username, password)) {
            statusLabel.setText("Login Successful");
            createPollButton.setEnabled(true);
            voteButton.setEnabled(true);
            viewResultsButton.setEnabled(true);
        } else {
            statusLabel.setText("Invalid username or password");
        }
    }

    private boolean isValidUser(String username, String password) {
        
        //we will a single user with username "admin" and password "password"
        return username.equals("admin") && password.equals("password");
		
    }

    private void createPoll() {
        String question = JOptionPane.showInputDialog("Enter 1st Option:");
        if (question != null && !question.trim().isEmpty()) {
            String[] options = JOptionPane.showInputDialog("Enter 2nd Option:").split(",");
            voteCounts.put(question, 0);
            for (String option : options) {
                voteCounts.put(option.trim(), 0);
            }
            statusLabel.setText("Poll created successfully");
        } else {
            statusLabel.setText("Poll creation failed");
        }
    }

    private void vote() {
        String[] options = voteCounts.keySet().toArray(new String[0]);
        String selectedOption = (String) JOptionPane.showInputDialog(null, "Select an option:", "Vote",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selectedOption != null) {
            int currentCount = voteCounts.get(selectedOption);
            voteCounts.put(selectedOption, currentCount + 1);
            statusLabel.setText("Vote recorded successfully");
        } else {
            statusLabel.setText("Vote failed");
        }
    }

    private void viewResults() {
        StringBuilder results = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : voteCounts.entrySet()) {
            results.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(null, results.toString(), "Poll Results", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new PollingSystem();

    }
}
