import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{
    private JButton STARTGAMEButton;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel menuPanel;
    private JLabel player1;
    private JLabel player2;
    private JLabel Title;

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.setContentPane(menu.menuPanel);
        menu.setSize(CommonConstants.FRAME_SIZE);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void menuComponents(){

        Title.setFont(new Font("Dialog", Font.PLAIN, 90));
    }

}
