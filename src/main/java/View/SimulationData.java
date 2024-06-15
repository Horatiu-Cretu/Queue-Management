package View;

import javax.swing.*;
import java.awt.*;

public class SimulationData extends JFrame {
    private JTextField timeLimitText, minArrTimeText, maxArrTimeText, maxProcTimeText, minProcTimeText, noQueuesText, noClientsText;
    private JButton startButton;
    private JLabel timeLimitLabel, minArrTimeLabel, maxArrTimeLabel, maxWaitTimeLabel, minWaitTimeLabel, noQueuesLabel, noClientsLabel;
    private JPanel bigPanel;
    private JComboBox<String> comboBox1;

    public SimulationData() {
        // Initializam componentele
        initComponents();

        // Configuram layout ul GUI ului
        configureLayout();

        // Initializam JFrame ul
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(bigPanel);
        pack();
        setLocationRelativeTo(null); // centram GUI ul pe ecran
        setVisible(true);
    }

    private void initComponents() {
        timeLimitText = new JTextField(10);
        minArrTimeText = new JTextField(10);
        maxArrTimeText = new JTextField(10);
        maxProcTimeText = new JTextField(10);
        minProcTimeText = new JTextField(10);
        noQueuesText = new JTextField(10);
        noClientsText = new JTextField(10);
        startButton = new JButton("Start Simulation");
        timeLimitLabel = new JLabel("Time Limit:");
        minArrTimeLabel = new JLabel("Min Arrival Time:");
        maxArrTimeLabel = new JLabel("Max Arrival Time:");
        minWaitTimeLabel = new JLabel("Min Waiting Time:");
        maxWaitTimeLabel = new JLabel("Max Waiting Time:");
        noQueuesLabel = new JLabel("Number of Queues:");
        noClientsLabel = new JLabel("Number of Clients:");
        comboBox1 = new JComboBox<>(new String[]{"SELECTED_TIME", "SELECTED_QUEUE"});
        bigPanel = new JPanel();
    }

    private void configureLayout() {
        bigPanel.setLayout(new GridLayout(0, 2, 10, 10));
        bigPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel timeLimitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timeLimitPanel.add(timeLimitLabel);
        timeLimitPanel.add(timeLimitText);
        bigPanel.add(timeLimitPanel);

        addRow(minArrTimeLabel, minArrTimeText);
        addRow(maxArrTimeLabel, maxArrTimeText);
        addRow(maxWaitTimeLabel, maxProcTimeText);
        addRow(minWaitTimeLabel, minProcTimeText);
        addRow(noQueuesLabel, noQueuesText);
        addRow(noClientsLabel, noClientsText);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(comboBox1);
        buttonPanel.add(startButton);
        bigPanel.add(buttonPanel);

        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        applyFontToComponents(labelFont, textFont, timeLimitLabel, minArrTimeLabel, maxArrTimeLabel,
                maxWaitTimeLabel, minWaitTimeLabel, noQueuesLabel, noClientsLabel);
        applyFontToComponents(null, textFont, timeLimitText, minArrTimeText, maxArrTimeText,
                maxProcTimeText, minProcTimeText, noQueuesText, noClientsText);
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    }

    private void addRow(Component left, Component right) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.add(left, BorderLayout.WEST);
        rowPanel.add(right, BorderLayout.EAST);
        bigPanel.add(rowPanel);
    }

    private void applyFontToComponents(Font labelFont, Font textFont, Component... components) {
        for (Component component : components) {
            if (component instanceof JLabel && labelFont != null) {
                ((JLabel) component).setFont(labelFont);
            } else if (component instanceof JTextField || component instanceof JComboBox) {
                ((JComponent) component).setFont(textFont);
            }
        }
    }

    public JTextField getTimeLimitText() {
        return timeLimitText;
    }

    public JTextField getNoQueuesText() {
        return noQueuesText;
    }

    public JTextField getNoClientsText() {
        return noClientsText;
    }

    public JTextField getMaxArrTimeText() {
        return maxArrTimeText;
    }

    public JTextField getMinArrTimeText() {
        return minArrTimeText;
    }

    public JTextField getMinWaitTimeText() {
        return minProcTimeText;
    }

    public JTextField getMaxWaitTimeText() {
        return maxProcTimeText;
    }

    public JButton getStartButton() {
        return startButton;
    }

    //returnam strategia selectata
    public int getSelectedStrategy() {
        String selectedOption = (String) comboBox1.getSelectedItem();
        if (selectedOption.equals("SELECTED_TIME"))
            return 1;
        else
            return 0;
    }
 
}
