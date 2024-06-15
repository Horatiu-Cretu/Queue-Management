package Controller;

import Model.Client;
import Model.QueueSystem;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<QueueSystem> queueSystems;
    private int maxQueueSystems;
    private int maxClientsPerQueue;
    private Strategy strategy;


    public Scheduler(int maxQueueSystems, int maxClientsPerQueue) {
        this.queueSystems = new ArrayList<>();
        this.maxQueueSystems = maxQueueSystems;
        this.maxClientsPerQueue = maxClientsPerQueue;

        for (int i = 0; i < maxQueueSystems; i++) {
            QueueSystem queueSystem = new QueueSystem(maxClientsPerQueue);
            queueSystems.add(queueSystem);
            Thread thread = new Thread(queueSystem);
            thread.start();
        }

        // Initialize with default strategy (shortest queue)
        changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy.equals(SelectionPolicy.SHORTEST_QUEUE)) {
            strategy = new QueueSelectionStrategy();
        } else if (policy.equals(SelectionPolicy.SHORTEST_TIME)) {
            strategy = new TimeBasedQueueSelection();
        }
    }

    public void dispatchClient(Client client) {
        strategy.selectAndAddClient(queueSystems, client, maxClientsPerQueue);
    }

    public List<QueueSystem> getQueueSystems() {
        return queueSystems;
    }

    public int getMaxClientsPerQueue() {
        return maxClientsPerQueue;
    }
}
