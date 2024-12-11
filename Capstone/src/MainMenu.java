import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton STARTGAMEButton;
    private JTextField textField1; // Field for player 1's name
    private JTextField textField2; // Field for player 2's name
    private JPanel menuPanel;
    private JLabel player1;
    private JLabel player2;
    private JLabel Title;

    public MainMenu() {
        setTitle("Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(menuPanel);
        setLocationRelativeTo(null);

        STARTGAMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String player1Name = textField1.getText(); // Get player 1's name
                String player2Name = textField2.getText(); // Get player 2's name
                new TicTacToeGUI(player1Name, player2Name).setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.setVisible(true);
    }
}