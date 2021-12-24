package gui;
import grille.*;
import observer.*;
import constante.Constante;
import java.util.*;
import combatant.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * La class représente la vue du plateau graphiquement sous forme de JPanel
 */
public class VueCombattantGraphique extends JPanel implements EcouteurModel{

    /**
     * Combattant 
     */
    private Combattant combattant;
    /**
     * JPanel représentant le proxy du combattant
     */
    private JPanel cp = new JPanel();
  
    /**
     * 
     * @param combattant corresponds au combattant étant représenter par la vue 
     */
    public VueCombattantGraphique(Combattant combattant)
    {
        this.combattant=combattant;
        combattant.ajoutEcouteur(this);
        this.setLayout(new BorderLayout());
        cp.setLayout(new GridLayout(Constante.nbLigne,Constante.nbColonne));
        this.add(cp);
        Dessin();
    }
    /**
     * Permet de construire  et d'ajouter un Jpanel à la class contenant la représentation du plateau
     */
    public void Dessin(){
        cp.removeAll();
        
        for(int i=0;i<Constante.nbLigne;i++){
            for(int j=0;j<Constante.nbColonne;j++){
                JLabel lab = new JLabel(combattant.getProxy().getStringElementPlateau(i,j));

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
      
    @Override
    public void modeleMisAjour(Object source)
    {
        this.Dessin();
        this.updateUI();
    }
    

}