package Ecosystem2;

/**
 * Fish animal class.
 * If a fish collides with another fish of the same gender, the strongest one
 * survives.
 * If a fish collides with another fish of a different gender, they don't move
 * and reproduce.
 * If a fish collides with a bear, the fish gets eaten.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public class Fish extends Animal {
    public Fish(Tile tile) {
        super(tile);
    }

    @Override
    protected boolean testAnimal(Animal other) {
        // Make sure that other animal is still trying to move into our next tile and is
        // still alive
        if (other.nextTile != this.nextTile || other.isDead())
            return true;

        String otherS = other.toString();

        switch (otherS) {
            case "F":
                // If the other animal is of an oppsite gender, then reproduce
                if (other.gender != this.gender) {
                    // A fish is either currently in or moving into the next tile, don't move and
                    // create a new animal randomly somewhere else.
                    setReproduceFlag(true);
                    nextTile = currentTile;

                    return false;
                }
                // Otherwise, the one with the most strength survives
                else {
                    if (other.strength > this.strength) {
                        die();

                        return false;
                    }
                    // We are stronger... KILL THE WEAKLING!
                    else {
                        other.die();
                        return true;
                    }
                }
            case "B":
                // A bear is in the next tile... uh oh...
                die();

                return false;
        }

        return true;
    }

    @Override
    public Fish createOffspring(Tile tile) {
        return new Fish(tile);
    }

    @Override
    public String toString() {
        return "F";
    }
}