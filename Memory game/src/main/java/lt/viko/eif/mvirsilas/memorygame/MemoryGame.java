package lt.viko.eif.mvirsilas.memorygame;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MemoryGame {

    private JFrame frame;
    private JButton[][] buttons = new JButton[6][6]; // 6x6 buttons
    private int[][] board = new int[6][6];
    private JButton firstButton = null, secondButton = null;
    private int firstRow, firstCol, secondRow, secondCol;

    public MemoryGame() {

        frame = new JFrame("Memory game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 6)); // 6x6 table

        generateBoard();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                buttons[i][j] = new JButton("");
                final int row = i, col = j;

                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));

                panel.add(buttons[i][j]);
            }
        }
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void generateBoard() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 17; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = numbers.get(index);
                index++;
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        if (buttons[row][col].getText().equals("")) {
            buttons[row][col].setText(String.valueOf(board[row][col]));

            if (firstButton == null) {
                firstButton = buttons[row][col];
                firstRow = row;
                firstCol = col;
            } else {
                secondButton = buttons[row][col];
                secondRow = row;
                secondCol = col;

                if (board[firstRow][firstCol] == board[secondRow][secondCol]) {
                    firstButton = null;
                    secondButton = null;
                } else {
                    Timer timer = new Timer(1000, e -> {
                        firstButton.setText("");
                        secondButton.setText("");
                        firstButton = null;
                        secondButton = null;
                    });
                    timer.setRepeats(false);
                    timer.start();
                }


            }
        }
    }


    public static void main(String[] args) {

        new MemoryGame();
    }
}