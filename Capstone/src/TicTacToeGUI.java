import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private int xScore, oScore, moveCounter;
    private boolean isPlayerOne;
    private JLabel turnLabel, scoreLabel, resultLabel;
    private JButton[][] board;
    private JDialog resultDialog;
    private String player1Name, player2Name;

    private static final String HIGH_SCORE_DIR = "High Score";
    private static final String SCORE_FILE = HIGH_SCORE_DIR + File.separator + "scores.txt";

    public TicTacToeGUI(String player1Name, String player2Name) {
        super("TicTacToe");
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        new File(HIGH_SCORE_DIR).mkdirs();
        loadScores();
        createResultDialog();
        board = new JButton[3][3];

        addGuiComponent();
    }

    private void addGuiComponent() {
        JLabel barLabel = new JLabel();
        barLabel.setOpaque(true);
        barLabel.setBackground(CommonConstants.BAR_COLOR);
        barLabel.setBounds(0, 0, CommonConstants.FRAME_SIZE.width, 25);

        // Display current player's name
        turnLabel = new JLabel(isPlayerOne ? player1Name + "'s Turn" : player2Name + "'s Turn");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
        turnLabel.setPreferredSize(new Dimension(300, turnLabel.getPreferredSize().height));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(isPlayerOne ? CommonConstants.X_COLOR : CommonConstants.O_COLOR);
        turnLabel.setForeground(CommonConstants.BOARD_COLOR);
        turnLabel.setBounds(
                (CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width) / 2,
                0,
                turnLabel.getPreferredSize().width,
                turnLabel.getPreferredSize().height
        );

        scoreLabel = new JLabel(player1Name + " - " + xScore + " | " + player2Name + " - " + oScore);
        scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(CommonConstants.BOARD_COLOR);
        scoreLabel.setBounds(0,
                turnLabel.getY() + turnLabel.getPreferredSize().height + 25,
                CommonConstants.FRAME_SIZE.width,
                scoreLabel.getPreferredSize().height
        );

        GridLayout gridLayout = new GridLayout(3, 3);
        JPanel boardPanel = new JPanel(gridLayout);
        boardPanel.setBounds(0,
                scoreLabel.getY() + scoreLabel.getPreferredSize().height + 35,
                CommonConstants.BOARD_SIZE.width,
                CommonConstants.BOARD_SIZE.height
        );

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Dialog", Font.PLAIN, 180));
                button.setPreferredSize(CommonConstants.BUTTON_SIZE);
                button.setBackground(CommonConstants.BACKGROUND_COLOR);
                button.addActionListener(this);
                button.setBorder(BorderFactory.createLineBorder(CommonConstants.BOARD_COLOR));

                // add button to board
                board[i][j] = button;
                boardPanel.add(board[i][j]);
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Dialog", Font.PLAIN, 24));
        resetButton.addActionListener(this);
        resetButton.setBackground(CommonConstants.BOARD_COLOR);
        resetButton.setBounds(
                (CommonConstants.FRAME_SIZE.width - resetButton.getPreferredSize().width) / 2,
                CommonConstants.FRAME_SIZE.height - 100,
                resetButton.getPreferredSize().width,
                resetButton.getPreferredSize().height
        );

        getContentPane().add(turnLabel);
        getContentPane().add(barLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(boardPanel);
        getContentPane().add(resetButton);
    }

    private void createResultDialog() {
        resultDialog = new JDialog();
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        resultLabel.setForeground(CommonConstants.BOARD_COLOR);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Play Again");
        restartButton.setBackground(CommonConstants.BOARD_COLOR);
        restartButton.addActionListener(this);

        JButton exitButton = new JButton("Exit Game");
        exitButton.setBackground(CommonConstants.BOARD_COLOR);
        exitButton.addActionListener(e -> System.exit(0));

        resultDialog.add(resultLabel);
        resultDialog.add(restartButton);
        resultDialog.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Reset") || command.equals("Play Again")) {
            resetGame();

            if (command.equals("Reset")) {
                xScore = oScore = 0;
            }

            if (command.equals("Play Again")) {
                resultDialog.setVisible(false);
            }
        } else {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")) {
                moveCounter++;

                if (isPlayerOne) {
                    button.setText("X");
                    button.setForeground(CommonConstants.X_COLOR);
                    turnLabel.setText(player2Name + "'s turn");
                    isPlayerOne = false;
                } else {
                    button.setText("O");
                    button.setForeground(CommonConstants.O_COLOR);
                    turnLabel.setText(player1Name + "'s turn");
                    isPlayerOne = true;
                }

                checkWin();
                checkDraw();
                scoreLabel.setText(player1Name + " - " + xScore + " | " + player2Name + " - " + oScore);
            }

            repaint();
            revalidate();
        }
    }

    private void checkWin() {
        // Check for wins for both players
        if (checkPlayerWin(player1Name, CommonConstants.X_COLOR)) {
            resultLabel.setText(player1Name + " wins!");
            resultDialog.setVisible(true);
            xScore++;
            saveScores(player1Name, player2Name, xScore, oScore);
        } else if (checkPlayerWin(player2Name, CommonConstants.O_COLOR)) {
            resultLabel.setText(player2Name + " wins!");
            resultDialog.setVisible(true);
            oScore++;
            saveScores(player1Name, player2Name, xScore, oScore);
        }
    }


    private boolean checkPlayerWin(String playerName, Color playerColor) {
        String playerSymbol = playerName.equals(player1Name) ? "X" : "O";


        for (int row = 0; row < board.length; row++) {
            if (board[row][0].getText().equals(playerSymbol) && board[row][1].getText().equals(playerSymbol) && board[row][2].getText().equals(playerSymbol)) {
                return true;
            }
        }

        for (int col = 0; col < board.length; col++) {
            if (board[0][col].getText().equals(playerSymbol) && board[1][col].getText().equals(playerSymbol) && board[2][col].getText().equals(playerSymbol)) {
                return true;
            }
        }

        if (board[0][0].getText().equals(playerSymbol) && board[1][1].getText().equals(playerSymbol) && board[2][2].getText().equals(playerSymbol)) {
            return true;
        }

        if (board[2][0].getText().equals(playerSymbol) && board[1][1].getText().equals(playerSymbol) && board[0][2].getText().equals(playerSymbol)) {
            return true;
        }

        return false;
    }

    private void checkDraw() {
        if (moveCounter >= 9) {
            resultLabel.setText("Draw!");
            resultDialog.setVisible(true);
            saveScores(player1Name, player2Name, xScore, oScore);
        }
    }

    private void resetGame() {
        isPlayerOne = new Random().nextBoolean();
        turnLabel.setText(isPlayerOne ? player1Name + "'s turn" : player2Name + "'s turn");
        turnLabel.setBackground(isPlayerOne ? CommonConstants.X_COLOR : CommonConstants.O_COLOR);
        scoreLabel.setText(player1Name + " - " + xScore + " | " + player2Name + " - " + oScore);
        moveCounter = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setText("");
            }
        }
    }

    private void saveScores(String player1Name, String player2Name, int xScore, int oScore) {
        // Create the file directory if it does not exis
        File file = new File(HIGH_SCORE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(player1Name + " vs " + player2Name + ": ");
            if (xScore > oScore) {
                writer.write(player1Name + " wins");
            } else if (oScore > xScore) {
                writer.write(player2Name + " wins");
            } else {
                writer.write("Draw");
            }
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving scores: " + e.getMessage());
        }
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error loading scores: " + e.getMessage());
        }
    }
}