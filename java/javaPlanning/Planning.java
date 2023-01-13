package javaPlanning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javaPlanning.exceptions.WrongTimeException;

public class Planning {

    // TODO : Trier liste par date et heure
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
        // On verifie d'abord la disponibilite de l'heure, du circuit, et de la date
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
        // Recopie le match dans une variable tampon, et garde l'index du match dans la liste
        Match tmp = new Match(match.getDate(), match.getHeureDeb(), match.getHeureFin(), match.getCircuit(), match.getNbJoueursMax());
        int index = matchs.indexOf(match);
        supprimerMatch(match);
        if (verifierDispo(new Match(date, heureDebut, heureFin, match.getCircuit(), match.getNbJoueursMax()))) {
            match.setDate(date);
            match.setHeureDeb(heureDebut);
            match.setHeureFin(heureFin);
            matchs.add(index, match);
        } else {
            // Si on ne peut pas le deplacer, on remet le match dans la liste
            matchs.add(index, tmp);
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

            // Verifie si l'heure de debut du match est comprise entre l'heure de debut et
            // de fin d'un autre match
            // Verifie aussi que l'heure de fin du match est comprise entre l'heure de debut
            // et de fin d'un autre match
            // Verifie aussi les minutes, renvoie false si c'est le cas
            if ((Integer.parseInt(checkHourDeb[0]) > Integer.parseInt(checkHourDeb2[0])
                    && Integer.parseInt(checkHourDeb[0]) < Integer.parseInt(checkHourFin2[0]))
                    || (Integer.parseInt(checkHourDeb[0]) == Integer.parseInt(checkHourDeb2[0])
                            && Integer.parseInt(checkHourDeb[1]) >= Integer.parseInt(checkHourDeb2[1]))
                    || (Integer.parseInt(checkHourDeb[0]) == Integer.parseInt(checkHourFin2[0])
                            && Integer.parseInt(checkHourDeb[1]) <= Integer.parseInt(checkHourFin2[1]))
                    || (Integer.parseInt(checkHourFin[0]) > Integer.parseInt(checkHourDeb2[0])
                            && Integer.parseInt(checkHourFin[0]) < Integer.parseInt(checkHourFin2[0]))
                    || (Integer.parseInt(checkHourFin[0]) == Integer.parseInt(checkHourDeb2[0])
                            && Integer.parseInt(checkHourFin[1]) >= Integer.parseInt(checkHourDeb2[1]))
                    || (Integer.parseInt(checkHourFin[0]) == Integer.parseInt(checkHourFin2[0])
                            && Integer.parseInt(checkHourFin[1]) <= Integer.parseInt(checkHourFin2[1]))) {
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

    /**
     * Affiche le planning, avec chaque match sur une ligne
     * Un match est affiche selon le format : date/heureDebut-heureFin/nombreNecessaire/Circuit
     */
    public void afficherPlanning() {
        //sortPlanning();
        for (Match m : matchs) {
            System.out.println(m);
        }
    }

    /**
     * Trie le planning selon la date et l'heure de debut, ainsi que les minutes
     */
    public void sortPlanning() {
        Collections.sort(matchs, new Comparator<Match>() {
            @Override
            public int compare(Match m1, Match m2) {
                if (m1.getDate().equals(m2.getDate())) {
                    String[] checkHourDeb1 = m1.getHeureDeb().split(":");
                    String[] checkHourDeb2 = m2.getHeureDeb().split(":");
                    if (Integer.parseInt(checkHourDeb1[0]) == Integer.parseInt(checkHourDeb2[0])) {
                        return Integer.compare(Integer.parseInt(checkHourDeb1[1]), Integer.parseInt(checkHourDeb2[1]));
                    } else {
                        return Integer.compare(Integer.parseInt(checkHourDeb1[0]), Integer.parseInt(checkHourDeb2[0]));
                    }
                } else {
                    return m1.getDate().compareTo(m2.getDate());
                }
            }
        });
    }

    public ArrayList<Match> getMatchs() {
        return matchs;
    }

}
