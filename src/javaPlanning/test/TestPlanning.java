package javaPlanning.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javaPlanning.*;
import javaPlanning.exceptions.*;

public class TestPlanning {

    static Planning test;
    static Match match1;
    static Match match2;

    @BeforeAll
    public static void init() {

        test = new Planning();

        try {
            ListCircuit.addCircuit(new Circuit("Monza", "Viale di Vedano 5", 500));
            ListCircuit.addCircuit(new Circuit("Silverstone", "Silverstone", 1000));
            ListCircuit.addCircuit(new Circuit("Monaco", "38 Quai Jean Charles Rey", 100));
            test.ajouterMatch(new Match("01/01/2020", "10:00", "12:00", ListCircuit.getCircuit("Monza")));
            match1 = new Match("01/01/2020", "12:01", "14:00", ListCircuit.getCircuit("Monza"));
            match2 = new Match("02/01/2020", "12:01", "14:00", ListCircuit.getCircuit("Monza"));
        } catch (CircuitExistException e1) {
            System.out.println(e1.getMessage());
        } catch (WrongTimeException e2) {
            System.out.println(e2.getMessage());
        }
    }

    @AfterAll
    public static void end() {
        test.afficherPlanning();;
        System.out.println(ListCircuit.getInstance());
    }

    /**
     * Test l'ajout d'un match dans le cas ou tout est bon
     */
    @Test
    public void testAddMatch() {
        Assertions.assertDoesNotThrow(() -> {
            test.ajouterMatch(match1);
        });

        // Test ajout match jour different mais meme horaire
        Assertions.assertDoesNotThrow(() -> {
            test.ajouterMatch(match2);
        });
    }

    /**
     * Test l'ajout d'un match dans le cas ou l'heure de debut est inferieur a l'heure de fin
     */
    @Test
    public void testAddMatchTimeIllegal() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            test.ajouterMatch(new Match("07/01/2020", "12:00", "10:00", ListCircuit.getCircuit("Silverstone")));
        });
    }

    /**
     * Test l'ajout d'un match dans le cas ou un autre match est deja present
     */
    @Test
    public void testAddMatchWrong() {
        Assertions.assertThrows(WrongTimeException.class, () -> {
            test.ajouterMatch(new Match("01/01/2020", "11:00", "15:00", ListCircuit.getCircuit("Monza")));
        });
    }

    /**
     * Test l'ajout d'un match dans le cas ou c'est le meme horaire mais sur un autre circuit
     */
    @Test
    public void testAddMatchSameTime() {
        Assertions.assertDoesNotThrow(() -> {
            test.ajouterMatch(new Match("01/01/2020", "10:00", "12:00", ListCircuit.getCircuit("Silverstone")));
        });
    }

    /**
     * Test l'ajout d'un match dans la cas ou le circuit n'existe pas
     */
    @Test
    public void testAddMatchCircuitNotExist() {
        Assertions.assertThrows(CircuitExistException.class, () -> {
            test.ajouterMatch(new Match("01/01/2020", "10:00", "12:00", ListCircuit.getCircuit("Castelet")));
        });
    }

    /**
     * Test le deplacement d'un match dans le cas ou tout est bon
     */
    @Test
    public void testMoveMatch() {
        Assertions.assertDoesNotThrow(() -> {
            test.deplacerMatch(match1, "01/01/2020", "16:00", "18:00");
        });

        // Verification que le match est bien deplace
        assertEquals("01/01/2020, début à 16:00 et fin à 18:00. Nbr nécessaires 500 à Monza, Viale di Vedano 5, 500 places", match1.toString());

        Assertions.assertDoesNotThrow(() -> {
            test.deplacerMatch(match1, "07/01/2020", "00:00", "23:00");
        });

        // Verification que le match est bien deplace
        assertEquals("07/01/2020, début à 00:00 et fin à 23:00. Nbr nécessaires 500 à Monza, Viale di Vedano 5, 500 places", match1.toString());
    }

    /**
     * Test le deplacement d'un match dans le cas ou un match est deja present
     */
    @Test
    public void testMoveMatchWrong() {
        Assertions.assertThrows(WrongTimeException.class, () -> {
            test.deplacerMatch(match1, "01/01/2020", "11:00", "15:00");
        });
    }
}
