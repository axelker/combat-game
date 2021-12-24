package combatant;
import element.*;
import game.Coordonne;
import grille.*;
import observer.*;
import arme.*;
import constante.Constante;

import java.util.*;



public abstract class Personnage extends AbstractModelEcoutable implements Combattant {
    /**
     * Strategy du personnage
     */
    private StrategyCombattant strategie = Constante.strategycombattant;
    /**
     * Nom du personnage
     */
    protected String nom;
    /**
     * Point de vie du personnage
     */
    protected int pointVie;
    /**
     * Arme du personnage
     */
    protected Arme arme;
    /**
     * Liste des bombes posées par le personnage
     */
    protected List<Bombe>bombepose;
     /**
     * Liste des mines posées par le personnage
     */
    protected List<Mine>minepose;
    /**
     * Coordonné x,y du personnage
     */
    protected Coordonne c;
    /**
     * Activation du bouclier 
     */
    protected boolean bouclier=false;
    /**
     * Direction possible pour le personnage 
     */
    private Map<String,Integer>direction=new HashMap<String,Integer>();
    /**
     * Proxy du personnage 
     */
    private ProxyPlateau proxy;

    /**
     * @param x abscisse du personnage
     * @param y ordonnée du personnage
     */
    public Personnage(int x,int y) throws IllegalArgumentException
    {
        this.pointVie=Constante.pointViePersonnage;
        this.bombepose=new ArrayList<Bombe>();
        this.minepose=new ArrayList<Mine>();
        this.c=new Coordonne(x,y);
        MiseAjourDirection();
        if(this.pointVie<1){
            throw new IllegalArgumentException("Erreur les points de vie du personnage on été initialisés avec une valeur injouable"); 

        }
        this.proxy=new ProxyPlateau(bombepose,minepose);
        


    }
    /**
     * Actualise les valeur des coordonnées du personnages par rapport au direction
     */
    private void MiseAjourDirection(){
        this.direction.put(Constante.gauche,this.c.gety());
        this.direction.put(Constante.haut,this.c.getx());
        this.direction.put(Constante.droite,this.c.gety());
        this.direction.put(Constante.bas,this.c.getx());
    }
    /**
     * @return retourne le nom du personnage
     */
    public String getnom()
    {
        return this.nom;
    }
    /**
     * @return retourne les points de vie du personnage
     */
    public int getpointVie()
    {
        return this.pointVie;
    }
    /**
     * @return retourne la coordonné du personnage
     */
    public Coordonne getCoordonne(){
        return this.c;
    }
    /**
     * @return la liste des bombes posées par le  personnage
     */
    public List<Bombe>getListBombe()
    {
        return this.bombepose;
    }
    /**
     * @return la liste des mines posées par le  personnage
     */
    public List<Mine>getListMine()
    {
        return this.minepose;
    }
    /**
     * @return abscisse x du personnage
     */
    public int getx()
    {
        return c.getx();
    }
    /**
     * @return ordonnée y du personnage
     */
    public int gety()
    {
        return c.gety();
    }
    /**
     * @return l'arme du personnage
     */
    public Arme getArme()
    {
        return this.arme;
    }
    /**
     *  Retourne la valeur d'activation du bouclier
     * @return la valeur du bouclier
     */
    public boolean getBouclier(){
        return this.bouclier;
    }
    /**
     * Retourne le proxy en attribut de class
     * @return proxy personnage 
     */
    public ProxyPlateau getProxy(){
        return this.proxy;
    }

    
    /**
     * Modifie les points de vie du personnage
     * @param value nouvelle valeur des points de vie du personnage
     */
    public void setPointVie(int value)
    {
        this.pointVie=value;
        this.fireChangement();
    }
    /**
     * Desactive le bouclier 
     */
    public void desactiveBouclier(){
        this.bouclier=false;
        this.fireChangement();
    }
    //Méthode permettant l'ajout et la supprésion d'une mine ou bombe dans leur liste respectivent
    public void addBombe(Bombe b){
        this.bombepose.add(b);
    }
    public void addMine(Mine m){
        this.minepose.add(m);
    }
    public void removeBombe(Bombe b){
        if(!bombepose.isEmpty() && bombepose.contains(b)){
            this.bombepose.remove(b);
            this.fireChangement();
        }
    }
    public void removeMine(Mine m){
        if(!minepose.isEmpty() && minepose.contains(m)){
            this.minepose.remove(m);
            this.fireChangement();
        }
    }

    public void setProxy(PlateauJeu p){
        this.proxy.setPlateu(p);
        this.fireChangement();
    }
    
    /**
     * Le personnage effectue une action selon le type de strategie initialisée en attribut de la classe 
     * @param mapCombattant map des combattants 
     * @param mapElement map des éléments
     */
    @Override
    public String strategyCombat(Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement){
        return strategie.strategyCombat(this, mapCombattant, mapElement);
    }

    @Override
    /**
     * Construit la liste des déplacements du personnage
     *  @return retourne la liste des coordonnées des déplacments autorisés pour le personnage
     */
    public List<Coordonne> listeDeplacement(Map<Coordonne,Combattant>MapJoueurRestant,Map<Coordonne,Element>mapElement){
        List<Coordonne>listeDep=new ArrayList<Coordonne>();
        if(this.c.getx()!=0){
            listeDep.add(new Coordonne(this.c.getx()-1,this.c.gety()));
        }
        if(this.c.getx()<Constante.nbLigne-1){
            listeDep.add(new Coordonne(this.c.getx()+1,this.c.gety()));
        }
        if(this.c.gety()!=0){
            listeDep.add(new Coordonne(this.c.getx(),this.c.gety()-1));
        }
        if(this.c.gety()<Constante.nbColonne-1){
            listeDep.add(new Coordonne(this.c.getx(),this.c.gety()+1));
        }
        //Liste temporaire pour vider les déplacement non valide et éviter une exception en enlevant un élément en cours de parcours
        List<Coordonne> tmp = new ArrayList<Coordonne>(listeDep);

        //Vider les coordonné non valide (déja prise par un combattant ou mur)
        for(Coordonne cord : listeDep){
            if(MapJoueurRestant.containsKey(cord)){
                tmp.remove(cord);
            }
            if(mapElement.containsKey(cord) && mapElement.get(cord) instanceof Mur)
            {
                tmp.remove(cord);
            }
        }
        return tmp;


    }
    /**
     * Vérifie que les coordonnées ne sont pas déja occupper par un combattant
     * @param x abscisse x du déplacement envisagé
     * @param y ordonnée y du déplacement envisagé
     * @param MapJoueurRestant map des joueurs affecté à une coordonné
     */
    public boolean isValidDeplacement(int x,int y,Map<Coordonne,Combattant>MapJoueurRestant,Map<Coordonne,Element>mapElement){
       // La coordonne x,y est contenu dans la liste des déplacements autorisés pour le joueur retourner vrai
        if(!listeDeplacement(MapJoueurRestant,mapElement).isEmpty() && listeDeplacement(MapJoueurRestant,mapElement).contains(new Coordonne(x,y))){
            return true;
        }
        return false;
    }


    /**
     * Permet de déplacer le personnage en modifiant l'abscisse x et ordonné y du personnage
     * @param dx nouvelle abscisse x du personnage
     * @param dy nouvelle ordonné y du personnage
     */
    public void deplacement(int dx,int dy)
    {
        //DEPLACEMENT FAIT PERDRE DE L'ENERGIE
        pointVieDeplacement();
        this.c.setx(dx);
        this.c.sety(dy);
        this.fireChangement();
    }
    /** Fait diminuer la vie du combattant en paramètre en effectuant un tir avec l'arme en attribut de classe
     * @param combattant le combattant impacter par le tir
     */
    public void tir(Combattant combattant){
        if(combattant.getBouclier()==false){
            //Firechangement deja definis au sein du set point de vie
            combattant.setPointVie(combattant.getpointVie()-this.arme.getDegat());
        }
    }
     /**
    * Permet de retourner une bombe à la coordonné x,y si celle -ci est valide
    * @return bombe de coordonnée x,y
    */
    public Bombe depotBombe(int x,int y,Map<Coordonne,Element>mapElement)
    {
        if(this.ListeDepot(mapElement).contains(new Coordonne(x,y)))
        {
            addBombe(new Bombe(x,y));
            this.proxy.setListBombe(this.bombepose);
            this.fireChangement();
            return new Bombe(x,y);
        }
        return null;
    }
    /**
     * Retourne une mine en x,y si celle-ci est bien sur une des 8 cases adjacentes au personnage
     * @return mine de coordonné x,y
    */
    public Mine depotMine(int x,int y,Map<Coordonne,Element>mapElement)
    {
        if(this.ListeDepot(mapElement).contains(new Coordonne(x,y)))
        {
            addMine(new Mine(x,y));
            this.proxy.setListMine(this.minepose);
            this.fireChangement();
            return new Mine(x,y);
        }
        return null;
    }
      /**
     * Active le bouclier 
     */
    public void activeBouclier()
    {
        //BOUCLIER FAIT PERDRE DE L'ENERGIE
        pointVieBouclier();
        //Se proteger des tir et bombe pour le prochain tour 
        bouclier=true;
        this.fireChangement();

    }
    
    /**
     * Construit la liste des coordonné impacté par un tir selon sa direction
     * @param directionKey corresponds à l'une des quatres directions possible sous forme de chaine de caractère
     * @param mapElement corresponds au élément du plateau
     * @return la liste des coordonné impacté par le tir 
     */
    public List<Coordonne> listCoordonneTir(String directionKey,Map<Coordonne,Element>mapElement)
    {
        MiseAjourDirection();
        int valueCoord=this.direction.get(directionKey);
        int valDepassement= Constante.nbColonne;
        if(directionKey.equals("bas") || directionKey.equals("haut")){
            valDepassement=Constante.nbLigne;
        }

        //Liste des coordonnées des tir possible 
        List<Coordonne> coordonneTir= ConstructListTir(directionKey, valueCoord, valDepassement);

        //Liste temporaire permettant de vider les emplacement suivant un mur 
        List<Coordonne> tmp = new ArrayList<Coordonne>(coordonneTir);

        for(int i=0;i<coordonneTir.size();i++){
            //Test si l'élément est un mur 
            if(mapElement.containsKey(coordonneTir.get(i)) && mapElement.get(coordonneTir.get(i)) instanceof Mur ){

                for(int k=i;k<coordonneTir.size();k++){
                    tmp.remove(coordonneTir.get(k));  //Vider les emplacements suivants
                }
                break;
            }
        }
        return tmp;
    }
     
    /**
     * Construit la liste des coordonnées impactés par le tir 
     * @param directionKey corresponds à l'une des quatres directions possible sous forme de chaine de caractère
     * @param valueCoord correponds à la valeur x ou y de la coordonné selon la direction en paramètre
     * @param valDepassement corresponds à la taille de la direction à ne pas dépasser
     * @return
     */
    public List<Coordonne> ConstructListTir(String directionKey,int valueCoord,int valDepassement)
    {
        List<Coordonne> coordonneTir = new ArrayList<Coordonne>();
        int porte=this.arme.getPorte();

        while(porte!=0)
        {
            Coordonne valide = null;
            if(directionKey.equals("gauche") || directionKey.equals("haut"))
            {
                valueCoord--;
                porte--;
                if(directionKey.equals("gauche"))
                {
                    valide = new Coordonne(this.getx(),valueCoord);
                }
                else if(directionKey.equals("haut"))
                {
                    valide=new Coordonne(valueCoord,this.gety());
                }
            }
            if(directionKey.equals("droite") || directionKey.equals("bas"))
            {
                valueCoord++;
                porte--;
                if(directionKey.equals("droite"))
                {
                    valide=new Coordonne(this.getx(),valueCoord);
                }
                else if(directionKey.equals("bas"))
                {
                    valide=new Coordonne(valueCoord,this.gety());
                }

            }
            //Ajouter uniquement les valeurs supérieur à 0 et qui ne depasse pas la valeur de depassement
            if(!valide.equals(null) && valueCoord>=0 && valueCoord<valDepassement){
                coordonneTir.add(valide);
            }
        }
        return coordonneTir;
    }
  
    

    /**
     * Construit la liste des coordonnées disponible pour un dépot de bombe/mine du personnage
     * @return liste des coordonnées des bombes/mines possible
     */
    public List<Coordonne> ListeDepot(Map<Coordonne,Element>mapElement){
        int nbLigne=Constante.nbLigne;
        int nbColonne=Constante.nbColonne;
        List<Coordonne>listedepot=new ArrayList<Coordonne>();
        int ligne=-1;
        int colonne=-1;
        for(int i=-1;i<2;i++){
            ligne=getx()+i;
            for(int j=-1;j<2;j++){
                colonne=gety()+j;
                //Coordonnée ne sortant pas du plateau et n'étant pas les mêmes que la position du joueur posant 
                if(ligne>=0 && ligne<nbLigne && colonne>=0 && colonne<nbColonne && (ligne!=getx() || colonne!=gety())){
                    //Ajout uniquement des coordonnées valide (presence d'une case vide ou d'un personnage )

                    if(mapElement.containsKey(new Coordonne(ligne,colonne))){
                        if(mapElement.get(new Coordonne(ligne,colonne)) instanceof Vide){
                            listedepot.add(new Coordonne(ligne,colonne));
                        }
                    }
                    else if(!mapElement.containsKey(new Coordonne(ligne,colonne))){
                        listedepot.add(new Coordonne(ligne,colonne));
                    }
                   
                }
            }
            
        }
       return listedepot; 
    }
  
    /**
     * Effectue l'application du cout d'une déplacement sur la vie du personnage
     */
    protected abstract void pointVieDeplacement();
    /**
     * Effectue l'application du cout de l'activation du bouclier sur la vie du perosnnage
     */
    protected abstract void pointVieBouclier();

     //Redefinition du equals par rapport coordonné x,y et au nom du personnage
     @Override
     public boolean equals(Object c) 
     {
         if(this==c)
         {
             return true;
         }
 
         else if(c instanceof Combattant)
             {
                 Combattant tmp = (Combattant)c;
                 if(tmp.getx()==this.getx() && tmp.gety()==this.gety() && tmp.getnom().equals(this.getnom()))
                 {
                     return true;
                 }
             }
         
         return false;
     }
    


}