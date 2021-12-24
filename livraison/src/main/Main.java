package main;
import grille.*;
import game.*;
import observer.*;
import gui.*;
import vueconsole.*;
import java.util.*;
import java.lang.*;
import combatant.*;


public class Main {




    public static void main(String[] args) {
      
        PlateauJeu p = new PlateauJeu();
        p.StrategyRemplissagePlateau();

        System.out.println(p.toString()+"\n");
        VuePlateauJeu vue = new VuePlateauJeu(p);
     
        FenetrePrincipale f = new FenetrePrincipale(p);
        while(!p.isOver()){
            Scanner s = new Scanner(System.in);
            System.out.println("Appuyer sur la touche entrée pour faire jouer le combattant");
            s.nextLine();
            p.play();
        }
        System.out.println(p.toString()+"\n");






        
    }

}