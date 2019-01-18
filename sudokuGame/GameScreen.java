package sudokuGame;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;

public class GameScreen extends JFrame {

    JFrame frame;
    private final JPanel panelBoard;
    private final JPanel mainBoard;
    private final JPanel decorTop;
    private final JPanel decorLeft;
    private final JPanel decorRight;
    private final JPanel decorBottom;
    private final JPanel panelKeys;
    private final JPanel panelButton;
    private final JButton undobtn;
    private final JButton reset;
    private final JButton tutorial;
    private final JButton quit;
    private final JButton[] input;
    Document[] doc = new Document[121];
    JTextField[][] board = new JTextField[9][9];
    NumberFormat customFormat = NumberFormat.getIntegerInstance();
    UndoManager undo = new UndoManager();
    JTextField temp = new JTextField();

    void validateGame() {
        boolean done = true;
        boolean err = false;
        int[][] boardVals = new int[9][9];
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (!(board[i][j].getText().isEmpty())) {
                        boardVals[i][j] = Integer.parseInt(board[i][j].getText());
                    } else {
                        boardVals[i][j] = 0;
                        done = false;
                    }

                }
            }
        } catch (NumberFormatException n) {
        }
        int[] row = new int[9];
        int[] cellBlock = new int[9];
        int[] column = new int[9];

        for (int x = 0; x < 9; x++) {
            for (int j = 0; j < 9; j++) {
                column[j] = boardVals[j][x];
                int iTemp = (x / 3) * 3 + j / 3;
                int jTemp = x * 3 % 9 + j % 3;
                cellBlock[j] = boardVals[iTemp][jTemp];
            }
            row = boardVals[x].clone();
            int[] rowTemp = row.clone();
            int[] cellBlockTemp = cellBlock.clone();
            int[] columnTemp = column.clone();

            Arrays.sort(column);
            Arrays.sort(cellBlock);
            Arrays.sort(row);

            for (int i = 0; i < 8; i++) {
                int n = i + 1;

                if (column[i] == column[n] && column[i] > 0) {
                    for (int a = 0; a < 9; a++) {
                        if (columnTemp[a] == column[i]) {
                            board[a][x].setForeground(Color.red);
                            err = true;
                        }
                    }
                }
                if (row[i] == row[n] && row[i] > 0) {
                    for (int a = 0; a < 9; a++) {
                        if (rowTemp[a] == row[i]) {
                            board[x][a].setForeground(Color.red);
                            err = true;
                        }
                    }
                }
                if (cellBlock[i] == cellBlock[n] && cellBlock[i] > 0) {
                    for (int a = 0; a < 9; a++) {
                        int iTemp = (x / 3) * 3 + a / 3;
                        int jTemp = x * 3 % 9 + a % 3;
                        if (cellBlockTemp[a] == cellBlock[i]) {
                            board[iTemp][jTemp].setForeground(Color.red);
                            err = true;
                        }
                    }
                }
            }
        }
        if (done == true) {
            if (err = false) {
                if (JOptionPane.showConfirmDialog(null, "Game Over!\n Do you want to play again?", "Game Over",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new GameScreen();
                    frame.dispose();

                } else {
                    new MainScreen();
                }
            } else {
                if (JOptionPane.showConfirmDialog(null, "Invalid Solution!\n Do you want to keep playing?", "Game Over",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                } else {
                    new MainScreen();
                    frame.dispose();
                }

            }
        }
    }

    DocumentListener mylistener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            try {
                JFormattedTextField f = (JFormattedTextField) e.getDocument().getProperty("owner");
                try {

                    int x = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));

                    if (x < 1 || x > 9) {

                        f.setBackground(Color.red);
                        JOptionPane.showMessageDialog(frame, "values 1-9 only", "Invalid Input", WARNING_MESSAGE);
                        f.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                f.setText("");
                                //f.setBackground(Color.white);

                            }
                        });
                    } else {

                        f.setBackground(Color.white);
                        f.setForeground(Color.black);
                        validateGame();
                    }
                } catch (NumberFormatException n) {
                    f.setText("");
                    JOptionPane.showMessageDialog(frame, "Input values 1-9 only");

                }

            } catch (BadLocationException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

    };

    public GameScreen() {

        frame = new JFrame("Sudoku");
        input = new JButton[9];
        panelBoard = new JPanel();
        mainBoard = new JPanel();
        decorBottom = new JPanel();
        decorRight = new JPanel();
        decorTop = new JPanel();
        decorLeft = new JPanel();
        panelKeys = new JPanel();
        panelButton = new JPanel();
        undobtn = new JButton("Undo");
        reset = new JButton("Reset");
        tutorial = new JButton("How to Play");
        quit = new JButton("Quit");

        customFormat.setMinimumIntegerDigits(1);
        customFormat.setMaximumIntegerDigits(1);

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout) contentPane.getLayout()).columnWidths = new int[]{104, 700, 300, 0};
        ((GridBagLayout) contentPane.getLayout()).rowHeights = new int[]{300, 300, 150, 100};

        panelBoard.setLayout(new GridLayout(9, 9));
        panelBoard.setBackground(Color.white);

        buildBoard();

        contentPane.add(panelBoard, new GridBagConstraints(1, 0, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 100), 0, 0));

        panelKeys.setLayout(new GridLayout(3, 3, 10, 10));
        panelKeys.setBackground(Color.white);

        buildKeys();

        contentPane.add(panelKeys, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 100), 0, 0));

        panelButton.setLayout(new GridBagLayout());
        ((GridBagLayout) panelButton.getLayout()).columnWidths = new int[]{0, 128, 128, 128, 116};
        ((GridBagLayout) panelButton.getLayout()).rowHeights = new int[]{32, 0};

        undobtn.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(204, 102, 0)));
        undobtn.setBackground(new Color(255, 217, 132));
        panelButton.add(undobtn, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 12), 0, 0));

        undobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        board[i][j].getActionMap().put("Undo", new AbstractAction("Undo") {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                            }
                        });
                    }
                }
                if (undo.canUndo()) {
                    undo.undo();
                }
            }
        });

        reset.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(204, 102, 0)));
        reset.setBackground(new Color(255, 217, 132));
        panelButton.add(reset, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 12), 0, 0));

        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the game?", "Reset",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            board[i][j].setText("");
                        }
                        //frame.dispose();;
                    }

                } else {

                }

            }
        });

        tutorial.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(204, 102, 0)));
        tutorial.setBackground(new Color(255, 217, 132));
        panelButton.add(tutorial, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 12), 0, 0));
        tutorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(null, "You will lose your current game. Continue to tutorial?", "",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    new Tutorial();
                    frame.dispose();

                } else {

                }
            }
        });

        quit.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(204, 102, 0)));
        quit.setBackground(new Color(255, 217, 132));
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?\nThe game will not be saved.", "Quit",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    new MainScreen();
                    frame.dispose();

                } else {

                }

            }
        });

        panelButton.add(quit, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        panelButton.setBackground(Color.white);
        contentPane.add(panelButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 100), 0, 0));
        contentPane.setBackground(Color.white);
        frame.add(contentPane);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1400, 1000);
        frame.setVisible(true);

    }

    void createPuzzle()
    {
        board[0][1].setText("8");
        board[0][1].setEnabled(false);
       
        board[2][2].setText("6");
        board[2][2].setEnabled(false);
        
        board[1][4].setText("8");
        board[1][4].setEnabled(false);
        board[1][5].setText("4");
        board[1][5].setEnabled(false);
        board[2][3].setText("3");
        board[2][3].setEnabled(false);
        board[2][4].setText("2");
        board[2][4].setEnabled(false);
        
        board[0][6].setText("2");
        board[0][6].setEnabled(false);
        board[1][7].setText("9");
        board[1][7].setEnabled(false);
        board[2][7].setText("1");
        board[2][7].setEnabled(false);
        
        board[3][1].setText("9");
        board[3][1].setEnabled(false);
        board[3][2].setText("7");
        board[3][2].setEnabled(false);
        board[4][0].setText("8");
        board[4][0].setEnabled(false);
        board[5][1].setText("1");
        board[5][1].setEnabled(false);
        
        board[4][3].setText("9");
        board[4][3].setEnabled(false);
        board[4][5].setText("3");
        board[4][5].setEnabled(false);
        
        board[3][7].setText("8");
        board[3][7].setEnabled(false);
        board[4][8].setText("2");
        board[4][8].setEnabled(false);
        board[5][7].setText("5");
        board[5][7].setEnabled(false);
        board[5][6].setText("9");
        board[5][6].setEnabled(false);
        
        board[6][1].setText("7");
        board[6][1].setEnabled(false);
        board[7][1].setText("3");
        board[7][1].setEnabled(false);
        board[8][2].setText("8");
        board[8][2].setEnabled(false);
        
        board[6][4].setText("4");
        board[6][4].setEnabled(false);
        board[6][5].setText("5");
        board[6][5].setEnabled(false);
        board[7][3].setText("7");
        board[7][3].setEnabled(false);
        board[7][4].setText("1");
        board[7][4].setEnabled(false);
        
        board[6][6].setText("8");
        board[6][6].setEnabled(false);
        board[8][7].setText("4");
        board[8][7].setEnabled(false);
        
        for(int i = 0; i< 9; i++)
        {
            for(int j = 0; j< 9; j++)
            {
                if(board[i][j].isEnabled()==false)
                {
                    board[i][j].setEditable(false);
                    
                }
            }
        }
        
        
        
    }
    final void buildBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new JFormattedTextField(new NumberFormatter(customFormat));

                board[i][j].setBackground(Color.white);
                board[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                board[i][j].setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
                board[i][j].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                 board[i][j].setDisabledTextColor(Color.blue);
                board[i][j].getDocument().addUndoableEditListener(undo);

                board[i][j].addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        temp = (JTextField) e.getComponent();
                        e.getComponent().setBackground(new Color(255, 217, 132));

                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                        e.getComponent().setBackground(Color.white);

                    }
                });

                if ((i == 2) || (i == 8) || (i == 5)) {
                    if ((j == 1) || (j == 3) || (j == 4) || (j == 6) || (j == 7)) {
                        board[i][j].setBorder(new MatteBorder(1, 1, 3, 1, Color.black));
                    }

                    if ((j == 2) || (j == 5) || j == 8) {
                        board[i][j].setBorder(new MatteBorder(1, 1, 3, 3, Color.black));
                    }

                    if (j == 0) {
                        board[i][j].setBorder(new MatteBorder(1, 3, 3, 1, Color.black));
                    }
                }

                if ((i == 1) || (i == 3) || (i == 4) || (i == 6) || (i == 7)) {
                    if ((j == 1) || (j == 3) || (j == 4) || (j == 6) || (j == 7)) {
                        board[i][j].setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
                    }

                    if ((j == 2) || (j == 5) || j == 8) {
                        board[i][j].setBorder(new MatteBorder(1, 1, 1, 3, Color.black));
                    }

                    if (j == 0) {
                        board[i][j].setBorder(new MatteBorder(1, 3, 1, 1, Color.black));
                    }
                }

                if (i == 0) {
                    if ((j == 1) || (j == 3) || (j == 4) || (j == 6) || (j == 7)) {
                        board[i][j].setBorder(new MatteBorder(3, 1, 1, 1, Color.black));
                    }
                    if ((j == 2) || (j == 5) || j == 8) {
                        board[i][j].setBorder(new MatteBorder(3, 1, 1, 3, Color.black));
                    }
                    if (j == 0) {
                        board[i][j].setBorder(new MatteBorder(3, 3, 1, 1, Color.black));
                    }
                }

                board[i][j].getDocument().putProperty("owner", board[i][j]);

                board[i][j].getDocument().addDocumentListener(mylistener);

                panelBoard.add(board[i][j]);
            }
        }
       createPuzzle();

    }

    final void buildKeys() {
        for (int i = 0; i < 9; i++) {
            int inputLabel = i + 1;
            input[i] = new JButton(Integer.toString(inputLabel));
            input[i].setName("" + inputLabel);
            input[i].setBackground(new Color(255, 217, 132));
            input[i].setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(255, 217, 72)));
            input[i].setFont(new Font("Cambria Math", Font.BOLD, 28));
            input[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if (temp.getText().isEmpty()) {
                        temp.setText(e.getComponent().getName());

                        validateGame();
                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

            panelKeys.add(input[i]);
        }
    }

}
