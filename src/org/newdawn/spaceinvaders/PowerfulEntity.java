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
	/** the interval between shots **/
	private long firingInterval = 1000;
	private long lastShotTime = 0;
	
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
	
	public void move(long delta) {
		super.move(delta);
		tryToFire();
	}
	
	public void tryToFire() {
		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastShotTime < firingInterval) {
			return;
		}
		
		// if we waited long enough, create the shot entity, and record the time.
		lastShotTime = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(game,"sprites/shotFlipped.gif",(int)this.x+10,(int)this.y+30, -Math.PI/2);
		game.AddEntity(shot);
	}
}
