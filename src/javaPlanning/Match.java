package javaPlanning;

import java.util.ArrayList;

import javaPlanning.exceptions.TooManyParticipant;

public class Match {

    /**
     * Attributs de la classe Match
     * Elle est representee par une date, une heure de debut et de fin, le nombre de joueurs nécessaires
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
     * @param date date du match
     * @param heureDeb heure de debut du match
     * @param heureFin heure de fin du match
     * @param nbJoueursMax nombre de joueurs necessaires pour le match
     * @param circuit circuit ou se deroule le match
     * @param adherent liste des participants
     */
    public Match(String date, String heureDeb, String heureFin, Circuit circuit, ArrayList<Adherent> participant) {
        this.date = date;
        this.heureDeb = heureDeb;
        this.heureFin = heureFin;
        this.nbJoueursMax = circuit.getNbrMaxPlace();
        this.circuit = circuit;
        this.participant = participant;
    }

    /**
     * Constructeur de la classe Match, sans liste d'adherent en parametre
     * @param date date du match
     * @param heureDeb heure de debut du match
     * @param heureFin heure de fin du match
     * @param nbJoueursMax nombre de joueurs necessaires pour le match
     * @param circuit circuit ou se deroule le match
     */
    public Match(String date, String heureDeb, String heureFin, Circuit circuit) {
        this(date, heureDeb, heureFin, circuit, new ArrayList<Adherent>());
    }

    /**
     * Getter de l'attribut date
     * @return la date du match
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter de l'attribut date
     * @param date nouvelle date du match
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setter de l'attribut heureDeb
     * @param heureDeb nouvelle heure de debut du match
     */
    public void setHeureDeb(String heureDeb) {
        this.heureDeb = heureDeb;
    }

    /**
     * Setter de l'attribut heureDeb
     * @return l'heure de debut du match
     */
    public String getHeureDeb() {
        return heureDeb;
    }

    /**
     * Setter de l'attribut heureFin
     * @param heureFin nouvelle heure de fin du match
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Getter de l'attribut heureFin
     * @return l'heure de fin du match
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * Getter de l'attribut circuit
     * @return le circuit du match
     */
    public Circuit getCircuit() {
        return circuit;
    }

    /**
     * Setter de l'attribut circuit
     * @param circuit nouveau circuit du match
     */
    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    /**
     * Getter de l'attribut participant
     * @return la liste des participants au match
     */
    public ArrayList<Adherent> getParticipant() {
        return participant;
    }

    /**
     * Setter de l'attribut participant
     * @param participant nouvelle liste des participants au match
     */
    public void setParticipant(ArrayList<Adherent> participant) {
        this.participant = participant;
    }

    /**
     * Ajoute un adherent a la liste de participant
     * @param adherent
     */
    public void addParticipant(Adherent participant) throws TooManyParticipant {
        if (this.participant.size() < this.nbJoueursMax) {
            this.participant.add(participant);
        }
        else {
            throw new TooManyParticipant("Le nombre de participants est deja atteint");
        }
    }

    /**
     * Enleve un adherent de la liste de participant
     * @param participant adherent a enlever
     */
    public void removeParticipant(Adherent participant) {
        this.participant.remove(participant);
    }

    @Override
    public String toString() {
        return(date + ", début à " + heureDeb + " et fin à " + heureFin + ". Nbr nécessaires " + nbJoueursMax + " à " + circuit);
    }
}
