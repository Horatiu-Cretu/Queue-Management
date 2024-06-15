package Controller;

import Model.Client;
import Model.QueueSystem;

import java.util.List;

public class QueueSelectionStrategy implements Strategy {

    @Override
    public void selectAndAddClient(List<QueueSystem> queueSystems, Client client, int maxClientsPerQueue) {
        int smallestQueueSize = Integer.MAX_VALUE;
        QueueSystem bestQueueSystem = null;

        // Gasim coada cu cei mai putini clienti
        for (QueueSystem system : queueSystems) {
            if (system.getCurrentQueueSize() < smallestQueueSize && system.getCurrentQueueSize() < maxClientsPerQueue) {
                smallestQueueSize = system.getCurrentQueueSize();
                bestQueueSystem = system;
            }
        }

        // Adaugam clienti in coada
        if (bestQueueSystem != null) {
            bestQueueSystem.addClientToQueue(client);
        }
    }

}
