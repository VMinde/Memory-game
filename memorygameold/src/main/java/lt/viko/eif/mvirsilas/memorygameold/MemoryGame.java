package lt.viko.eif.mvirsilas.memorygameold;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame {

    private JFrame frame;
    private JButton[][] buttons = new JButton[4][4]; // 6x6 buttons
    private int[][] board = new int[4][4];
    private JButton firstButton = null, secondButton = null;
    private int firstRow, firstCol, secondRow, secondCol;
    private JButton restartButton;

    private JLabel timerLaber;
    private Timer timer;
    private int elapsedTime = 0;

    public MemoryGame() {
        //Frame settings
        frame = new JFrame("Memory game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        timerLaber = new JLabel("Time: 0 s", SwingConstants.CENTER);
        timerLaber.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(timerLaber, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4)); // 6x6 table

        generateBoard();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                buttons[i][j].setBackground(Color.LIGHT_GRAY);
                buttons[i][j].setForeground(Color.BLUE);
                buttons[i][j].setFocusPainted(false);
                final int row = i, col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                panel.add(buttons[i][j]);
            }
        }
        frame.add(panel, BorderLayout.CENTER);

        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLaber.setText("Time: " + elapsedTime + " sec");
        });
        timer.start();

        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setBackground(new Color(224, 40, 40));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(e -> restartGame());
        frame.add(restartButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void generateBoard() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <=7; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = numbers.get(index);
                index++;
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        if (buttons[row][col].getText().equals("")) {
            buttons[row][col].setText(String.valueOf(board[row][col]));
            buttons[row][col].setBackground(Color.WHITE);

            if (firstButton == null) {
                firstButton = buttons[row][col];
                firstRow = row;
                firstCol = col;
            } else {
                secondButton = buttons[row][col];
                secondRow = row;
                secondCol = col;

                setButtonsEnabled(false);

                if (board[firstRow][firstCol] == board[secondRow][secondCol]) {
                    firstButton = null;
                    secondButton = null;

                    setButtonsEnabled(true);

                    if (isGameWon()){
                        timer.stop();
                        JOptionPane.showMessageDialog(frame, "You won, you time: " + elapsedTime + " sec");

                    }

                } else {
                    Timer timer = new Timer(1000, e -> {
                        firstButton.setText("");
                        secondButton.setText("");
                        firstButton.setBackground(Color.LIGHT_GRAY);
                        secondButton.setBackground(Color.LIGHT_GRAY);
                        firstButton = null;
                        secondButton = null;
                        setButtonsEnabled(true);
                    });
                    timer.setRepeats(false);
                    timer.start();
                }


            }
        }
    }

    private void restartGame(){

        elapsedTime = 0;
        timerLaber.setText("Time: 0 sec");
        timer.restart();

        generateBoard();

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                buttons[i][j].setText("");
            }
        }
        firstButton = null;
        secondButton = null;
    }

    private boolean isGameWon(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j <4; j++){
                if(buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setButtonsEnabled(boolean enabled){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                buttons[i][j].setEnabled(enabled);
            }
        }
    }

    public static void main(String[] args) {

        new MemoryGame();
    }
}