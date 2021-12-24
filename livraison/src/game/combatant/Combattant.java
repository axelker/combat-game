package combatant;
import element.*;
import arme.*;
import game.Coordonne;
import grille.*;
import observer.*;
import java.util.*;

/**
 * Permet de representer un combattant
 */
public interface Combattant extends ModeleEcoutable {

    public String getnom();
    public int getpointVie();
    public Coordonne getCoordonne();
    public List<Bombe>getListBombe();
    public List<Mine>getListMine();
    public int getx();
    public int gety();
    public Arme getArme();
    public boolean getBouclier();
    public ProxyPlateau getProxy();
    public void setPointVie(int value);
    public void desactiveBouclier();
    public void addBombe(Bombe b);
    public void addMine(Mine m);
    public void removeBombe(Bombe b);
    public void removeMine(Mine m);
    public void setProxy(PlateauJeu p);
    public String strategyCombat(Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement);
    public List<Coordonne>listeDeplacement(Map<Coordonne,Combattant>MapJoueurRestant,Map<Coordonne,Element>mapElement);
    public boolean isValidDeplacement(int x,int y,Map<Coordonne,Combattant>MapJoueurRestant,Map<Coordonne,Element>mapElement);
    public void deplacement(int dx,int dy);
    public void tir(Combattant combattant);
    public List<Coordonne> listCoordonneTir(String directionKey,Map<Coordonne,Element>mapElement);
    public void activeBouclier();
    public List<Coordonne> ListeDepot(Map<Coordonne,Element>mapElement);
    public Bombe depotBombe(int x,int y,Map<Coordonne,Element>mapElement);
    public Mine depotMine(int x,int y,Map<Coordonne,Element>mapElement);
}