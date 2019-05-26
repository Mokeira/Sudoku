package sudokuGame;
/////////////////////////////Copy of Mainscreen. Editted as needed
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Tutorial extends JFrame {

    private JFrame frame;
    private final JPanel panelText;
    private final JLabel label;
    private final JPanel panelButton;
    private final JButton play;
    private final JButton howTo;
    private final JButton quit;

    public Tutorial() {
        this.quit = new JButton("Quit");
        this.play = new JButton("Play");
        this.label = new JLabel();
        this.panelButton = new JPanel();
        this.howTo = new JButton("How to Play");
        this.panelText = new JPanel();
        this.frame = new JFrame("Sudoku: How to Play");

        frame.setLayout(new GridLayout(2, 1));
        frame.setBackground(Color.white);
        frame.setForeground(Color.white);

        panelText.setBackground(Color.white);
        panelText.setLayout(new GridLayout());

        label.setIcon( new ImageIcon(this.getClass().getResource("tutorial.PNG")));
        label.setBackground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panelText.add(label);
        frame.add(panelText);

        panelButton.setBackground(Color.white);
        panelButton.setLayout(new GridBagLayout());
        GridBagLayout sz = (GridBagLayout) panelButton.getLayout();
        sz.columnWidths = new int[]{605, 205, 600, 0};
        sz.rowHeights = new int[]{36, 60, 60, 60, 0};
        sz.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};

        play.setText("Play");
        play.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(255, 217, 72)));
        play.setBackground(new Color(255, 217, 132));
        play.setFont(new Font("Cambria Math", Font.BOLD, 28));
        panelButton.add(play, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 12, 5), 0, 0));

        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new GameScreen();
                frame.dispose();

            }
        });

        howTo.setText("OK");
        howTo.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(255, 217, 72)));
        howTo.setBackground(new Color(255, 217, 132));
        howTo.setFont(new Font("Cambria Math", Font.BOLD, 28));
        panelButton.add(howTo, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 12, 5), 0, 0));

        howTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainScreen();
                frame.dispose();
            }
        });

        quit.setText("Quit");
        quit.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.black, new Color(255, 217, 72)));
        quit.setBackground(new Color(255, 217, 132));
        play.setVisible(false);
        quit.setVisible(false);
        quit.setFont(new Font("Cambria Math", Font.BOLD, 28));
        panelButton.add(quit, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 12, 5), 0, 0));

        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //playMouseClicked(e);
                frame.dispose();

            }
        });

        frame.add(panelButton);
        frame.pack();
        // frame.setLocationRelativeTo(getOwner());
        frame.setSize(1400, 1000);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
