import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterPlayerName extends JFrame {
    private JButton STARTGAMEButton;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel menuPanel;
    private JLabel player1;
    private JLabel player2;
    private JLabel Title;

    public EnterPlayerName() {
        setTitle("Enter Player Name");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(menuPanel);
        setLocationRelativeTo(null);

        STARTGAMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String player1Name = textField1.getText().trim();
                String player2Name = textField2.getText().trim();

                if (player1Name.isEmpty()) {
                    player1Name = "Player 1";
                }
                if (player2Name.isEmpty()) {
                    player2Name = "Player 2";
                }

                new TicTacToeGUI(player1Name, player2Name).setVisible(true);
                dispose();
            }
        });

    }

    //public static void main(String[] args) {

   // }
}