package javaPlanning;

import java.util.ArrayList;
import javaPlanning.exceptions.WrongTimeException;

public class Planning {

    private ArrayList<Match> matchs;

    /**
     * Constructeur de la classe Planning
     * Initialise la liste des matchs
     */
    public Planning() {
        this.matchs = new ArrayList<Match>();
    }

    /**
     * Ajoute un match au planning
     * Verfie au prealable qu'on peut l'ajouter
     * 
     * @param match le match a ajouter
     */
    public void ajouterMatch(Match match) throws WrongTimeException {
        if (verifierDispo(match)) {
            matchs.add(match);
        } else {
            throw new WrongTimeException("Le match ne peut pas etre ajouté à cause de la date ou de l'heure");
        }
    }

    /**
     * Deplace un match en verifiant qu'on peut le deplacer
     * 
     * @param match      le match a deplacer
     * @param date       la nouvelle date du match
     * @param heureDebut la nouvelle heure de debut du match
     * @param heureFin   la nouvelle heure de fin du match
     */
    public void deplacerMatch(Match match, String date, String heureDebut, String heureFin) throws WrongTimeException {
        if (verifierDispo(new Match(date, heureDebut, heureFin, match.getCircuit()))) {
            match.setDate(date);
            match.setHeureDeb(heureDebut);
            match.setHeureFin(heureFin);
        } else {
            throw new WrongTimeException("Le match ne peut pas etre deplacé à cause de la date ou de l'heure");
        }
    }

    /**
     * Verfie la disponibilite d'ajouter un match a la liste des matchs
     * 
     * @param match match a verifier
     * @return true si le match peut etre ajoute, false sinon
     */
    public boolean verifierDispo(Match match) {
        // Comme l'heure est au format hh:mm, on peut la split en 2
        // chechHourDeb[0] representera l'heure et checkHourDeb[1] les minutes
        String[] checkHourDeb = match.getHeureDeb().split(":");
        String[] checkHourFin = match.getHeureFin().split(":");

        for (Match m : matchs) {
            String[] checkHourDeb2 = m.getHeureDeb().split(":");
            String[] checkHourFin2 = m.getHeureFin().split(":");

            // Si ce n'est pas la meme date, on passe au match suivant
            if (!m.getDate().equals(match.getDate())) {
                continue;
            }

            // Si ce n'est pas sur le meme circuit, on passe au match suivant
            if (!m.getCircuit().equals(match.getCircuit())) {
                continue;
            }

            // Verifie si l'heure de debut du match est comprise entre l'heure de debut et de fin d'un autre match
            // Verifie aussi les minutes, renvoie false si c'est le cas
            if ((Integer.parseInt(checkHourDeb[0]) > Integer.parseInt(checkHourDeb2[0])
                    && Integer.parseInt(checkHourDeb[0]) < Integer.parseInt(checkHourFin2[0]))
                    || (Integer.parseInt(checkHourDeb[0]) == Integer.parseInt(checkHourDeb2[0])
                            && Integer.parseInt(checkHourDeb[1]) >= Integer.parseInt(checkHourDeb2[1]))
                    || (Integer.parseInt(checkHourDeb[0]) == Integer.parseInt(checkHourFin2[0])
                            && Integer.parseInt(checkHourDeb[1]) <= Integer.parseInt(checkHourFin2[1]))) {
                return false;
            }
        }
        // Si on arrive ici, c'est que le match peut etre ajoute
        return true;
    }

    /**
     * Supprime un match du planning
     * 
     * @param match le match a supprimer
     */
    public void supprimerMatch(Match match) {
        matchs.remove(match);
    }

    public void afficherPlanning() {
        for (Match m : matchs) {
            System.out.println(m);
        }
    }

}
