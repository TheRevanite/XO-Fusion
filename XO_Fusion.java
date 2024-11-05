import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class XO_Fusion extends JFrame {
    private String[] board;
    private String turn;
    private JButton[] buttons;
    private JLabel statusLabel;
    private LinkedList<Integer> xMoves;
    private LinkedList<Integer> oMoves;
    private final Color backgroundColor = new Color(28, 28, 40);
    private final Color neonBlue = new Color(0, 204, 255);
    private final Color neonPink = new Color(255, 20, 147);
    private final Color textColor = Color.WHITE;

    public XO_Fusion() {
        showHomeScreen();
    }

    private void showHomeScreen() {
        getContentPane().removeAll();
        setTitle("XO Fusion");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(backgroundColor);
        JLabel titleLabel = new JLabel("XO Fusion");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 48));
        titleLabel.setForeground(neonBlue);
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(backgroundColor);

        JButton playButton = createStyledButton("Play Game (Multiplayer)");
        JButton cpuButton = createStyledButton("Play Game (Player vs CPU)");
        JButton instructionsButton = createStyledButton("Instructions");
        JButton exitButton = createStyledButton("Exit");

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(cpuButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(instructionsButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exitButton);

        playButton.addActionListener(e -> initializeGame(false));
        cpuButton.addActionListener(e -> initializeGame(true));
        instructionsButton.addActionListener(e -> showInstructions());
        exitButton.addActionListener(e -> System.exit(0));

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(neonPink);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(400, 100));
        button.setPreferredSize(new Dimension(200, 50));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(neonBlue);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(neonPink);
            }
        });

        return button;
    }

    private void showInstructions() {
        JOptionPane.showMessageDialog(this, """
                How to Play:

                1. Each player can only have 3 moves on the board.
                2. When placing a 4th move, your oldest move will be removed.
                3. Get three in a row to win!
                4. X goes first.""",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void makeCpuMove() {
        int move;
        do {
            move = (int) (Math.random() * 9);
        } while (board[move].equals("X") || board[move].equals("O"));

        buttons[move].doClick();
    }

    private void initializeGame(boolean isCpuGame) {
        getContentPane().removeAll();
        board = new String[9];
        turn = "X";
        buttons = new JButton[9];
        xMoves = new LinkedList<>();
        oMoves = new LinkedList<>();

        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }

        setLayout(new BorderLayout());

        JLabel gameTitle = new JLabel("XO Fusion", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        gameTitle.setForeground(neonBlue);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(backgroundColor);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
            buttons[i].setBackground(backgroundColor);
            buttons[i].setForeground(neonBlue);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(neonPink, 2));
            buttons[i].addActionListener(new ButtonClickListener(i, isCpuGame));
            boardPanel.add(buttons[i]);
        }

        statusLabel = new JLabel("X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        statusLabel.setForeground(textColor);

        JButton homeButton = createStyledButton("Home");
        homeButton.addActionListener(e -> showHomeScreen());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.add(gameTitle, BorderLayout.CENTER);
        topPanel.add(homeButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private class ButtonClickListener implements ActionListener {
        private final int index;
        private final boolean isCpuGame;

        public ButtonClickListener(int index, boolean isCpuGame) {
            this.index = index;
            this.isCpuGame = isCpuGame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!board[index].equals("X") && !board[index].equals("O")) {
                LinkedList<Integer> currentMoves = turn.equals("X") ? xMoves : oMoves;

                if (currentMoves.size() == 3) {
                    int oldestMove = currentMoves.peek();
                    highlightOldestMove(oldestMove);

                    Timer timer = new Timer(500, evt -> {
                        currentMoves.poll();
                        board[oldestMove] = String.valueOf(oldestMove + 1);
                        buttons[oldestMove].setText("");
                        buttons[oldestMove].setEnabled(true);

                        placeMove();
                        resetHighlight(oldestMove);
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    placeMove();
                }
            }
        }

        private void placeMove() {
            board[index] = turn;
            buttons[index].setText(turn);
            buttons[index].setForeground(turn.equals("X") ? neonBlue : neonPink);
            buttons[index].setEnabled(false);
            LinkedList<Integer> currentMoves = turn.equals("X") ? xMoves : oMoves;
            currentMoves.add(index);

            String winner = checkWinner();
            if (winner != null) {
                if (winner.equals("draw")) {
                    statusLabel.setText("It's a draw!");
                } else {
                    statusLabel.setText("Congratulations! " + winner + " has won!");
                }
                disableButtons();
            } else {
                turn = turn.equals("X") ? "O" : "X";
                statusLabel.setText(turn + "'s turn");
                if (isCpuGame && turn.equals("O")) {
                    makeCpuMove();
                }
            }
        }

        private void highlightOldestMove(int oldestMove) {
            buttons[oldestMove].setBorder(BorderFactory.createLineBorder(Color.PINK, 5));
            buttons[oldestMove].setBackground(Color.GRAY);
        }

        private void resetHighlight(int oldestMove) {
            buttons[oldestMove].setBorder(BorderFactory.createLineBorder(neonPink, 2));
            buttons[oldestMove].setBackground(backgroundColor);
        }
    }

    private String checkWinner() {
        int[][] winningCombos = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 }
        };

        for (int[] combo : winningCombos) {
            if (board[combo[0]].equals(board[combo[1]]) && board[combo[1]].equals(board[combo[2]])
                    && !board[combo[0]].equals(String.valueOf(combo[0] + 1))) {
                return board[combo[0]];
            }
        }

        for (String s : board) {
            if (!s.equals("X") && !s.equals("O"))
                return null;
        }

        return "draw";
    }

    private void disableButtons() {
        for (JButton button : buttons)
            button.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XO_Fusion game = new XO_Fusion();
            game.setVisible(true);
        });
    }
}
