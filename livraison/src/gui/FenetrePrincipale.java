package gui;
import grille.*;
import observer.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import combatant.Combattant;
import constante.Constante;

import javax.swing.border.*;
import javax.swing.border.Border;



public class FenetrePrincipale extends JFrame implements ActionListener {
    private PlateauJeu plateau; 
    private JPanel cp;
    private final String valueBoutonSuivant="suivant";
    private JButton suivant = new JButton(valueBoutonSuivant);

    public FenetrePrincipale(PlateauJeu plateau)
    {
        super("Jeu Combat");  // nom de la fenetre 
        this.plateau=plateau;

        //Taille fenetre et quitter fenetre quand cliquer 
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        //Centrer la fentre 
        this.setLocationRelativeTo(null); 

        //Ajout des différentes vue
        CreationElementFenetre();

        this.setVisible(true); 
        
        //Génération des frames des combattants présent sur le plateau
        for(Combattant c : this.plateau.getListeOrdre()){
            JFrame j = new JFrame(c.getnom());
            j.setSize(400, 400);
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            //Centrer la fentre 
            j.setLocationRelativeTo(null); 
            JPanel content=(JPanel) j.getContentPane();  
            content.setLayout(new BorderLayout()); 
            //VUE GRAPHIQUE de chaque combattant 
            VueCombattantGraphique vueC = new VueCombattantGraphique(c); 
            content.add(vueC,BorderLayout.CENTER);
            j.setVisible(true);
        }
        
        
    }

    public void CreationElementFenetre(){
        cp=(JPanel) this.getContentPane();  
        cp.setLayout(new BorderLayout()); 
        //VUE GRAPHIQUE ET AJOUT VUE À FENETRE //
        VueGraphiquePlateau vue = new VueGraphiquePlateau(this.plateau); 
        cp.add(vue,BorderLayout.CENTER);

        //Controleur
        suivant.addActionListener(this);
        cp.add(suivant,BorderLayout.SOUTH);
       
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
            //Prends la valeur texte du bouton pour tester si plusieurs bouton le quelle a été selectionné
            JButton Bsrc= (JButton) e.getSource(); 
            String BoutonPresse= Bsrc.getText();  
            if(BoutonPresse.equalsIgnoreCase(valueBoutonSuivant)){

                plateau.play();
            }
          
    }
      
}