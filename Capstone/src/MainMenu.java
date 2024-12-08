import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton STARTGAMEButton;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel menuPanel;
    private JLabel player1;
    private JLabel player2;
    private JLabel Title;

    public MainMenu() {
        setTitle("Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(menuPanel); // Attach the GUI form panel to the frame
        setLocationRelativeTo(null);

        // Add action listener for STARTGAMEButton
        STARTGAMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open TicTacToeGUI when button is clicked
                new TicTacToeGUI().setVisible(true);
                dispose(); // Optional: Close the main menu window
            }
        });
    }

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.setVisible(true);
    }
}
