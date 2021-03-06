package modele;

import java.awt.Graphics;
import java.awt.Rectangle;
import vue.Sprite;


public class Entite {

    private Sprite sprite;
    private HitBox hitBox;
	private Niveau niveau;
    private boolean solid;
    private Strategie strat;
    private int score;
    private int etat = 0;
    
    public Entite(Sprite sprite, Niveau niveau, boolean solid, Strategie strat, int score, HitBox hitBox){
    	this.sprite = sprite;
    	this.niveau = niveau;
        this.solid = solid;
        this. strat = strat;    	
        this.score = score;
        this.hitBox = hitBox;
    }
    
    public Entite(int posX, int posY, int width, int height, Niveau niveau, boolean solid, Strategie strat, Sprite sprite, int score) {
    	this.sprite = sprite;
    	this.strat = strat;
		this.niveau = niveau;
		this.solid = solid;
		this.score = score;
		this.hitBox = new HitBox(new Rectangle(posX,posY,width,height));
	}

    public void setNiveau(Niveau niveau){
    	if(this.niveau == null && niveau != null)
    		this.niveau = niveau;
    }
    
	public boolean intersects(Entite e){
    	return hitBox.intersects(e.hitBox);
    }
    
    public int getPosX(){
    	return hitBox.getPosX();
    }
    
    public int getPosY(){
    	return hitBox.getPosY();
    }
    
    /*
     * \pre posX positifs
     * \pre posY positifs
     */
    public void setPosition(int posX, int posY){
    	if(posX >= 0 && posY >= 0 )
    		hitBox.setPosition(posX, posY);
    	else
    		throw new IllegalArgumentException("coordonne negative");
    }
    
    public int getWidth(){
    	return hitBox.getWidth();
    }
    
    public int getHeight(){
    	return hitBox.getHeight();
    }
    
    public boolean isSolid(){
    	return solid;
    }
    
    /*
     * permet de dessiner l'entité
     * \param g le Graphics utilisé pour dessiner l'entite
     * \param deltaX le decalage horizontal avec lequel afficher l'entité
     * \param deltaY le decalage verticlal avec lequel afficher l'entité
     * \param screenWidth la taille horizontal de l'ecran sur laquel l'entité s'affiche
     * \param screenHeight la taille vertical de l'ecran sur laquel l'entité s'affiche
     * \pre tout les arguments non null
     * \pre screenWidth positifs
     * \pre screenHeight positifs
     */
    public void rendu(Graphics g,int deltaX,int deltaY,int screenWidth,int screenHeight){
    	g.drawImage(sprite.getBufferedImage(), ((getPosX()+deltaX)*screenWidth)/600, ((getPosY()+deltaY)*screenHeight)/600, (getWidth()*screenWidth)/600+1, (getHeight()*screenHeight)/600+1,null);
    }
    
    public void drawDebug(Graphics g,int deltaX,int deltaY,int screenWidth,int screenHeight){
    	hitBox.drawDebug(g,deltaX,deltaY,screenWidth,screenHeight);
    }

    public Sprite getSprite() {
		return sprite;
	}

	public Niveau getNiveau() {
		return niveau;
	}
	
	public Strategie getStrat(){
		return strat;
	}
	
	/*
	 * permet a l'entite de metre a jours sa position
	 * \param tiles tableau representant le decors du niveau
	 * \param l liste des ennemis du niveau
	 * \param lTrace liste des boucliers du niveau
	 */
	public int eval(){
		etat = strat.eval(this, niveau);
		return etat;
	}
	
	public int getEtat() {
		return etat;
	}

	public int setEtat(int etat) {
		this.etat = etat;
		return etat;
	}
	
	/*
	 * premet de supprimer l'entité du niveau auquel elle est rattacher
	 */
	public void deces(){
		Score.augmenterScore(score);
		niveau.supprimerEntite(this);
	}
	
	/*
	 * 
	 */
	public void ajouterSprite(){
		return;
	}
	
}