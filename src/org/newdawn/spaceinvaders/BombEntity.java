package org.newdawn.spaceinvaders;

public class BombEntity extends ShotEntity {
	/** reference the game */
	private Game game;
	
	public BombEntity(Game game,String sprite,int x,int y, double angleRads) {
		super(game,sprite,x,y,angleRads);
		
		this.game = game;
	}
	
	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	@Override
	public void collidedWith(Entity other) {
		if (used) {
			return;
		}
		// if we've hit an alien, kill it!
		if (!collidesWithPlayer && other instanceof AlienEntity) {
			game.tryToFireBomb(x,y);
			((AlienEntity)other).shot(this);
			used = true;
		} else if (collidesWithPlayer && other instanceof ShipEntity) {
			game.tryToFireBomb(x,y);
			((ShipEntity)other).shot(this);
			used = true;
		}
	}
}
