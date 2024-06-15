package Model;

import Controller.SimulationManager;

import java.util.LinkedList;
import java.util.Queue;

public class QueueSystem implements Runnable {
    private Queue<Client> queue; //avem coada de clienti
    private int maxClientsPerQueue;

    public QueueSystem(int maxClientsPerQueue) { // creem un sistem de cozi
        this.queue = new LinkedList<>();
        this.maxClientsPerQueue = maxClientsPerQueue;
    }
    public synchronized void addClientToQueue(Client client) { // adaugam clientii in coada sincronizat
        if (queue.size() < maxClientsPerQueue) {
            queue.offer(client);
            System.out.println("Client " + client.getId() + " added to Queue " + this.hashCode());
        }
    }
    public synchronized void removeClientFromQueue(Client client) { // scoatem clientii din coada sincronizat
        if (queue.contains(client)) {
            queue.remove(client);
            System.out.println("Client " + client.getId() + " removed from Queue " + this.hashCode());
        }
    }
    public synchronized int getCurrentQueueSize() {
        return queue.size();
    }
    public synchronized String getQueueState() { // gasim starea cozii intr un anumit moment de timp, afisand id-urile
        StringBuilder queueState = new StringBuilder();
        for (Client client : queue) {
            queueState.append(client.getId()).append(", ");
        }
        return queueState.toString();
    }
    public synchronized double getAverageWaitingTime() { // calculam waiting time ul
        if (queue.isEmpty()) {
            return 0.0;
        }
        int totalWaitingTime = 0;
        for (Client client : queue) {
            totalWaitingTime += client.getWaitingTime();
        }
        return (double) totalWaitingTime / queue.size();
    }
    @Override
    public void run() {
        while (true) {
            if (!queue.isEmpty()) {
                Client nextClient = queue.peek();
                // Verificam daca clientul sau timpul a ajuns la final
                if (SimulationManager.getCurrentTime() - nextClient.getArrivalTime() >= nextClient.getWaitingTime()) {
                    // Daca waiting time ul s a terminat, scoatem clientul din coada
                    removeClientFromQueue(nextClient);
                }
            }
            // Pentru ca simularea sa poata fi lizibila asteptam o perioada de timp
            try {
                Thread.sleep(100); // Facem waiting time ul aproximativ o secunda, acest timp poate sa fie modificat
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
