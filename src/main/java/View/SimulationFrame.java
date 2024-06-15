package View;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private JLabel time;
    private JPanel bigPanel;
    private JTextArea waitingText;
    private JTextArea queuesText;

    public SimulationFrame() {
        super("SimulationFrame");
        time = new JLabel();

        waitingText = new JTextArea(100, 100);
        queuesText = new JTextArea(100, 100);

        bigPanel = new JPanel();
        bigPanel.add(time);
        bigPanel.add(waitingText);
        bigPanel.add(queuesText);

        JScrollPane waitingScrollPane = new JScrollPane(waitingText);
        waitingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane queuesScrollPane = new JScrollPane(queuesText);
        queuesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        bigPanel.add(waitingScrollPane);
        bigPanel.add(queuesScrollPane);

        setContentPane(bigPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,1000));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void appendToSimulation(String message) {
        waitingText.append("\n" + message);
    }

    public void clearSimulationFrame() {
        waitingText.setText("");
        queuesText.setText("");
    }
}
