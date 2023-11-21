package Ecosystem;

/**
 * Fish animal class.
 * If a fish collides with another fish, they don't move and reproduce.
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
        // Make sure that other animal is still trying to move into our next tile
        if (other.nextTile != this.nextTile)
            return true;

        String otherS = other.toString();

        switch (otherS) {
            case "F":
                // A fish is either currently in or moving into the next tile, don't move and
                // create a new animal randomly somewhere else.
                setReproduceFlag(true);
                nextTile = currentTile;

                return false;
            case "B":
                // A bear is in the next tile... uh oh...
                nextTile = null;
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