package View;

import Controller.SelectionPolicy;
import Controller.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private SimulationData simulationData;

    public MainController(SimulationData simulationData) {
        this.simulationData = simulationData;
        setupStartButtonListener();
    }

    private void setupStartButtonListener() {
        simulationData.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Returnam elementele inputate in GUI
                    int timeLimit = Integer.parseInt(simulationData.getTimeLimitText().getText());
                    int numberOfClients = Integer.parseInt(simulationData.getNoClientsText().getText());
                    int numberOfQueues = Integer.parseInt(simulationData.getNoQueuesText().getText());
                    int minWait = Integer.parseInt(simulationData.getMinWaitTimeText().getText());
                    int maxWait = Integer.parseInt(simulationData.getMaxWaitTimeText().getText());
                    int minArrival = Integer.parseInt(simulationData.getMinArrTimeText().getText());
                    int maxArrival = Integer.parseInt(simulationData.getMaxArrTimeText().getText());
                    int SelectedStrategy = simulationData.getSelectedStrategy();
                    SelectionPolicy timeSelection;
                    if (SelectedStrategy == 1) {
                         timeSelection = SelectionPolicy.SHORTEST_TIME;
                    }  else {
                         timeSelection = SelectionPolicy.SHORTEST_QUEUE;
                    }
                    // Creem o instanta a SimulationManager ului cu elementele din GUI
                    SimulationManager simulationManager = new SimulationManager(
                            numberOfQueues, numberOfClients, timeLimit, maxArrival,
                            minArrival, minWait, maxWait, timeSelection
                    );
                    // Incepem simularea apeland metoada smiulate din simulationManager
                    simulationManager.simulate();

                } catch (NumberFormatException ex) {
                    // Daca input ul in GUI este invalid returnam un mesaj de eroare
                    simulationData.getTimeLimitText().setText("INVALID INPUT!!!");
                }
            }
        });
    }
}