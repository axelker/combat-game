package gui;
import grille.*;
import observer.*;
import constante.Constante;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * La class représente la vue du plateau graphiquement sous forme de JPanel
 */
public class VueGraphiquePlateau extends JPanel implements EcouteurModel{

    /**
     * Plateau de jeu 
     */
    private PlateauJeu plateau;
    /**
     * JPanel représentant le plateau de jeu 
     */
    private JPanel cp = new JPanel();
    /**
     * JPanel représentant la liste des combattants
     */
    private JPanel containsList = new JPanel() ;
    /**
     * 
     * @param p corresponds au plateau de jeu
     */
    public VueGraphiquePlateau(PlateauJeu p)
    {
        this.plateau=p;
        plateau.ajoutEcouteur(this);
        cp.setLayout(new GridLayout(Constante.nbLigne,Constante.nbColonne));
        containsList.setLayout(new BorderLayout());
        setLayout(new GridLayout(1,2));
        this.add(cp);
        this.add(containsList);
        Dessin();
        ConstructList();
    }
    /**
     * Permet de construire  et d'ajouter un Jpanel à la class contenant la représentation du plateau
     */
    public void Dessin(){
        cp.removeAll();
        
        for(int i=0;i<Constante.nbLigne;i++){
            for(int j=0;j<Constante.nbColonne;j++){
                JLabel lab = new JLabel(plateau.getStringElementPlateau(i,j));

                lab.setVerticalAlignment(SwingConstants.CENTER);
                lab.setHorizontalAlignment(SwingConstants.CENTER);

                //Combattant 
                if(Constante.persotoString.contains(lab.getText())){
                    lab.setForeground(Color.red);
                }
                //Element
                if(Constante.elementtoString.contains(lab.getText())){
                    lab.setForeground(Color.white);
                }
                //Explosif
                if(Constante.explosiftoString.contains(lab.getText())){
                    lab.setForeground(Color.orange);
                }
                
                lab.setBackground(Color.black);
                lab.setBorder(BorderFactory.createLineBorder(Color.white, 1));        
                lab.setOpaque(true);
                cp.add(lab);
                
            }
        }
        this.add(cp);
    }
    /**
     * Permet de construire  et d'ajouter un Jpanel à la class contenant sous forme de textarea scrollable la liste des combattants et leur états.
     */
    public void ConstructList(){

        containsList.removeAll();
        JTextArea textarea = new JTextArea(200,200);
        textarea.setText(plateau.EtatCombattant());
        textarea.setFont(new Font("Verdana",1,13));
        textarea.setBorder(BorderFactory.createLineBorder(Color.black, 1));  
        textarea.setEditable(false);
        textarea.setVisible(true);
        JScrollPane scroll = new JScrollPane (textarea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        containsList.add(scroll,BorderLayout.CENTER);

       
        this.add(containsList);
    }
    

   
    @Override
    public void modeleMisAjour(Object source)
    {
        this.plateau.PlateautoString();
        this.Dessin();
        this.ConstructList();
        this.updateUI();
    }
    

}