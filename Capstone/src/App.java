import javax.swing.*;
import java.io.File;

public class App {
    public static void main(String[] args) {
        File file = new File("Two Gong Fire");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGUI().setVisible(true);
            }
        });

    }
}