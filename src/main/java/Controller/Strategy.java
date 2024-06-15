package Controller;

import Model.Client;
import Model.QueueSystem;

import java.util.List;

public interface Strategy {
    /**
     * Selectam o coada bazat pe strategia corespunzatoare folosind parametrii pentru eficientizare
     *
     * @param queueSystems      O lista de queueSystems
     * @param client            Clientul care trebuie adaugat in coada
     * @param maxClientsPerQueue Numarul maxim de clienti care trebuie adaugat in coada
     */
    void selectAndAddClient(List<QueueSystem> queueSystems, Client client, int maxClientsPerQueue);
}
