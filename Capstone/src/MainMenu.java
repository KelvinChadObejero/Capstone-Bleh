import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.*;

public class MainMenu extends JFrame {
    private JButton playGameButton;
    private JButton viewHistoryButton;
    private JPanel menuPanel;
    private JLabel titleLabel;
    private JLabel player1;
    private JLabel player2;

    public MainMenu() {
        setTitle("Tic Tac Toe");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        titleLabel = new JLabel("TICTACTOE");
        titleLabel.setFont(new Font("Eras Bold ITC", Font.BOLD, 50));
        titleLabel.setForeground(new Color(255,159,28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);


        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(2, 1));
        menuPanel.setBackground(new Color(1, 22, 39));
        add(menuPanel, BorderLayout.CENTER);


        playGameButton = new JButton("Play Game");
        playGameButton.setFont(new Font("Arial", Font.BOLD, 24));
        playGameButton.setBackground(new Color(43,45,48));
        playGameButton.setForeground(Color.WHITE);
        playGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EnterPlayerName enterPlayerName = new EnterPlayerName();
                enterPlayerName.setVisible(true);
            }
        });
        playGameButton.setPreferredSize(new Dimension(100, 50));
        menuPanel.add(playGameButton);


        viewHistoryButton = new JButton("Game History");
        viewHistoryButton.setFont(new Font("Arial", Font.BOLD, 24));
        viewHistoryButton.setBackground(new Color(43,45,48));
        viewHistoryButton.setForeground(Color.WHITE);
        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewHistory viewHistory = new ViewHistory();
                viewHistory.setVisible(true);
            }
        });
        viewHistoryButton.setPreferredSize(new Dimension(100, 50)); // Set preferred size
        menuPanel.add(viewHistoryButton);


        menuPanel.setBorder(BorderFactory.createEmptyBorder(300, 100, 300, 100));

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
}

class ViewHistory extends JFrame {
    private JTextArea historyTextArea;
    private JPanel historyPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private static final String HIGH_SCORE_DIR = "High Score";
    private static final String SCORE_FILE = HIGH_SCORE_DIR + File.separator + "scores.txt";

    public ViewHistory() {
        setTitle("Game History");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        titleLabel = new JLabel("Game History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);


        historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBackground(new Color(1, 22, 39));
        add(historyPanel, BorderLayout.CENTER);


        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        historyTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        historyPanel.add(new JScrollPane(historyTextArea), BorderLayout.CENTER);


        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);


        File file = new File(HIGH_SCORE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historyTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            historyTextArea.append("No game history found.");
        }


        historyPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        setLocationRelativeTo(null);
    }
}