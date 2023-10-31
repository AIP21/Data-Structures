package Ecosystem2;

import utils.RandomUtils;

/**
 * Abstract Animal class.
 * Inherit from this class to create a new animal!
 * Handles the data and logic that applies to every animal.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public abstract class Animal {
    protected Tile currentTile;
    protected Tile nextTile;

    protected boolean reproduceFlag = false;

    private boolean isDead = false;

    protected int age = 0;

    protected boolean gender;
    protected float strength;

    public Animal(Tile tile) {
        this.currentTile = tile;

        this.gender = RandomUtils.randomBoolean();
    }

    public void tick() {
        // Make sure we randomly moved
        if (nextTile == null)
            return;

        // Perform the logic
        performLogic();

        // Increment the age
        age++;
    }

    /**
     * Move from the current tile to the next tile.
     * Sets the next tile's contained animal to this animal.
     * Sets this current tile's contained animal to null.
     * Sets this animal's current tile to its next tile.
     * Sets this animal's next tile to null.
     * 
     * NOTE: This can cause issues becuase it overwrites the next tile's contained
     * animal. Be careful when using this!
     */
    public void moveToNextTile() {
        // If this animal moved in the last tick (has a next tile), then move it over
        // to that next tile
        if (nextTile != null) {
            currentTile.setContained(null);
            nextTile.setContained(this);

            // Swap tile references
            currentTile = nextTile;
            nextTile = null;
        }
    }

    public void cancelMove() {
        if (nextTile != null) {
            currentTile.setContained(this);
        }
    }

    /**
     * Determines what should happen based on which animal is in the next tile or is
     * moving into the next tile.
     */
    public void performLogic() {
        // Test animal in next tile (if its not moving out of that tile and its not this
        // animal)
        Animal nextTileAnimal = nextTile.getContained();

        boolean keepChecking = true;

        if (nextTileAnimal != null && nextTileAnimal.nextTile == nextTileAnimal.currentTile && nextTileAnimal != this) {
            this.testAnimal(nextTile.getContained());
        }

        if (!keepChecking || isDead() || getReproduceFlag())
            return;

        // Test all the animals also trying to move into the next tile
        for (Animal other : nextTile.getIncomingList()) {
            keepChecking = this.testAnimal(other);

            if (!keepChecking || isDead() || getReproduceFlag())
                break;
        }
    }

    /**
     * Perform this animal's unique logic.
     * 
     * @param other The other animal to check against
     * @return Whether or not we should keep checking
     */
    protected abstract boolean testAnimal(Animal other);

    /**
     * Create a new offspring animal of the same type.
     * 
     * @param tile The tile to create the new animal at.
     * @return The offspring animal.
     */
    public abstract Animal createOffspring(Tile tile);

    /**
     * Set the traits of this animal to the traits of a parent animal, with some
     * random mutation.
     * This creates basic genetics.
     * 
     * A mutation to a trait has a 25% chance of ocurring.
     * When a mutation occurs, it adds a random value (-0.2 to 0.2) to that trait.
     */
    public void setTraits(Animal parent) {
        this.strength = parent.strength + (RandomUtils.randomBoolean(0.3f) ? RandomUtils.randomFloat(-0.2f, 0.2f) : 0);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile toSet) {
        this.nextTile = toSet;

        if (toSet != null)
            toSet.tryToEnter(this);
    }

    protected void setReproduceFlag(boolean toSet) {
        this.reproduceFlag = toSet;
    }

    public boolean getReproduceFlag() {
        return this.reproduceFlag;
    }

    public boolean isBaby() {
        return age == 0;
    }

    public float getStrength() {
        return strength;
    }

    /**
     * This animal died.
     */
    protected void die() {
        this.nextTile = null;
        isDead = true;
    }

    /**
     * Check if this animal is dead.
     * 
     * @return Whether or not this animal is dead.
     */
    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public String toString() {
        return " ";
    }
}