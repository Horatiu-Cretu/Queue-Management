package Controller;

import View.MainController;
import View.SimulationData;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Creem o instanta a interfetei grafice
                SimulationData simulationData = new SimulationData();
                new MainController(simulationData);
        }
    }

