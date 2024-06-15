package Controller;

import Model.Client;
import Model.QueueSystem;

import java.util.List;

public class TimeBasedQueueSelection implements Strategy {

    @Override
    public void selectAndAddClient(List<QueueSystem> queueSystems, Client client, int maxClientsPerQueue) {
        int minWaitingTime = Integer.MAX_VALUE;
        QueueSystem bestQueueSystem = null;
        // Gasim coasa cu cel mai scurt timp de asteptare
        for (QueueSystem system : queueSystems) {
            if (system.getAverageWaitingTime() < minWaitingTime && system.getCurrentQueueSize() < maxClientsPerQueue) {
                minWaitingTime = (int) system.getAverageWaitingTime();
                bestQueueSystem = system;
            }
        }
        // Adaugam clientul in lista
        if (bestQueueSystem != null) {
            bestQueueSystem.addClientToQueue(client);
        }
    }
}
