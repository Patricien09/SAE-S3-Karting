package javaPlanning;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import javaPlanning.exceptions.CircuitExistException;
import javaPlanning.exceptions.WrongTimeException;

import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;


public class Main implements ActionListener {

    static JFrame insertionJFrame, mainJFrame, updateJFrame, reservationJFrame;

    // Cree les instances necessaires a la connexion a la bdd
    String url = "jdbc:mysql://localhost:3306/bdsae";
    Connection con;

    // Objets pour le planning
    Planning planning = new Planning();
    JComboBox circuitComboBox;

    // Objets du JFrame InsertionJFrame
    JLabel entrerDate, entrerDebut, entrerFin, entrerNbrPart, entrerCircuit, confirmation;
    JTextField date, debut, fin, nbrPart, circuit;
    JButton enregistrer;

    // Objets du JFrame mainJFrame
    JButton buttonInsertionPage, buttonUpdate, buttonDelete, buttonReservation;
    JPanel JPButtons, JPListeEvents;
    JScrollPane scrollablePane;
    JLabel legende1, legende2, legende3, legende4, legende5, legende6, legende7, displaySelectedLine;
    String selectedLine;

    // Objets du JFrame UpdateJFrame
    JLabel changerDate, changerDebut, changerFin, changerGagnant, affichageResultat;
    JTextField TFdate, TFdebut, TFfin;
    JComboBox changerGagnantCB;
    JButton modifier;

    // Objets du JFrame ReservationJFrame
    JLabel RDate, RDebut, RFin, RCircuit, RParticipants, RAdherent, RAccepterLabel, RRefuserLabel;
    JButton RAccepter, RRefuser;
    JPanel RButtons, RLists;

    Main() {
        // Connexion a la bdd pour recuperer les circuits et les placer dans ListCircuit
        // On remplit aussi le planning avec les matchs de la bdd, ainsi que les adhérents
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM circuit");
            circuitComboBox = new JComboBox();

            while (rs.next()) {
                // Ajoute les circuits
                circuitComboBox.addItem(rs.getString(4));
                ListCircuit.addCircuit(new Circuit(rs.getString(4), rs.getString(2), rs.getInt(3)));
            }

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM `match` WHERE 1");
            // Utilise un autre Statement pour avoir les circuits
            Statement stmt2 = con.createStatement();
            // Utilise un autre Statement pour avoir les adherents participant au match
            Statement stmtAd = con.createStatement();

            while (rs2.next()) {
                // On recupere le circuit correspondant au circuit du match pour avoir son nom
                ResultSet rsCircuit = stmt2.executeQuery("SELECT * FROM circuit WHERE idCircuit = " + rs2.getInt(8));
                rsCircuit.next();
                // Ajoute les matchs
                planning.ajouterMatch(new Match(rs2.getString(2), rs2.getString(3), rs2.getString(4),
                        ListCircuit.getCircuit(rsCircuit.getString(4)), rs2.getInt(5)));
                // On recupere les adherents participant au match
                ResultSet rsAd = stmtAd.executeQuery("SELECT idPersonne, nom, prenom FROM personne JOIN match_has_adherent ON match_has_adherent.Adherent_Personne_idPersonne = personne.idPersonne WHERE Match_idMatch = " + rs2.getInt(1));
                while (rsAd.next()) {
                    Adherent adherent = new Adherent(rsAd.getString(2), rsAd.getString(3), rsAd.getInt(1));
                    // Ajoute les participants au match
                    planning.getMatchs().get(planning.getMatchs().size() - 1).addParticipant(adherent);
                    // Si c'est le gagnant du match, on l'ajoute au match
                    if (rs2.getInt(6) == rsAd.getInt(1)) {
                        planning.getMatchs().get(planning.getMatchs().size() - 1).setGagnant(adherent);
                    }
                }
            }
            // Ferme les Statement et les ResultSet
            stmt.close();
            stmt2.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Page d'insertion dans la bdd
        insertionJFrame = new JFrame("Page d'insertion");
        insertionJFrame.setLayout(new GridLayout(0, 2));
        entrerDate = new JLabel("Date", SwingConstants.CENTER);
        date = new JTextField("yyyy-MM-dd");

        entrerDebut = new JLabel("Heure de début", SwingConstants.CENTER);
        debut = new JTextField("hh:mm");

        entrerFin = new JLabel("Heure de fin", SwingConstants.CENTER);
        fin = new JTextField("hh:mm");

        entrerNbrPart = new JLabel("Nombre de participants", SwingConstants.CENTER);
        nbrPart = new JTextField();

        entrerCircuit = new JLabel("Circuit", SwingConstants.CENTER);
        circuit = new JTextField();

        enregistrer = new JButton("Enregistrer l'event");
        enregistrer.addActionListener(this);
        confirmation = new JLabel("", SwingConstants.CENTER);

        insertionJFrame.add(entrerDate);
        insertionJFrame.add(date);

        insertionJFrame.add(entrerDebut);
        insertionJFrame.add(debut);

        insertionJFrame.add(entrerFin);
        insertionJFrame.add(fin);

        insertionJFrame.add(entrerNbrPart);
        insertionJFrame.add(nbrPart);

        insertionJFrame.add(entrerCircuit);
        insertionJFrame.add(circuitComboBox);

        insertionJFrame.add(enregistrer);
        insertionJFrame.add(confirmation);

        insertionJFrame.setSize(650, 550);
        insertionJFrame.setVisible(false);

        // Page contenant la liste des matchs présents dans la base de donnees
        mainJFrame = new JFrame("Planning");
        mainJFrame.setSize(1000, 600);
        mainJFrame.setLayout(new BorderLayout());

        legende1 = new JLabel("id", SwingConstants.CENTER);
        legende2 = new JLabel("Date", SwingConstants.CENTER);
        legende3 = new JLabel("Heure de début", SwingConstants.CENTER);
        legende4 = new JLabel("Heure de fin", SwingConstants.CENTER);
        legende5 = new JLabel("Nombre Participant", SwingConstants.CENTER);
        legende6 = new JLabel("Circuit", SwingConstants.CENTER);
        legende7 = new JLabel("Gagnant", SwingConstants.CENTER);

        JPListeEvents = new JPanel(new GridLayout(0,7));

        ArrayList<JLabel> listLegende = new ArrayList<JLabel>();

        listLegende.add(legende1);
        listLegende.add(legende2);
        listLegende.add(legende3);
        listLegende.add(legende4);
        listLegende.add(legende5);
        listLegende.add(legende6);
        listLegende.add(legende7);

        for (JLabel l : listLegende) {
            l.setForeground(Color.blue);
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            l.setPreferredSize(new DimensionUIResource(100, 50));
            JPListeEvents.add(l);
        }

        // Place les matchs du planning dans la liste
        try {
            // Tri les matchs avant
            planning.sortPlanning();
            // Recupere les infos de chaque match
            for (Match m : planning.getMatchs()) {
                String id = (planning.getMatchs().indexOf(m) + 1) + "";
                String date = m.getDate();
                String heureDebut = m.getHeureDeb();
                String heureFin = m.getHeureFin();
                String nbrPart = String.valueOf(m.getNbJoueursMax());
                String circuit = m.getCircuit().getName();
                String gagnant = m.getGagnant() != null ? m.getGagnant().getNom() + " " + m.getGagnant().getPrenom() : null;

                final JButton JButtonId = new JButton(id);
                JButtonId.addActionListener(this);
                final JLabel JLabelDate = new JLabel(date, SwingConstants.CENTER);
                final JLabel JLabelHeureDebut = new JLabel(heureDebut, SwingConstants.CENTER);
                final JLabel JLabelHeureFin = new JLabel(heureFin, SwingConstants.CENTER);
                final JLabel JLabelNbrPart = new JLabel(nbrPart, SwingConstants.CENTER);
                final JLabel JLabelCircuit = new JLabel(circuit, SwingConstants.CENTER);
                final JLabel JLabelGagnant = new JLabel(gagnant, SwingConstants.CENTER);

                JButtonId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelHeureDebut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelHeureFin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelNbrPart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelCircuit.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelGagnant.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Ajout a la liste
                JPListeEvents.add(JButtonId);
                JPListeEvents.add(JLabelDate);
                JPListeEvents.add(JLabelHeureDebut);
                JPListeEvents.add(JLabelHeureFin);
                JPListeEvents.add(JLabelNbrPart);
                JPListeEvents.add(JLabelCircuit);
                JPListeEvents.add(JLabelGagnant);
                JPListeEvents.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
            }

            // On ferme la connexion
            con.close();
        } catch (SQLException e) {
            // Gestion des erreurs
            System.err.println("SQLException: " + e.getMessage());
        }

        JPButtons = new JPanel();
        JPButtons.setLayout(new GridLayout(0, 4));
        JPButtons.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));

        // JButton pour inserer une ligne
        buttonInsertionPage = new JButton("Insérer un match");
        buttonInsertionPage.addActionListener(this);
        JPButtons.add(buttonInsertionPage);

        // JButton pour modifier une ligne
        buttonUpdate = new JButton("Modifier un match");
        buttonUpdate.addActionListener(this);
        JPButtons.add(buttonUpdate);

        // JButton pour supprimer une ligne
        buttonDelete = new JButton("Supprimer un match");
        buttonDelete.addActionListener(this);
        displaySelectedLine = new JLabel("Ligne sélectionnée: ");
        JPButtons.add(buttonDelete);

        // JButton pour ouvrir la fenetre de gestion des reservations
        buttonReservation = new JButton("Gestion des réservations");
        buttonReservation.addActionListener(this);
        JPButtons.add(buttonReservation);

        JPButtons.add(new JLabel(""));
        JPButtons.add(displaySelectedLine);
        scrollablePane = new JScrollPane(JPListeEvents);

        mainJFrame.add(scrollablePane, BorderLayout.CENTER);

        JPButtons.setPreferredSize(new DimensionUIResource(50, 50));
        mainJFrame.add(JPButtons, BorderLayout.NORTH);

        mainJFrame.setVisible(true);
        mainJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // JFrame pour update un element dans la liste
        updateJFrame = new JFrame("Page d'update");
        updateJFrame.setLayout(new GridLayout(0, 2));

        changerDate = new JLabel("Date", SwingConstants.CENTER);
        TFdate = new JTextField();

        changerDebut = new JLabel("Heure de début", SwingConstants.CENTER);
        TFdebut = new JTextField();

        changerFin = new JLabel("Heure de fin", SwingConstants.CENTER);
        TFfin = new JTextField();

        changerGagnant = new JLabel("Gagnant", SwingConstants.CENTER);
        changerGagnantCB = new JComboBox<String>();

        modifier = new JButton("Modifier le match");
        modifier.addActionListener(this);
        affichageResultat = new JLabel("", SwingConstants.CENTER);

        updateJFrame.add(changerDate);
        updateJFrame.add(TFdate);
        updateJFrame.add(changerDebut);
        updateJFrame.add(TFdebut);
        updateJFrame.add(changerFin);
        updateJFrame.add(TFfin);
        updateJFrame.add(changerGagnant);
        updateJFrame.add(changerGagnantCB);
        updateJFrame.add(modifier);
        updateJFrame.add(affichageResultat);
        updateJFrame.setSize(650, 550);
        updateJFrame.setVisible(false);

        
        // JFrame pour la gestion des reservations
        reservationJFrame = new JFrame("Gestion des réservations");

        RButtons = new JPanel(new GridLayout(0,2));
        RLists = new JPanel(new GridLayout(0, 7));

        RAccepter = new JButton("Accepter");
        RRefuser = new JButton("Refuser");

        RAccepter.addActionListener(this);
        RRefuser.addActionListener(this);

        RButtons.add(RAccepter);
        RButtons.add(RRefuser);

        // On ajoute les données dans la table
        refreshR();

        reservationJFrame.add(RLists, BorderLayout.CENTER);
        reservationJFrame.add(RButtons, BorderLayout.SOUTH);
        reservationJFrame.pack();
        reservationJFrame.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            con = DriverManager.getConnection(url, "root", "");
        } catch (Exception ex) {
            System.out.println("Erreur de connexion");
        }

        // Quand le bouton pour confirmer l'insertion dans la bdd est selectionné
        if (e.getSource() == enregistrer) {
            // Verification que tous les champs sont remplis
            if (!date.getText().equals("") && !debut.getText().equals("") && !fin.getText().equals("")
                    && !nbrPart.getText().equals("")) {
                try {
                    // Creation du match pour verifier toutes les infos
                    Match tmp = new Match(date.getText(), debut.getText(), fin.getText(),
                            ListCircuit.getCircuit(circuitComboBox.getSelectedItem().toString()),
                            Integer.parseInt(nbrPart.getText()));
                    // Ajout du match dans le planning
                    planning.ajouterMatch(tmp);
                    // Message de confirmation
                    confirmation.setText("Match ajouté avec succès");

                    Statement stmt = con.createStatement();

                    String query = "INSERT INTO `match` (`idMatch`, `date`, `heureDebut`, `heureFin`, `nbrPartNecessaire`, `Gagnant`, `resultatFinal`, `Circuit_idCircuit`, `Admin_Personne_idPersonne`) VALUES (NULL, '" + java.sql.Date.valueOf(date.getText()) + "', '" + java.sql.Time.valueOf(debut.getText() + ":00") + "', '" + java.sql.Time.valueOf(fin.getText() + ":00") + "', '" + Integer.parseInt(nbrPart.getText()) + "', NULL, NULL, (SELECT `idCircuit` from `circuit` WHERE `adresse` = '" + ListCircuit.getCircuit(circuitComboBox.getSelectedItem().toString()).getAdresse() + "'), '36')";

                    // Ajoute la ligne dans la bdd apres les verifications
                    stmt.executeUpdate(query);
                    stmt.close();

                    // Rafraichissement de la liste
                    refreshList(tmp, "add");

                    insertionJFrame.setVisible(false);
                    confirmation.setText("");
                } catch (WrongTimeException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
                } catch (CircuitExistException e2) {
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e3) {
                    JOptionPane.showMessageDialog(null, e3.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e4) {
                    JOptionPane.showMessageDialog(null, e4.getMessage(), "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Un des paramètres est vide", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Quand le bouton pour accéder à l'écran d'insertion est sélectionné
        else if (e.getSource() == buttonInsertionPage) {
            insertionJFrame.setVisible(true);
        }

        // Quand le bouton pour accéder à l'écran d'update est sélectionné
        else if (e.getSource() == buttonUpdate) {
            if (selectedLine != null) {
                // Recupere le match choisit dans la liste
                Match m = planning.getMatchs().get(Integer.valueOf(selectedLine) - 1);

                // Rempli les champs avec les infos du match
                TFdate.setText(m.getDate());
                TFdebut.setText(m.getHeureDeb());
                TFfin.setText(m.getHeureFin());

                // Rempli la comboBox des Participants
                for (Adherent a : m.getParticipant()) {
                    changerGagnantCB.addItem(a);
                }

                // Affiche la page d'update
                updateJFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Selectionnez une ligne", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Quand le bouton pour supprimer un element est selectionne
        else if (e.getSource() == buttonDelete) {
            if (selectedLine != null) {
                Match m = planning.getMatchs().get(Integer.valueOf(selectedLine) - 1);
                planning.supprimerMatch(m);

                try {
                    Statement stmt = con.createStatement();

                    // Supprime d'abord les lignes dans la table match_has_adherent ou le match est present
                    // Puis supprime la ligne dans la table match
                    String query1 = "DELETE from `match_has_adherent` WHERE `Match_idMatch` = (SELECT `idMatch` FROM `match` WHERE `date` = '" + java.sql.Date.valueOf(m.getDate()) + "' AND `heureDebut` = '" + java.sql.Time.valueOf(m.getHeureDeb()) + "' AND `heureFin` = '" + java.sql.Time.valueOf(m.getHeureFin()) + "')";
                    String query2 = "DELETE from `match` WHERE `date` = '" + java.sql.Date.valueOf(m.getDate()) + "' AND `heureDebut` = '" + java.sql.Time.valueOf(m.getHeureDeb()) + "' AND `heureFin` = '" + java.sql.Time.valueOf(m.getHeureFin()) + "'";

                    stmt.executeUpdate(query1);
                    stmt.executeUpdate(query2);
                    stmt.close();
                } catch (SQLException e1) {
                    System.err.println(e1.getMessage());
                }

                refreshList(m, "delete");
            } else {
                JOptionPane.showMessageDialog(null, "Selectionnez une ligne", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Quand le bouton pour effectuer la modification d'une ligne dans la bdd est
        // selectionne
        else if (e.getSource() == modifier) {
            if (!TFdate.getText().equals("") && !TFdebut.getText().equals("") && !TFfin.getText().equals("")) {
                String date = TFdate.getText();
                String debut = TFdebut.getText();
                String fin = TFfin.getText();
                Adherent gagnant = (Adherent) changerGagnantCB.getItemAt(changerGagnantCB.getSelectedIndex());
                try {
                    Match tmp = planning.getMatchs().get(Integer.valueOf(selectedLine) - 1);
                    String oldDate = tmp.getDate();
                    String oldDebut = tmp.getHeureDeb();
                    String oldFin = tmp.getHeureFin();
                    planning.deplacerMatch(tmp, date, debut, fin);

                    tmp.setGagnant(gagnant);

                    Statement stmt = con.createStatement();

                    // Desactive la verification des clefs etrangeres
                    stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
                    
                    // Modifie la ligne dans la bdd
                    String query = "UPDATE `match` SET `date` = '" + java.sql.Date.valueOf(date) + "', `heureDebut` = '" + java.sql.Time.valueOf(debut) + "', `heureFin` = '" + java.sql.Time.valueOf(fin) + "', `Gagnant` = " + gagnant.getId() + " WHERE `date` = '" + java.sql.Date.valueOf(oldDate) + "' AND `heureDebut` = '" + java.sql.Time.valueOf(oldDebut) + "' AND `heureFin` = '" + java.sql.Time.valueOf(oldFin) + "'";
                    
                    stmt.executeUpdate(query);

                    stmt.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
                    stmt.close();

                    refreshList(tmp, "update");
                    updateJFrame.setVisible(false);
                } catch (WrongTimeException ex) {
                    // Affiche une boite de dialogue d'erreur
                    JOptionPane.showMessageDialog(null,
                            "Erreur heures (heure de début>=heure de fin ou mauvaises valeurs d'entrée)", "ERREUR",
                            JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e1) {
                    System.err.println(e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Un ou plusieurs paramètres sont vides", "ERREUR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // Quand le bouton pour afficher la fenetre de gestion des reservations est appuye
        else if (e.getSource() == buttonReservation) {
            reservationJFrame.setVisible(true);
        }

        // Quand le bouton pour accepter une reservation est appuye
        else if (e.getSource() == RAccepter) {
            if (selectedLine != null) {
                try {
                    Statement stmt = con.createStatement();

                    // Modifie la ligne dans la bdd
                    String query = "UPDATE `reservation` SET `autorisation` = '1' WHERE `idReservation` = " + selectedLine;
                    stmt.executeUpdate(query);

                    stmt.close();
                } catch (SQLException e1) {
                    System.err.println(e1.getMessage());
                }

                RLists.removeAll();
                refreshR();
            } else {
                JOptionPane.showMessageDialog(null, "Selectionnez une ligne", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }

            RLists.validate();
            RLists.repaint();
        }

        // Quand le bouton pour refuser une reservation est appuye
        else if (e.getSource() == RRefuser) {
            if (selectedLine != null) {
                try {
                    Statement stmt = con.createStatement();

                    // Modifie la ligne dans la bdd
                    String query = "UPDATE `reservation` SET `autorisation` = '2' WHERE `idReservation` = " + selectedLine;
                    stmt.executeUpdate(query);

                    stmt.close();
                } catch (SQLException e1) {
                    System.err.println(e1.getMessage());
                }

                RLists.removeAll();
                refreshR();
            } else {
                JOptionPane.showMessageDialog(null, "Selectionnez une ligne", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }

            RLists.validate();
            RLists.repaint();
        }

        // Quand un des bouton pour selectionner une ligne est appuye
        else {
            JButton select = (JButton) e.getSource();
            selectedLine = select.getText();
            System.out.println(selectedLine);
            displaySelectedLine.setText("Ligne sélectionnée : " + String.valueOf(selectedLine));
        }

        try {
            con.close();
        } catch (SQLException e1) {
            System.err.println(e1.getMessage());
        }
    }

    /*
     * Fonction servant a mettre la liste à jour (après
     * insertion/suppression/modification), type donnant le type de mise à jour
     */
    public void refreshList(Match m, String type) {
        switch (type) {
            case "add":
                // Ajoute le match dans la liste des matchs visibles
                String id = (planning.getMatchs().indexOf(m) + 1) + "";
                String dateR = m.getDate();
                String debutR = m.getHeureDeb();
                String finR = m.getHeureFin();
                String nbrPartR = String.valueOf(m.getNbJoueursMax());
                String circuitR = m.getCircuit().getName();

                final JButton JButtonId = new JButton(id);
                JButtonId.addActionListener(this);
                final JLabel JLabelTitle = new JLabel(dateR, SwingConstants.CENTER);
                final JLabel JLabelDebut = new JLabel(debutR, SwingConstants.CENTER);
                final JLabel JLabelFin = new JLabel(finR, SwingConstants.CENTER);
                final JLabel JLabelNbrPart = new JLabel(nbrPartR, SwingConstants.CENTER);
                final JLabel JLabelCircuit = new JLabel(circuitR, SwingConstants.CENTER);

                JButtonId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelDebut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelFin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelNbrPart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelCircuit.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JPListeEvents.add(JButtonId);
                JPListeEvents.add(JLabelTitle);
                JPListeEvents.add(JLabelDebut);
                JPListeEvents.add(JLabelFin);
                JPListeEvents.add(JLabelNbrPart);
                JPListeEvents.add(JLabelCircuit);
                break;

            case "delete":
                // Supprime le match de la liste des matchs visibles
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)) + 6);
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)) + 5);
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)) + 4);
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)) + 3);
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)) + 2);
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)) + 1);
                JPListeEvents.remove(7 * (Integer.valueOf(selectedLine)));
                break;

            case "update":
                // Met à jour le match dans la liste des matchs visibles
                JLabel date = (JLabel) JPListeEvents.getComponent(7 * (Integer.valueOf(selectedLine)) + 1);
                date.setText(m.getDate());
                JLabel hDeb = (JLabel) JPListeEvents.getComponent(7 * (Integer.valueOf(selectedLine)) + 2);
                hDeb.setText(m.getHeureDeb());
                JLabel hFin = (JLabel) JPListeEvents.getComponent(7 * (Integer.valueOf(selectedLine)) + 3);
                hFin.setText(m.getHeureFin());
                JLabel gagnant = (JLabel) JPListeEvents.getComponent(7 * (Integer.valueOf(selectedLine)) + 6);
                gagnant.setText(m.getGagnant().getNom() + " " + m.getGagnant().getPrenom());
                break;
        }

        mainJFrame.validate();
        mainJFrame.repaint();
    }

    public void refreshR() {
        try {
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservation");
            // Si autorisation est a 0, cela veut dire que l'admin n'a pas encore repondu
            // S'il est a 1, c'est que l'admin a accepté la reservation
            // S'il est a 2, c'est que l'admin a refusé la reservation
            while (rs.next()) {
                if (!rs.getString("autorisation").equals("0")) {
                    continue;
                }

                String idR = rs.getString("idReservation");
                String date = rs.getString("date");
                String debut = rs.getString("heureDebut");
                String fin = rs.getString("heureFin");
                String nbrPart = rs.getString("nombreParticipant");
                String circuit = rs.getString("Circuit_idCircuit");
                String adherent = rs.getString("Adherent_Personne_idPersonne");

                // Recupere le nom du circuit
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM circuit WHERE idCircuit = " + circuit);
                rs2.next();
                String circuitName = rs2.getString("nom");

                try {
                    // Verifie que la reservation est possible
                    if (!planning.verifierDispo(new Match(date, debut, fin, ListCircuit.getCircuit(circuitName), 0))) {
                        System.out.println("Reservation " + idR + " refusée : circuit déjà réservé");
                        // On refusera la reservation
                        selectedLine = String.valueOf(idR);
                        RRefuser.doClick();
                        continue;
                    }
                } catch (CircuitExistException e) {
                    System.out.println("Reservation " + idR + " refusée " + e.getMessage());
                    continue;
                } catch (IllegalArgumentException e2) {
                    System.out.println("Reservation " + idR + " refusée " + e2.getMessage());
                    continue;
                }

                JButton idRButton = new JButton(idR);
                idRButton.addActionListener(this);
                JLabel dateLabel = new JLabel(date, SwingConstants.CENTER);
                JLabel debutLabel = new JLabel(debut, SwingConstants.CENTER);
                JLabel finLabel = new JLabel(fin, SwingConstants.CENTER);
                JLabel nbrPartLabel = new JLabel(nbrPart, SwingConstants.CENTER);
                JLabel circuitLabel = new JLabel(circuit, SwingConstants.CENTER);
                JLabel adherentLabel = new JLabel(adherent, SwingConstants.CENTER);

                dateLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                debutLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                finLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                nbrPartLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                circuitLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                adherentLabel.setBorder(BorderFactory.createLineBorder(Color.black));

                RLists.add(idRButton);
                RLists.add(dateLabel);
                RLists.add(debutLabel);
                RLists.add(finLabel);
                RLists.add(nbrPartLabel);
                RLists.add(circuitLabel);
                RLists.add(adherentLabel);
            }
            con.close();
        } catch (SQLException ex) {
            // Gestion des erreurs
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        new Main();
    }
}