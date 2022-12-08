package javaPlanning;

import java.util.ArrayList;

import javaPlanning.exceptions.TooManyParticipant;

public class Match {

    /**
     * Attributs de la classe Match
     * Elle est representee par une date, une heure de debut et de fin, le nombre de
     * joueurs nécessaires
     * Un circuit ou se déroule le match, et enfin, une liste d'adherents
     * La date est de la forme "dd/MM/yyyy"
     * et les heures sont de la forme "hh:mm"
     */
    private String date, heureDeb, heureFin;
    private Circuit circuit;
    private int nbJoueursMax;
    private ArrayList<Adherent> participant;

    /**
     * Constructeur de la classe Match, avec une liste d'adherent en parametre
     * 
     * @param date         date du match au format "dd/MM/yyyy"
     * @param heureDeb     heure de debut du match au format "hh:mm"
     * @param heureFin     heure de fin du match au format "hh:mm"
     * @param nbJoueursMax nombre de joueurs necessaires pour le match
     * @param circuit      circuit ou se deroule le match
     * @param adherent     liste des participants
     */
    public Match(String date, String heureDeb, String heureFin, Circuit circuit, int nbrJoueursMax, ArrayList<Adherent> participant)
            throws IllegalArgumentException {
        // Verifie que l'heure de fin est superieur a l'heure de debut
        if (Integer.parseInt(heureDeb.split(":")[0]) > Integer.parseInt(heureFin.split(":")[0])) {
            throw new IllegalArgumentException("L'heure de fin doit etre superieur a l'heure de debut");
        } else if (Integer.parseInt(heureDeb.split(":")[0]) == Integer.parseInt(heureFin.split(":")[0])) {
            if (Integer.parseInt(heureDeb.split(":")[1]) > Integer.parseInt(heureFin.split(":")[1])) {
                throw new IllegalArgumentException("L'heure de fin doit etre superieur a l'heure de debut");
            }
        }

        if (!checkTimeFormat(heureDeb, heureFin)) {
            throw new IllegalArgumentException("Le format des heures est incorrect");
        }

        if (!checkDateFormat(date)) {
            throw new IllegalArgumentException("Le format de la date est incorrect");
        }

        this.date = date;
        this.heureDeb = heureDeb;
        this.heureFin = heureFin;
        this.nbJoueursMax = nbrJoueursMax;
        this.circuit = circuit;
        this.participant = participant;
    }

    /**
     * Constructeur de la classe Match, sans liste d'adherent en parametre
     * 
     * @param date         date du match
     * @param heureDeb     heure de debut du match
     * @param heureFin     heure de fin du match
     * @param nbJoueursMax nombre de joueurs necessaires pour le match
     * @param circuit      circuit ou se deroule le match
     */
    public Match(String date, String heureDeb, String heureFin, Circuit circuit, int nbrJoueursMax) {
        this(date, heureDeb, heureFin, circuit, nbrJoueursMax, new ArrayList<Adherent>());
    }

    /**
     * Verifie que le format des heures sont bien les bons
     * Cad que les heures sont comprises entre 0 et 23, et les minutes entre 0 et 59
     * 
     * @param heureDeb heure de debut du match
     * @param heureFin heure de fin du match
     * @return true si le format est bon, false sinon
     */
    public boolean checkTimeFormat(String heureDeb, String heureFin) {
        String[] checkDep = heureDeb.split(":");
        int hourDep = Integer.parseInt(checkDep[0]);
        int minuteDep = Integer.parseInt(checkDep[1]);

        String[] checkArr = heureFin.split(":");
        int hourArr = Integer.parseInt(checkArr[0]);
        int minuteArr = Integer.parseInt(checkArr[1]);

        if ((hourDep > 23 || hourDep < 0 || minuteDep > 59 || minuteDep < 0)
                || (hourArr > 23 || hourArr < 0 || minuteArr > 59 || minuteArr < 0)) {
            return false;
        }
        return true;
    }

    /**
     * Verifie que le format de la date est bien le bon
     * Cad que le jour est compris entre 1 et 31, le mois entre 1 et 12
     * 
     * @param date date du match
     * @return true si le format est bon, false sinon
     */
    public boolean checkDateFormat(String date) {
        String[] checkDate = date.split("/");
        int day = Integer.parseInt(checkDate[0]);
        int month = Integer.parseInt(checkDate[1]);

        if (day > 31 || day < 1 || month > 12 || month < 1) {
            return false;
        }
        return true;
    }

    /**
     * Getter de l'attribut date
     * 
     * @return la date du match
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter de l'attribut date
     * 
     * @param date nouvelle date du match
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setter de l'attribut heureDeb
     * 
     * @param heureDeb nouvelle heure de debut du match
     */
    public void setHeureDeb(String heureDeb) {
        this.heureDeb = heureDeb;
    }

    /**
     * Setter de l'attribut heureDeb
     * 
     * @return l'heure de debut du match
     */
    public String getHeureDeb() {
        return heureDeb;
    }

    /**
     * Setter de l'attribut heureFin
     * 
     * @param heureFin nouvelle heure de fin du match
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Getter de l'attribut heureFin
     * 
     * @return l'heure de fin du match
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * Getter de l'attribut circuit
     * 
     * @return le circuit du match
     */
    public Circuit getCircuit() {
        return circuit;
    }

    /**
     * Setter de l'attribut circuit
     * 
     * @param circuit nouveau circuit du match
     */
    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    /**
     * Getter de l'attribut nbJoueursMax
     * 
     * @return le nombre de joueurs max du match
     */
    public int getNbJoueursMax() {
        return nbJoueursMax;
    }

    /**
     * Setter de l'attribut nbJoueursMax
     * 
     * @param nbJoueursMax nouveau nombre de joueurs max du match
     */
    public void setNbJoueursMax(int nbJoueursMax) {
        this.nbJoueursMax = nbJoueursMax;
    }

    /**
     * Getter de l'attribut participant
     * 
     * @return la liste des participants au match
     */
    public ArrayList<Adherent> getParticipant() {
        return participant;
    }

    /**
     * Setter de l'attribut participant
     * 
     * @param participant nouvelle liste des participants au match
     */
    public void setParticipant(ArrayList<Adherent> participant) {
        this.participant = participant;
    }

    /**
     * Ajoute un adherent a la liste de participant
     * 
     * @param adherent
     */
    public void addParticipant(Adherent participant) throws TooManyParticipant {
        if (this.participant.size() < this.nbJoueursMax) {
            this.participant.add(participant);
        } else {
            throw new TooManyParticipant("Le nombre de participants est deja atteint");
        }
    }

    /**
     * Enleve un adherent de la liste de participant
     * 
     * @param participant adherent a enlever
     */
    public void removeParticipant(Adherent participant) {
        this.participant.remove(participant);
    }

    @Override
    public String toString() {
        return (date + ", début à " + heureDeb + " et fin à " + heureFin + ". Nbr nécessaires " + nbJoueursMax + " à "
                + circuit);
    }
}
