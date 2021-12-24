package grille;
import observer.*;
import combatant.*;
import element.*;
import game.Coordonne;
import constante.Constante;
import java.lang.*;
import java.util.*;
/**
 * La classe PlateauJeu permet de représenter un plateau contenant les éléments d'un jeu de combat en modélisant les règles du jeu appliquées à celui-ci
 *
 */ 
public class PlateauJeu extends AbstractModelEcoutable implements EcouteurModel {


    /**
     * Nombre de ligne du plateau
     */
    private final int nbLigne=Constante.nbLigne;
    /**
     * Nombre de colonne du plateau
     */
    private final int nbColonne=Constante.nbColonne;
    /**
     * Nombre de personnage du plateau
     */
    private int nbPersonnage=Constante.nbPersonnage;
    /**
     * Liste des directions sous forme de string
     */
    private final ArrayList<String>direction=new ArrayList<String>(Constante.listeDirection);
    /**
     * Représente les combattants restants dans la partie en cours
     */
    private Map<Coordonne,Combattant>MapJoueurRestant;

     /**
     * Liste ordonnée mettant en place l'ordre de jeu des combattants
     */
    private List<Combattant> listeOrdreJeu;

    /**
     * Représente les éléments présent dans le plateau
     */
    private Map<Coordonne,Element>MapElementPlateau;
    /**
     * Représente le plateau sous forme de string
     */
    protected String[][] plateau;
    /**
     * Usine à génération de combattant
     */
    private FactoryCombattant factory = new FactoryCombattant();
    /**
     * Numéro du joueur courrant 
     */
    private int indiceNextCombattant=0;
    /**
     * Permet de representer l'action effectuer par un combattant
     */
    private String ActionCombattant="";

    /**
     * Strategy générateur de plateau
     */
    private StrategyPlateau strategyplateau = Constante.strategyplateau;
   

    public PlateauJeu() throws IllegalArgumentException
    {
       
        this.MapJoueurRestant = new HashMap<Coordonne,Combattant>();
        this.MapElementPlateau = new HashMap<Coordonne,Element>();
        
        //Si la taille plateau plus petite que le nombre de personnage
        if(nbLigne*nbColonne<nbPersonnage)
        {
            throw new IllegalArgumentException("La taille du plateau doit être plus grande que le nombre de personnage !");
        }
        //Autorisé un plateau uniquement plus grands que 3x3
        if(nbColonne * nbLigne <2){
            throw new IllegalArgumentException("Veuillez saisir une taille de plateau de 2x2 minimum !"); 
        }
        //Si moin de 2 personnage
        if(this.nbPersonnage<2){
            throw new IllegalArgumentException("Nombre minimum de personnage supérieur à 2 !"); 
        }

        
        this.plateau = new String[nbLigne][nbColonne];
      

        //Si plus d'élément qu'il devrait en avoir dans le plateau
        if(nbLigne*nbColonne<MapJoueurRestant.size()+MapElementPlateau.size()){
            throw new IllegalArgumentException("Le plateau est surchargé d'éléments !"); 
        }
    }
    /**
     * @return retourne l'élément du plateau à la position i,j
     */
    public String getStringElementPlateau(int i,int j){

        if(i>=0 && j>=0 && j<nbColonne && i<nbLigne){

            return this.plateau[i][j];
        }
        return "";
    }
    /**
     * 
     * @return retourn la liste des combattants selon leur ordre de jeu
     */
    public List<Combattant>getListeOrdre(){
        return this.listeOrdreJeu;
    }
    /**
     * Inclus dans le plateau les bombes et mines posées par les joueurs
     */
    public void poseExplosifPlateau(){

        for(Combattant c : MapJoueurRestant.values()){

            //Ajout bombe au plateau
            for(Bombe b : c.getListBombe()){
                if(!b.equals(null)){
                    b.ajoutEcouteur(this);
                    MapElementPlateau.put(b.getCoordonne(),b);
                }
            }
            //Ajout mine au plateau
            for(Mine m : c.getListMine()){
                if(!m.equals(null)){
                    m.ajoutEcouteur(this);
                    MapElementPlateau.put(m.getCoordonne(),m);

                }
            }
        }
        this.fireChangement();

    }

    /**
     * Permet de supprimer les combattants n'ayant plus de point de vie et de les remplacer par
     * une case vide.
     */
    public void removeDeadCombattant(){
        //Eviter modification concurrante
        Map<Coordonne,Combattant> tmp = new HashMap<Coordonne,Combattant>(MapJoueurRestant);
        for(Combattant c : MapJoueurRestant.values()){
            if(c.getpointVie()<=0){
                MapElementPlateau.put(new Coordonne(c.getx(),c.gety()),new Vide(c.getx(),c.gety()));
                for(Bombe b : c.getListBombe()){
                    MapElementPlateau.put(new Coordonne(b.getx(),b.gety()),new Vide(b.getx(),b.gety()));
                }
                for(Mine m : c.getListMine()){
                    MapElementPlateau.put(new Coordonne(m.getx(),m.gety()),new Vide(m.getx(),m.gety()));
                }
                
                tmp.remove(c.getCoordonne());
                this.nbPersonnage--;

            }
        }
        this.MapJoueurRestant=tmp;
        this.fireChangement();

        
    }
    /**
     * Applique les effets d'un element (explosion bombe,mine,pastille...) puis le supprime du plateau et la liste des explosifs pour le combattant
     */
    public void removeElementEffet(){

        //Eviter modif concurante
        Map<Coordonne,Element> tmpP = new HashMap<Coordonne,Element>(MapElementPlateau);
        Map<Coordonne,Combattant> tmpC = new HashMap<Coordonne,Combattant>(MapJoueurRestant);

        for(Element e : MapElementPlateau.values()){

           //Si bombe mine , pastille ... On effectuées leur fonction alors les supprimés
           if(!e.equals(null) && e.effet(MapJoueurRestant)){
              
            for(Coordonne combattantCoor : MapJoueurRestant.keySet()){

                   Combattant joueur = tmpC.get(combattantCoor);
                   //Supprimer liste combattant bombe
                   if(e instanceof Bombe){
                       Bombe b = (Bombe)e;
                        joueur.removeBombe(b);
                   }
                   //Supprimer liste combattant mine
                   if(e instanceof Mine){
                       Mine m = (Mine)e;
                        joueur.removeMine(m);
                    }
                    
                    tmpC.put(joueur.getCoordonne(),joueur);
               }
               tmpP.remove(e.getCoordonne());
               //Mettre un vide si un joueur n'est pas sur la case
               if(!MapJoueurRestant.containsKey(e.getCoordonne())){
                    tmpP.put(e.getCoordonne(),new Vide(e.getx(),e.gety()));
               }
                        

           }
           //Sinon ne rien faire
        }
        MapElementPlateau=tmpP;
        MapJoueurRestant=tmpC;
        this.fireChangement();
    }
    /**
     *  Permet d'effectuer les modifications impliquant le passage d'un tour de jeu
     * Un tour de jeu corresponds à l'ensemble des combattants ayant effectués une action.
     */
    public void TestTourPassed(){
        //Si tous les combattants ont joués cela fait un tour de jeu on remet le compteur de tour à 0 et décrémente le compteur des bombes
        if(this.indiceNextCombattant>=this.nbPersonnage){
            //Décrémenter compteur des bombes personnages en parcourant leur liste de bombe et mettre à jour les bombes du plateau
            for(Combattant comb : MapJoueurRestant.values()){

                if(!comb.getListBombe().isEmpty()){
                    for(Bombe b : comb.getListBombe()){
                        b.setCompteur();
                        MapElementPlateau.put(b.getCoordonne(),b);
                    }
                }
                comb.desactiveBouclier();
                MapJoueurRestant.put(comb.getCoordonne(),comb);
            }
            this.indiceNextCombattant=0;
            this.fireChangement();
        }

    }
    /**
     * Met sur ecoute les éléments contenu dans le plateau
     */
    public void MiseSurEcouteElement(){
        for(Combattant c : MapJoueurRestant.values()){
            c.ajoutEcouteur(this);
            
        }
        for(Element e : MapElementPlateau.values()){
            e.ajoutEcouteur(this);
        }
    }

    /**
     * Actualise les proxy des combattants
     */
    public void ProxySet(){
        for(Combattant c : MapJoueurRestant.values()){
            c.setProxy(this);
            this.fireChangement();            
        }

    }
    /**
     * Utilisation des fonctions permettant le déroulement du jeu selon un ordre définis.
     */
    public void play()
    {
        if(!isOver()){
            TestTourPassed();
            //Tour combattant prochain
            while(!MapJoueurRestant.containsValue(listeOrdreJeu.get(indiceNextCombattant))){
                indiceNextCombattant++;
            }

            //Sortir combattant correspondant à l'indice et effectuer la strategy correspondante
            this.ActionCombattant = listeOrdreJeu.get(indiceNextCombattant).strategyCombat(MapJoueurRestant,MapElementPlateau);
            
            poseExplosifPlateau();
            removeElementEffet();
            removeDeadCombattant();
            ProxySet();
            this.indiceNextCombattant++;
        }


    }

  /**
   * Permet de générer le contenu du plateau
   */
    public void StrategyRemplissagePlateau()
    {
       //effectuer LA STRAEGY PLATEAU
       strategyplateau.StrategyRemplissagePlateau(MapJoueurRestant,MapElementPlateau,factory);
       PlateautoString();
       //Mise sur écoute des éléments du plateau , init proxy et initialiser la liste d'ordre de jeu
       MiseSurEcouteElement();
       ProxySet();
       this.listeOrdreJeu=new ArrayList<Combattant>(MapJoueurRestant.values());
       

    }
    

    
    /**
     * Remplissage du plateau sous forme de chaine de caractère
     * dans le but de représenter le toString des éléments composant
     *  le plateau
     * 
     * */
    public void PlateautoString()
    {
        for(Combattant c : MapJoueurRestant.values())
        {
            this.plateau[c.getx()][c.gety()]=c.toString();
        }
        for(Element c1 : MapElementPlateau.values())
        {
            this.plateau[c1.getx()][c1.gety()]=c1.toString();
        }
    }

    
    /**
     * Permet de spécfié la fin de partie lorsque celle ci ne dispose plus que d'un seul joueur
     * @return boolean spécifiant une fin de partie ou non
     */
    public boolean isOver()
    {
        if(MapJoueurRestant.size()<=1)
        {
            return true;
        }
        return false;
    }
    /**
     * Retourne le combatant gagnant de la partie si celle-ci est finis
     * @return un objet null ou le gagnant de la partie
     */
    public Combattant getWinner()
    {
        if(isOver())
        {
            for(Combattant firstPersonnage : MapJoueurRestant.values() ){
                return firstPersonnage;
            }
            
        }
        return null;
    }
    
    @Override
    public void modeleMisAjour(Object source)
    {
        PlateautoString();
        fireChangement();
    }  

    @Override
    public String toString()
    {
        PlateautoString();
        String res="";
        for(int r=0;r<nbColonne;r++){res+="   "+r;}
        for(int i=0;i<nbLigne;i++)
        {
            res+=System.lineSeparator();
            res+=" "+i;
            for(int j=0;j<nbColonne;j++)
            {
                res+=" " +this.plateau[i][j]+"  ";
            }
            res+=" |";
        }
       
        res+=System.lineSeparator();
        for(int r=0;r<nbColonne;r++){res+="----";}
        res+=System.lineSeparator();
        res+=System.lineSeparator();
       
       
        
        return res;
    }
    /**
     * Retourne sous forme de chaine de caractère 
     * les combattants ainsi que leur différent attribut de jeu 
     *@return chaine listant les combattans sur le plateau
     **/
    public String EtatCombattant() {
        String res="";
        res+="Liste des combattants ainsi que leur point de vie : \n\n";
        for(Combattant c : MapJoueurRestant.values()){
            res+="Combattant " + c.getCoordonne().toString() +" vie : "+c.getpointVie()+" Bouclier : "+c.getBouclier()+"\n Coordonnés des bombes et mines déposés : "+bombeMine(c)+"\n\n";

        }
        res+=this.ActionCombattant+"\n\n\n";
       
        if(this.getWinner()!=null){
            res+="Le gagnant est le combattant : "+ this.getWinner().getnom()+" à la position "+this.getWinner().getCoordonne().toString();
        }

        return res;
    }
    /**
     * @return Chaine de caractère listant les explosifs 
     */
    public String bombeMine(Combattant c){
        String res="";
        for(Bombe b : c.getListBombe()){
            res+=b.getCoordonne().toString() +" compteur : "+b.getcompteur();
        }
        for(Mine m : c.getListMine()){
            res+=m.getCoordonne().toString() +" ";  
        }
        return res;
    }

}