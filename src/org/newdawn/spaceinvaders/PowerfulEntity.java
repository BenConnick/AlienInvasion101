package org.newdawn.spaceinvaders;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class PowerfulEntity extends AlienEntity {
	// properties
	/** The speed at which the alient moves horizontally */
	private double moveSpeed = 75;
	/** The game in which the entity exists */
	private Game game;
	/** The number of shots this alien must recieve before dying **/
	private int health = 2; 
	
	public PowerfulEntity(Game game,String ref,int x,int y) {
		super(game,ref,x,y);
		
		this.game = game;
		dx = -moveSpeed;
	}
	
	/**
	 * Notification that this alien has been shot
	 * 
	 * @param collider
	 */
	@Override
	public void shot(ShotEntity collider) {
		health -= 1;
		if (health <=0) {
			// notify 
			game.notifyAlienKilled();
			// kill if shot
			game.removeEntity(this);
		}
	}
}
