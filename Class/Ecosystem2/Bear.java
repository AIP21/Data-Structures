package Ecosystem2;

/**
 * Bear animal class.
 * If a bear collides with another bear of the same gender, the strongest one survives.
 * If a bear collides with another bear of a different gender, they don't move and reproduce.
 * If a bear collides with a fish, the fish gets eaten.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public class Bear extends Animal {

    public Bear(Tile tile) {
        super(tile);
    }

    @Override
    protected boolean testAnimal(Animal other) {
        // Make sure that other animal is still trying to move into our next tile and is
        // still alive
        if (other.nextTile != this.nextTile || other.isDead())
            return true;

        String otherAnimStr = other.toString();

        switch (otherAnimStr) {
            case "B":
                // If the other animal is of an oppsite gender, then reproduce
                if (other.gender != this.gender) {
                    // A bear is either currently in or moving into the next tile, don't move and
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
            case "F":
                // A fish is in the next tile, move to that next tile and eat the fish!
                return true;
        }

        return true;
    }

    @Override
    public Bear createOffspring(Tile tile) {
        return new Bear(tile);
    }

    @Override
    public String toString() {
        return "B";
    }
}