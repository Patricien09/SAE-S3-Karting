package javaPlanning;

import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.DimensionUIResource;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;

public class Main implements ActionListener{

    static JFrame insertionJFrame, mainJFrame, updateJFrame;

    // Objets du JFrame InsertionJFrame 
    JLabel entrerTitre, entrerDebut, entrerFin, entrerType, entrerDate, entrerArticle, confirmation;
    JTextField titre, type, article_id, date, debut, fin;
    JButton enregistrer;
    
    //Objets du JFrame mainJFrame
    JButton buttonInsertionPage, buttonUpdate, buttonDelete;
    JPanel JPButtons, JPListeEvents;
    JScrollPane scrollablePane; 
    JLabel legende1, legende2, legende3,legende4, legende5, legende6, displaySelectedLine;
    String selectedLine;

    //Objets du JFrame UpdateJFrame
    JLabel changerTitre, changerDate, changerDebut, changerFin, changerType, changerArticle, affichageResultat;
    JTextField TFtitre, TFdebut, TFfin, TFtype, TFarticle_id, TFdate;
    JButton modifier;

    Main()
    {
    //Page d'insertion dans la bdd
        insertionJFrame = new JFrame("Page d'insertion");
        insertionJFrame.setLayout(new GridLayout(0,2));
        entrerTitre = new JLabel("Titre", SwingConstants.CENTER);
        titre = new JTextField();

        entrerDate = new JLabel("Date de l'événement",SwingConstants.CENTER);
        date = new JTextField("AAAA:MM:JJ");

        entrerDebut = new JLabel("Heure de début", SwingConstants.CENTER);
        debut = new JTextField("hh:mm");

        entrerFin = new JLabel("Heure de fin", SwingConstants.CENTER);
        fin = new JTextField("hh:mm");

        entrerType = new JLabel("Type", SwingConstants.CENTER);
        type = new JTextField();

        entrerArticle = new JLabel("Id de l'article correspondant", SwingConstants.CENTER);
        article_id = new JTextField();

        enregistrer = new JButton("Enregistrer l'event");
        enregistrer.addActionListener(this);
        confirmation = new JLabel("", SwingConstants.CENTER);

        insertionJFrame.add(entrerTitre);
        insertionJFrame.add(titre);

        insertionJFrame.add(entrerDate);
        insertionJFrame.add(date);

        insertionJFrame.add(entrerDebut);
        insertionJFrame.add(debut);

        insertionJFrame.add(entrerFin);
        insertionJFrame.add(fin);

        insertionJFrame.add(entrerType);
        insertionJFrame.add(type);

        insertionJFrame.add(entrerArticle);
        insertionJFrame.add(article_id);

        insertionJFrame.add(enregistrer);
        insertionJFrame.add(confirmation);

        insertionJFrame.setSize(650,550);
        insertionJFrame.setVisible(false);

        
        
    // Page contenant la liste des events présents dans la bdd

        mainJFrame = new JFrame("Planning");
        //mainJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        mainJFrame.setSize(1000,600);
        mainJFrame.setLayout(new BorderLayout());

        legende1 = new JLabel("id", SwingConstants.CENTER);
        legende2 = new JLabel("Date", SwingConstants.CENTER);
        legende3 = new JLabel("Heure de début", SwingConstants.CENTER);
        legende4 = new JLabel("Heure de fin", SwingConstants.CENTER);
        legende5 = new JLabel("Nombre Participant", SwingConstants.CENTER);
        legende6 = new JLabel("Circuit", SwingConstants.CENTER);
        legende1.setForeground(Color.blue);
        legende2.setForeground(Color.blue);
        legende3.setForeground(Color.blue);
        legende4.setForeground(Color.blue);
        legende5.setForeground(Color.blue);
        legende6.setForeground(Color.blue);

        legende1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legende2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legende3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legende4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legende5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legende6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        legende1.setPreferredSize(new DimensionUIResource(100, 50));
        legende2.setPreferredSize(new DimensionUIResource(100, 50));
        legende3.setPreferredSize(new DimensionUIResource(100, 50));
        legende4.setPreferredSize(new DimensionUIResource(100, 50));
        legende5.setPreferredSize(new DimensionUIResource(100, 50));
        legende6.setPreferredSize(new DimensionUIResource(100, 50));

        JPListeEvents = new JPanel(new GridLayout(0, 6));
        JPListeEvents.add(legende1);
        JPListeEvents.add(legende2);
        JPListeEvents.add(legende3);
        JPListeEvents.add(legende4);
        JPListeEvents.add(legende5);
        JPListeEvents.add(legende6);

        // Cree les instances necessaires a la connexion a la bdd
        String url = "jdbc:mysql://localhost:3306/bdsae";
        Connection con;
        Statement stmt;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Recupere tous les matchs pour les afficher dans la liste
        try {

            con = DriverManager.getConnection(url, "root", "");

            stmt = con.createStatement();

            String query = "SELECT idMatch, date, heureDebut, heureFin, nbrPartNecessaire, (SELECT circuit.nom FROM `circuit` WHERE circuit.idCircuit = Circuit_idCircuit) FROM `match`";

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                // Recupere les infos de chaque match
                String id = rs.getString("idMatch");
                String date = rs.getString("date");
                String heureDebut = rs.getString("heureDebut");
                String heureFin = rs.getString("heureFin");
                String nbrPart = rs.getString("nbrPartNecessaire");
                String circuit = rs.getString("(SELECT circuit.nom FROM `circuit` WHERE circuit.idCircuit = Circuit_idCircuit)");

                final JButton JButtonId = new JButton(id);
                JButtonId.addActionListener(this);
                final JLabel JLabelDate = new JLabel(date, SwingConstants.CENTER);
                final JLabel JLabelHeureDebut = new JLabel(heureDebut, SwingConstants.CENTER);
                final JLabel JLabelHeureFin = new JLabel(heureFin, SwingConstants.CENTER);
                final JLabel JLabelNbrPart = new JLabel(nbrPart, SwingConstants.CENTER);
                final JLabel JLabelCircuit = new JLabel(circuit, SwingConstants.CENTER);

                JButtonId.setBackground(Color.lightGray);
                JLabelDate.setBackground(Color.lightGray);
                JLabelHeureDebut.setBackground(Color.lightGray);
                JLabelHeureFin.setBackground(Color.lightGray);
                JLabelNbrPart.setBackground(Color.lightGray);
                JLabelCircuit.setBackground(Color.lightGray);

                JButtonId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelHeureDebut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelHeureFin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelNbrPart.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabelCircuit.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JPListeEvents.add(JButtonId);
                JPListeEvents.add(JLabelDate);
                JPListeEvents.add(JLabelHeureDebut);
                JPListeEvents.add(JLabelHeureFin);
                JPListeEvents.add(JLabelNbrPart);
                JPListeEvents.add(JLabelCircuit);
            }
            JPListeEvents.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
        } catch (SQLException e) {
            // Gestion des erreurs
            System.err.println("SQLException: " + e.getMessage());
        }

        JPButtons = new JPanel();
        JPButtons.setLayout(new GridLayout(0,3));
        JPButtons.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));

        //JButton pour inserer une ligne
        buttonInsertionPage = new JButton("Insérer un event");
        buttonInsertionPage.addActionListener(this);
        JPButtons.add(buttonInsertionPage);

        //JButton pour modifier une ligne
        buttonUpdate = new JButton("Modifier un event");
        buttonUpdate.addActionListener(this);
        JPButtons.add(buttonUpdate);

        //JButton pour supprimer une ligne
        buttonDelete = new JButton("Supprimer un event");
        buttonDelete.addActionListener(this);
        displaySelectedLine = new JLabel("Ligne sélectionnée: ");
        JPButtons.add(buttonDelete);
        JPButtons.add(new JLabel(""));
        JPButtons.add(displaySelectedLine);
        scrollablePane = new JScrollPane(JPListeEvents);

        mainJFrame.add(scrollablePane, BorderLayout.CENTER);

        
        JPButtons.setPreferredSize(new DimensionUIResource(50, 50));
        mainJFrame.add(JPButtons, BorderLayout.NORTH);     
        

        mainJFrame.setVisible(true);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JFrame pour update un element dans la liste 
        updateJFrame = new JFrame("Page d'update");
        updateJFrame.setLayout(new GridLayout(0,2));

        changerTitre = new JLabel("Titre", SwingConstants.CENTER);
        TFtitre = new JTextField();

        changerDate = new JLabel("Date");
        TFdate = new JTextField();

        changerDebut = new JLabel("Date et heure de début", SwingConstants.CENTER);
        TFdebut = new JTextField();

        changerFin = new JLabel("Date et heure de fin", SwingConstants.CENTER);
        TFfin = new JTextField();

        changerType = new JLabel("Type", SwingConstants.CENTER);
        TFtype = new JTextField();

        changerArticle = new JLabel("Id de l'article correspondant", SwingConstants.CENTER);
        TFarticle_id = new JTextField();

        modifier = new JButton("Modifier l'event");
        modifier.addActionListener(this);
        affichageResultat = new JLabel("", SwingConstants.CENTER);

        updateJFrame.add(changerTitre);
        updateJFrame.add(TFtitre);
        updateJFrame.add(changerDebut);
        updateJFrame.add(TFdebut);
        updateJFrame.add(changerFin);
        updateJFrame.add(TFfin);
        updateJFrame.add(changerType);
        updateJFrame.add(TFtype);
        updateJFrame.add(changerArticle);
        updateJFrame.add(TFarticle_id);
        updateJFrame.add(modifier);
        updateJFrame.add(affichageResultat);
        updateJFrame.setSize(650,550);
        updateJFrame.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        //Quand le bouton pour confirmer l'insertion dans la bdd est selectionné
        if(e.getSource() == enregistrer)
        {
            if(titre.getText()!="" && date.getText()!="" && debut.getText()!="" && fin.getText()!="" && type.getText()!="" && article_id.getText()!="") 
            {
                String Stitre = titre.getText();
                if(compareTime(debut.getText(), fin.getText()))
                {
                    // On adapte le String a mettre dans la bdd pour qu'il corresponde au format dateTime de sql
                    String Sdebut = new String(date.getText()+" "+debut.getText()+":00");
                    String Sfin = new String(date.getText()+" "+ fin.getText()+":00");
                    String Stype = type.getText();
                    String Sarticle_id = article_id.getText();
                    //boolean success = false;
                    // // Appel de la fonction insertionBDD dans ConnexionBDD
                    //boolean success = co.insertionBDD (Stitre, Sdebut, Sfin, Stype, Sarticle_id);
                    // if(success)
                    // {
                    //     confirmation.setText("Insertion réussie");
                    //     // On met à jour la liste des events
                    //     this.refreshList();
                    // }
                    // else
                    // {
                    //     confirmation.setText("Insertion non réussie");
                    // }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Erreur heures (heure de début>=heure de fin","ERREUR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Un des paramètres est vide","ERREUR",JOptionPane.ERROR_MESSAGE);
            }
        }
        //Quand le bouton pour accéder à l'écran d'insertion est sélectionné
        else if(e.getSource() == buttonInsertionPage){
            insertionJFrame.setVisible(true);
        }
        //Quand le bouton pour accéder à l'écran d'update est sélectionné
        else if(e.getSource() == buttonUpdate)
        {
            if(selectedLine!=null)
            {
                // ResultSet rs = co.getEventById(selectedLine);
                // try {
                //     rs.next();
                //     String[] debutDate = rs.getString("debut").split(" ");
                //     String[] finDate = rs.getString("fin").split(" ");
                //     TFtitre.setText(rs.getString("titre"));
                //     TFdate.setText(debutDate[0]);
                //     TFdebut.setText(debutDate[1]);
                //     TFfin.setText(finDate[1]);
                //     TFtype.setText(rs.getString("type"));
                //     TFarticle_id.setText(rs.getString("article_id"));
                // } catch (SQLException e1) {
                //     // TODO Auto-generated catch block
                //     e1.printStackTrace();
                // }
                // updateJFrame.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "Selectionnez une ligne","ERREUR",JOptionPane.ERROR_MESSAGE);
            }
        }
        //Quand le bouton pour supprimer un élément est sélectionné
        else if(e.getSource() == buttonDelete)
        {
            if(selectedLine !=null)
            {
                // boolean result = co.deleteById(selectedLine);
                // if(result)
                // {
                //     this.refreshList();
                // }
            }
            else{
                JOptionPane.showMessageDialog(null, "Selectionnez une ligne","ERREUR",JOptionPane.ERROR_MESSAGE);
            }
        }
        //Quand le bouton pour effectuer la modification d'une ligne dans la bdd est sélectionné
        else if(e.getSource() == modifier)
        {
            if(TFtitre.getText()!="" && TFdate.getText()!="" && TFdebut.getText()!="" && TFfin.getText()!="" && TFtype.getText()!="" && TFarticle_id.getText() !="")
            {
                if(compareTime(TFdebut.getText(), TFfin.getText()))
                {
                    String titre = TFtitre.getText();
                    String debut = new String(date.getText()+" "+TFdebut.getText()+":00");
                    String fin = new String(date.getText()+" "+TFdebut.getText()+":00");
                    String type = TFtype.getText();
                    String article_id = TFarticle_id.getText();
                    // boolean result = co.updateLine(titre, debut, fin, type, article_id, selectedLine);
                    // if(result)
                    // {
                    //     System.out.println("Modification réussie");
                    //     affichageResultat.setText("Modification réussie");
                    //     this.refreshList();
                    // }
                    // else{
                    //     affichageResultat.setText("Modification non réussie");
                    //     System.out.println("Modification non réussie");
                    // }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Erreur heures (heure de début>=heure de fin ou mauvaises valeurs d'entrée)","ERREUR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Un ou plusieurs paramètres sont vides","ERREUR",JOptionPane.ERROR_MESSAGE);
            }
        }
        // Quand un des bouton pour sélectionner une ligne est appuyé
        else
        {
            JButton select = (JButton)e.getSource();
            selectedLine = select.getText();
            System.out.println(selectedLine);
            // ResultSet rs = co.getEventById(selectedLine);
            // //Affiche la ligne sélectionnée (au dessus de la liste dans le JLabel displaySelectedLine)
            // try {
            //     rs.next();
            //     displaySelectedLine.setText("Ligne sélectionnée: "+selectedLine+" - "+rs.getString("titre"));
            // } catch (SQLException e1) {
            //     // TODO Auto-generated catch block
            //     e1.printStackTrace();
            // }
            
        }
    }

    /**
     * Cette méthode sert à comparer deux heures entre elles 
     * @param infTime
     * @param supTime
     * @return true si supTime > infTime, false si supTime <= infTime
     */
    public boolean compareTime(String infTime, String supTime)
    {
        //splittedTime[0] = heure
        //splittedTime[1] = minutes
        String[] infSplittedTime = infTime.split(":");
        String[] supSplittedTime = supTime.split(":");
        int infHeure = 0;
        int infMinutes = 0;
        int supHeure = 0;
        int supMinutes = 0;
        try{
            infHeure = Integer.parseInt(infSplittedTime[0]);
            infMinutes = Integer.parseInt(infSplittedTime[1]);
            supHeure = Integer.parseInt(supSplittedTime[0]);
            supMinutes = Integer.parseInt(supSplittedTime[1]);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Erreur de format");
            return false;
        }
        
        if(infHeure> supHeure)
        {
            return false;
        }
        else if(infHeure== supHeure)
        {
            if(infMinutes>= supMinutes)
            {
                return false;
            }
            else{
                return true;
            }
        }
        return true;
    }

    /*
     * Fonction servant a mettre la liste à jour (après insertion/suppression/modification)
     */
    public void refreshList()
    {
    //     JPListeEvents.removeAll();
    //     JPListeEvents.validate();
    //     JPListeEvents.repaint();
    //     JPListeEvents.setLayout(new GridLayout(0, 6));
    //     JPListeEvents.add(legende1);
    //     JPListeEvents.add(legende2);
    //     JPListeEvents.add(legende3);
    //     JPListeEvents.add(legende4);
    //     JPListeEvents.add(legende5);
    //     JPListeEvents.add(legende6);
    //     ResultSet rs = co.getAllEvents();
    //         try {
    //             while(rs.next())
    //             {
    //                 String id = rs.getString("id");
    //                 String titre = rs.getString("titre");
    //                 String debut = rs.getString("debut");
    //                 String fin = rs.getString("fin");
    //                 String type = rs.getString("type");
    //                 String article_id = rs.getString("article_id");
    //                 final JButton JButtonId = new JButton(id);
    //                 JButtonId.addActionListener(this);
    //                 final JLabel JLabelTitle = new JLabel(titre, SwingConstants.CENTER);
    //                 final JLabel JLabelDebut = new JLabel(debut, SwingConstants.CENTER);
    //                 final JLabel JLabelFin = new JLabel(fin, SwingConstants.CENTER);
    //                 final JLabel JLabelType = new JLabel(type, SwingConstants.CENTER);
    //                 final JLabel AerArr = new JLabel(article_id, SwingConstants.CENTER);
    //                 JButtonId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //                 JLabelTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //                 JLabelDebut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //                 JLabelFin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //                 JLabelType.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //                 AerArr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //                 JPListeEvents.add(JButtonId);
    //                 JPListeEvents.add(JLabelTitle);
    //                 JPListeEvents.add(JLabelDebut);
    //                 JPListeEvents.add(JLabelFin);
    //                 JPListeEvents.add(JLabelType);
    //                 JPListeEvents.add(AerArr);
    //             }
    //         } catch (SQLException e1) {
    //             // TODO Auto-generated catch block
    //             e1.printStackTrace();
    //         }
    //    mainJFrame.validate();
    //    mainJFrame.repaint();
    }
    public static void main(String[] args) throws SQLException {
        new Main();
    }
}