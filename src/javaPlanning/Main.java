package javaPlanning;

import javaPlanning.exceptions.CircuitExistException;
import javaPlanning.exceptions.WrongTimeException;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        try {
            ListCircuit.addCircuit(new Circuit("Spa-Francorchamps", "je sais pas", 10));
            ListCircuit.addCircuit(new Circuit("Monza", "je sais toujours pas", 50));
        } catch (CircuitExistException e) {
            System.out.println(e.getMessage());
        }

        Match test = new Match("20/12/2022", "10:30", "12:30", ListCircuit.getInstance().get(1));

        Planning plan = new Planning();

        try {
            plan.ajouterMatch(test);
        } catch (WrongTimeException e) {
            System.out.println(e.getMessage());
        }

        plan.afficherPlanning();
    }
}