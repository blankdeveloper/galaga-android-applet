package com.gamefreezer.galaga;

public class AnimationPool {

    private Animation[] pool;
    private boolean[] available;
    private int free;
    private String name;

    public AnimationPool(String name, SpriteCache spriteCache, int maxFrames,
	    AnimationSource src, int size, boolean oneShot) {
	this.name = name;
	pool = new Animation[size];
	available = new boolean[size];
	free = size;

	for (int idx = 0; idx < pool.length; idx++) {
	    pool[idx] = new Animation(spriteCache, maxFrames, src, oneShot,
		    this);
	    available[idx] = true;
	}
    }

    public Animation get() {
	for (int idx = 0; idx < pool.length; idx++) {
	    if (available[idx]) {
		available[idx] = false;
		free--;
		pool[idx].reset();
		return pool[idx];
	    }
	}
	return null;
    }

    /* Return an animation to the pool: clients must call. */
    public void put(Animation anim) {
	assert anim != null : "anim is null!";
	for (int idx = 0; idx < pool.length; idx++) {
	    if (pool[idx] == anim && !available[idx]) {
		available[idx] = true;
		free++;
	    }
	}
    }

    public int remaining() {
	return free;
    }

    public Dimension getDimensions() {
	assert pool[0] != null : "pool is uninitialised!";
	return pool[0].getDimensions();
    }

    @Override
    public String toString() {
	return "AnimationPool: " + name + " " + remaining() + "/" + pool.length;
    }

}
