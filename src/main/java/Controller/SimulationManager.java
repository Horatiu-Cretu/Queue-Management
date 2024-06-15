package Controller;

import Model.Client;
import Model.QueueSystem;

import View.SimulationFrame;

import java.util.*;

import java.io.FileWriter;
import java.io.IOException;

public class SimulationManager {
    private static int currentTime;
    private Scheduler scheduler;
    private Queue<Client> clientArrivals;
    private int simulationDuration;
    private Random random;
    private FileWriter fileWriter;
    private SimulationFrame simulationFrame;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minWaitingTime;
    private int maxWaitingTime;
    private SelectionPolicy policy;

    public SimulationManager(int maxQueueSystems, int maxClientsPerQueue, int simulationDuration, int maxArrivalTime,
                             int minArrivalTime, int minWaitingTime, int maxWaitingTime, SelectionPolicy  policy) {
        this.scheduler = new Scheduler(maxQueueSystems, maxClientsPerQueue);
        this.clientArrivals = new PriorityQueue<>();
        this.simulationDuration = simulationDuration;
        this.random = new Random();
        this.simulationFrame = new SimulationFrame();
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minWaitingTime = minWaitingTime;
        this.maxWaitingTime = maxWaitingTime;
        this.policy = policy;

        try{
            // Creem fileWriter ca sa putem manipula informatia din fisiere, aici un fisier dummy dar poate fi modificat dupa nevoie
            fileWriter = new FileWriter("dummy.txt",true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void simulate() {
        simulationFrame.clearSimulationFrame(); // Generam frame ul pentru afisare
        generateClientArrivals(minArrivalTime,maxArrivalTime,minWaitingTime,maxWaitingTime); // Generam clienti random
        currentTime = 0;// Initializam timpul in care ne aflam cu 0
        while (currentTime <= simulationDuration) {
            // In fiecare secunda o sa scriem informatii despre starea cozilor si despre clienti
            System.out.println("\nClients Waiting:" + clientArrivals.size());
            System.out.println("Time " + currentTime);
            try { // Facem acelasi lucru dar in fisier
                fileWriter.write("\n\nTime:" + currentTime + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);}
            try {fileWriter.write("Clients Waiting:\n");}
            catch (IOException e) {
                throw new RuntimeException(e);}
            // Acum afisam informatiile despre simulare in Frame ul creat
            simulationFrame.appendToSimulation("\n\nTime:" + currentTime + "\n");
            simulationFrame.appendToSimulation("Clients Waiting:\n");
            outputWaitingClients(); // Printam informatii despre clientii care asteapta sa fie pusi in coada
            outputQueueStates(); // Printam starea curenta a cozilor
            dispatchArrivingClients(currentTime);// Punem in coada clientii conform metodei dispatch
            processQueueSystems();// Procesam informatia din fiecare thread din fiecare coada
            currentTime++;// Trecem la urmatorul timp de executie
            try {// De asemenea punem pe pauza procesul pentru lizibilitate
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\n THE SIMULATION IS OVER");
        try {fileWriter.close();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private void generateClientArrivals(int MinArrival, int MaxArrival, int MinWaiting, int MaxWaiting) {
        // Generam clienti si datele lor aleatoriu intre marginile impuse in GUI
        int clientId = 1;
        while (clientId <= scheduler.getMaxClientsPerQueue()) {
            int arrivalTime = random.nextInt(MinArrival,MaxArrival);
            int waitingTime = random.nextInt(MinWaiting,MaxWaiting);
            clientArrivals.add(new Client(arrivalTime, waitingTime, clientId));
            clientId++;
        }
    }

    private void dispatchArrivingClients(int currentTime) {
        // Adaugam clienti in coada
        while (!clientArrivals.isEmpty() && clientArrivals.peek().getArrivalTime() == currentTime) {
            Client nextClient = clientArrivals.poll();
            System.out.println("Client with id " + nextClient.getId() + " arrived");
            scheduler.changeStrategy(policy);
            scheduler.dispatchClient(nextClient);
        }
    }

    private void processQueueSystems() {
        // incepem procesarea cozilor in thread uri
        List<QueueSystem> queueSystems = scheduler.getQueueSystems();
        for (QueueSystem system : queueSystems) {
            Thread thread = new Thread(system);
            thread.start();
        }
    }

    private void outputQueueStates() {
        List<QueueSystem> queueSystems = scheduler.getQueueSystems();
        if (!queueSystems.isEmpty()) {
            for (int i = 0; i < queueSystems.size(); i++) {
                QueueSystem queueSystem = queueSystems.get(i);
                System.out.println("Queue " + (i + 1) + ": " + queueSystem.getQueueState());
                simulationFrame.appendToSimulation("Queue " + (i + 1) + ": " + queueSystem.getQueueState());
                try {
                    fileWriter.write("\nQueue " + (i + 1) + ": " + queueSystem.getQueueState());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void outputWaitingClients() {
        if (!clientArrivals.isEmpty()) {
            System.out.println("Waiting Clients:");
            for (Client client : clientArrivals) {
                System.out.println("Client ID: " + client.getId() +
                        ", Arrival Time: " + client.getArrivalTime() +
                        ", Waiting Time: " + client.getWaitingTime());
                simulationFrame.appendToSimulation("\nClient ID: " + client.getId() +
                        ", Arrival Time: " + client.getArrivalTime() +
                        ", Waiting Time: " + client.getWaitingTime());
                try {
                    fileWriter.write("Client ID: " + client.getId() +
                            ", Arrival Time: " + client.getArrivalTime() +
                            ", Waiting Time: " + client.getWaitingTime());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static int getCurrentTime() {
        return currentTime;
    }
}
