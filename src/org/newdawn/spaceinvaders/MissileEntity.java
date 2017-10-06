package org.newdawn.spaceinvaders;

public class MissileEntity extends Entity {
	/** The speed at which the missile moves */
	private double moveSpeed = 300;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;
	/** True if the shot collides with the player and NOT enemies */
	private boolean collidesWithPlayer = false;
	/** The target for the seeking behavior */
	private Entity target = null;
	/** acceleration vector */
	private Vec2 acceleration;
	
	/**
	 * Create a new shot
	 * 
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 */
	public MissileEntity(Game game,String sprite,int x,int y, Entity target, double dx, double dy) {
		super(sprite,x,y);
		
		this.game = game;
		
		this.target = target;
		
		// set initial velocity
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
				super.move(delta);
		
		// has the target been removed / invalidated?
		if (target == null || target.x < 0 || target.y < 0 || target.x > game.getWidth() || target.y > game.getHeight()) {
			// deactivate
			game.removeEntity(this);
			used = true;
		}
		
		// create a look-at vector (for acceleration)
		Vec2 lookVec = new Vec2(target.x - x, target.y - y);
		// normalize
		lookVec.normalize();
		
		Vec2 velocity = new Vec2(dx + lookVec.x * 10, dy + lookVec.y * 10);
		
		if (velocity.sqrMagnitude() > moveSpeed * moveSpeed) {
			velocity.clamp(moveSpeed);
		}
		
		//System.out.println(velocity.y);
		
		// set velocity
		dx = velocity.x;
		dy = velocity.y; 
		
		// if we shot off the screen, remove ourself
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
			((AlienEntity)other).shot(this);
			used = true;
		} else if (collidesWithPlayer && other instanceof ShipEntity) {
			// remove the affected entities
			game.removeEntity(this);
			used = true;
			((ShipEntity)other).shot(this);
		}
	}
}
