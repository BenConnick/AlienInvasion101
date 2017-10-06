package org.newdawn.spaceinvaders;

public class ExplosionEntity extends Entity {
	/** The number of frames that the explosion stays on screen for */
	private int timer = 100;
	/** The game in which the entity exists */
	private Game game;
	
	ExplosionEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);
		this.game = game;
	}
	
	@Override
	public void move(long delta) {
		// simple frame-dependent timer
		timer-=1;
		if (timer <= 0) {
			game.removeEntity(this);
		}
	}
	
	@Override
	public void collidedWith(Entity other) {
		// if its an alien, notify the alien that it is dead
		if (other instanceof AlienEntity) {
			((AlienEntity)other).shot(this);
		}
	}
}
