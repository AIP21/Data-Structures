package Ecosystem;

import java.util.ArrayList;

/**
 * A tile in the ecosystem.
 * Can contain a Bear, Fish, or nothing.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public class Tile {
    private final int row, col;

    private Animal contained;

    // A list containing the animals trying to move into this tile in this step
    private ArrayList<Animal> incomingAnimals = new ArrayList<>();

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the animal contained in this tile.
     * 
     * @return The animal contained in this tile. Returns null if no animal is
     *         contained within this tile.
     */
    public Animal getContained() {
        return contained;
    }

    /**
     * Set the animal contained in this tile.
     * 
     * @param toSet The animal to be contained by this tile.
     */
    public void setContained(Animal toSet) {
        this.contained = toSet;
    }

    /**
     * Try to move into this tile.
     * Adds the animal to this tile's incoming animal list.
     * 
     * @param animal The animal trying to move into this tile.
     */
    public void tryToEnter(Animal animal) {
        incomingAnimals.add(animal);
    }

    /**
     * Allow the winning animal into this tile.
     * Sets the contained animal to the last remaining animal in the incoming list
     * (if there is one).
     */
    public void allowAnimalIntoTile() {
        if (incomingAnimals.size() == 0)
            return;

        if (contained != null && contained.toString().toLowerCase() == "b") {
            System.out.println("Replaced bear with: " + incomingAnimals.get(0).toString());
        }

        // ALlow the first animal into this tile
        incomingAnimals.get(0).moveToNextTile();

        // Cancel the rest of the animals' moves
        for (int i = 1; i < incomingAnimals.size(); i++) {
            Animal animal = incomingAnimals.get(i);
            animal.nextTile = animal.currentTile;

            animal.moveToNextTile();
        }
    }

    /**
     * Clear the list that holds every animal trying to move into this tile.
     */
    public void clearIncomingList() {
        incomingAnimals.clear();
    }

    /**
     * Get the incoming animal list.
     * 
     * @return The list of incoming animals.
     */
    public ArrayList<Animal> getIncomingList() {
        return incomingAnimals;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    @Override
    public String toString() {
        if (contained == null)
            return " ";
        else
            return contained.isBaby() ? contained.toString().toLowerCase() : contained.toString();
    }
}