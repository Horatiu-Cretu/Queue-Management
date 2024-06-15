package Model;

public class Client implements Comparable<Client> {
    private int arrivalTime;
    private int waitingTime;
    private int id;
    public Client(int arrivalTime, int waitingTime, int id) {
        this.arrivalTime = arrivalTime;
        this.waitingTime = waitingTime;
        this.id = id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public int getId() {
        return id;
    }
    @Override
    public int compareTo(Client o) {
        return this.arrivalTime - o.arrivalTime;
    }

}
