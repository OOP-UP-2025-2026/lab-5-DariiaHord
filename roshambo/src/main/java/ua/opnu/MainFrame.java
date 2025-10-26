package ua.opnu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainFrame extends JFrame implements ActionListener {

    private final Random rnd = new Random();

    public MainFrame(String title) throws HeadlessException {
        super(title);

        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));

        JButton rockButton = new JButton("Камінь");
        rockButton.addActionListener(this);
        rockButton.setActionCommand("rock");

        JButton paperButton = new JButton("Папір");
        paperButton.addActionListener(this);
        paperButton.setActionCommand("paper");

        JButton scissorsButton = new JButton("Ножиці");
        scissorsButton.addActionListener(this);
        scissorsButton.setActionCommand("scissors");

        this.add(rockButton);
        this.add(paperButton);
        this.add(scissorsButton);

        this.pack();
        this.setVisible(true);
    }

    private GameShape generateShape() {
        int r = rnd.nextInt(3);
        switch (r) {
            case 0: return new Rock();
            case 1: return new Paper();
            default: return new Scissors();
        }
    }

    private int checkWinner(GameShape player, GameShape computer) {
        // 0 — нічия, 1 — гравець, -1 — комп’ютер
        if ((player instanceof Rock && computer instanceof Rock) ||
                (player instanceof Paper && computer instanceof Paper) ||
                (player instanceof Scissors && computer instanceof Scissors)) return 0;

        if ((player instanceof Rock && computer instanceof Scissors) ||
                (player instanceof Paper && computer instanceof Rock) ||
                (player instanceof Scissors && computer instanceof Paper)) return 1;

        return -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameShape computerShape = generateShape();

        GameShape playerShape;
        switch (e.getActionCommand()) {
            case "rock":     playerShape = new Rock();     break;
            case "paper":    playerShape = new Paper();    break;
            case "scissors": playerShape = new Scissors(); break;
            default:         playerShape = new GameShape();
        }

        int res = checkWinner(playerShape, computerShape);
        String msg = "Player shape: " + playerShape + ". Computer shape: " + computerShape + ". ";
        msg += (res == 1) ? "Player has won!" : (res == 0) ? "It's a tie!" : "Computer has won!";
        JOptionPane.showMessageDialog(null, msg);
    }
}
