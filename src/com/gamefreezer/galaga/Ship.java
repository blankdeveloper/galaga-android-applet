package com.gamefreezer.galaga;

public class Ship extends Entity {

    private final Speed RIGHT_SPEED;
    private final Speed LEFT_SPEED;
    private final Speed NO_SPEED;
    private boolean fireMode;
    private Gun gun;
    Location gunLocation;

    // TODO would be superior to allow ship to animate - use array of imgnames
    public Ship(SpriteCache spriteStore, Screen screen, String imageName,
	    Gun gun, Speed rightSpeed, Speed leftSpeed, Speed noSpeed) {
	super(spriteStore, screen, new Location(), 0, 0,
		new String[] { imageName }, null);

	assert gun != null : "gun is null!";
	assert rightSpeed != null : "rightSpeed is null!";
	assert leftSpeed != null : "leftSpeed is null!";
	assert noSpeed != null : "noSpeed is null!";

	RIGHT_SPEED = rightSpeed;
	LEFT_SPEED = leftSpeed;
	NO_SPEED = noSpeed;
	fireMode = false;
	this.gun = gun;
	this.moveTo(screen.middleX(), screen.inGameBottom() - height);
	gunLocation = new Location(movement.getLocation());
    }

    /* The start location for bullets fired by the ship. */
    public Location getGunLocation(int bulletWidth) {
	gunLocation.moveTo(this.getLocation());
	// TODO bullet offset for ship should be configurable, or use ships
	// bullet width
	gunLocation.moveBy(width / 2 - bulletWidth / 2, 0);
	return gunLocation;
    }

    public void shoot(Bullets bullets, Score score) {
	boolean firedBullet = gun.shoot(bullets, getGunLocation(bullets
		.bulletWidth()));
	if (firedBullet) {
	    score.incremementShotsFired();
	}
    }

    public boolean triggerDown() {
	return fireMode;
    }

    public void fireModeOn() {
	fireMode = true;
    }

    public void fireModeOff() {
	fireMode = false;
    }

    public void goingLeft() {
	setSpeed(LEFT_SPEED);
    }

    public void goingRight() {
	setSpeed(RIGHT_SPEED);
    }

    public void standingStill() {
	setSpeed(NO_SPEED);
    }
}
