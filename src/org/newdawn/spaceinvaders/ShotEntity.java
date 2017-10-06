package org.newdawn.spaceinvaders;

/**
 * An entity representing a shot fired by the player's ship
 * 
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
	/** The speed at which the shot moves */
	private double moveSpeed = -300;
	/** The angle in radians at which the shot is fired (0 is right) */
	private double angleRads = 0;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;
	/** True if the shot collides with the player and NOT enemies */
	private boolean collidesWithPlayer = false;
	
	/**
	 * Create a new shot
	 * 
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 */
	public ShotEntity(Game game,String sprite,int x,int y, double angleRads) {
		super(sprite,x,y);
		
		this.game = game;
		
		this.angleRads = angleRads;
		
		dx = moveSpeed * Math.cos(angleRads);
		dy = moveSpeed * Math.sin(angleRads);
		
		// if the shot is heading downward, it's going towards the p
		collidesWithPlayer = dy > 0;
	}
	
	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);
		
		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			game.removeEntity(this);
		}
		else if (y > game.getHeight()) {
			game.removeEntity(this);
		}
	}
	
	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
		// prevents double kills, if we've already hit something,
		// don't collide
		if (used) {
			return;
		}
		
		// if we've hit an alien, kill it!
		if (!collidesWithPlayer && other instanceof AlienEntity) {
			// remove the affected entities
			game.removeEntity(this);
			AlienEntity alien = (AlienEntity)other;
			alien.shot(this);
			used = true;
		} else if (collidesWithPlayer && other instanceof ShipEntity) {
			// remove the affected entities
			game.removeEntity(this);
			used = true;
			game.notifyDeath();
		}
	}
}