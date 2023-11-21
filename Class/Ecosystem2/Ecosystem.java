package Ecosystem2;

import java.util.Scanner;

import utils.CE;
import utils.RandomUtils;

/**
 * An ecosystem simulation. Part 2.
 * There are only bear and fish animals.
 * 
 * Project description:
 * Write a simulator as in the previous project, but add a boolean gender field
 * and a floating-point strength field to each Animal object. Now, if two
 * animals of the same type try to collide, then they only create a new instance
 * of that type of animal if they are of different genders. Otherwise, if two
 * animals of the same type and gender try to collide, then only the one of
 * larger strength survives.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public class Ecosystem extends CE {
    private int SIZE = 10;

    private boolean quietMode = false;

    private float fishChance = 0.2f, bearChance = 0.1f;

    private Tile[][] tiles = null;
    private int iteration = 0;

    private int lastFish = 0;
    private int lastBears = 0;

    private Scanner inputScanner;

    // Runner
    public static void main(String[] args) {
        new Ecosystem(args);
    }

    /**
     * Create an Ecosystem object using arguments.
     */
    public Ecosystem(String[] args) {
        print("\n~~~~~~~~~~~~\nEcosystem Simulation Part 2. By Alexander Irausquin-Petit (2023)\n~~~~~~~~~~~~\n");

        // Handle arguments
        boolean shouldContinue = true;
        for (int i = 0; i < args.length && shouldContinue; i++) {
            String arg = args[i];

            switch (arg.toLowerCase()) {
                case "-h", "-help":
                    print("Help:\n   '-h' or '-help'  Show possible arguments\n   '-s' or '-size'  The size of the ecosystem world\n   '-q' or '-quiet'  Don't print out the simulation every step.");
                    shouldContinue = false;
                    System.exit(0);

                    break;
                case "-s", "-size":
                    if (args.length == i + 1) {
                        print("Please provide an argument for the size of the simulation!");
                        shouldContinue = false;
                        System.exit(1);

                        break;
                    }

                    SIZE = Integer.parseInt(args[i + 1]);

                    break;
                case "-q", "-quiet":
                    print("Stealthy Operation Mode (TM) has been engaged\n");
                    quietMode = true;

                    break;
            }
        }

        if (SIZE >= 50) {
            print("WARNING: The size you entered is very large! Printing the ecosystem each step will take a long time. Please consider reducing the simulation size. Stealthy Operation Mode has been automatically engaged for your convenience. (Hint: To manually enagage Stealthy Operation Mode, run the simualation with the argument '-q' or '-quiet' for silent operation.)\n");

            if (!quietMode) {
                print("Stealthy Operation Mode (TM) has been automatically engaged\n");
                quietMode = true;
            }
        }

        // Set up the tileset
        populateTileset();

        // Await input from the user
        awaitInput();
    }

    /**
     * Tick the ecosystem one step.
     * 
     * @param askInput Ask for the user's input after the tick completes. This is
     *                 used for the auto-step functionality.
     */
    private void tick(boolean askInput) {
        print("Tick: " + iteration);

        // First, randomly move the animals (50% chance to move)
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                Animal animal = tile.getContained();

                // Make sure this tile contains an animal
                if (animal == null)
                    continue;

                // 50% chance to move
                if (RandomUtils.randomBoolean()) {
                    // Choose random tile to move into
                    Tile nextTile = chooseAdjacentTile(animal.getCurrentTile());

                    animal.setNextTile(nextTile);
                } else {
                    // If it didn't move, just set the next tile to the current tile
                    animal.setNextTile(animal.getCurrentTile());
                }
            }
        }

        // Next, tick every animal (perform the logic for the animal's movement)
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                // print("Tile incoming: " + tile.getIncomingList().size());
                Animal animal = tile.getContained();

                if (animal != null && !animal.isDead()) {
                    animal.tick();
                }
            }
        }

        // All collisions should be resolved by now, so allow the remaining animal into
        // the tile.
        // Move the winning animals into their next tiles and clear the incoming animal
        // list in each tile
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.allowAnimalIntoTile();
                tile.clearIncomingList();
            }
        }

        // Finally, create new animals from the last step's reproduction.
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                Animal animal = tile.getContained();

                if (animal != null && !animal.isDead() && animal.getReproduceFlag()) {
                    createNewAnimal(animal);

                    animal.setReproduceFlag(false);
                }
            }
        }

        // Show the current tileset
        printTileset(false);

        // Increase the iteration
        iteration++;

        // Wait for input to go to next tick
        if (askInput)
            awaitInput();
    }

    /**
     * Create, populate, and print the tileset array
     */
    private void populateTileset() {
        // Initialize the tileset
        tiles = new Tile[SIZE][SIZE];

        // Populate the array
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                tiles[row][col] = populateTile(row, col);
            }
        }

        // Show the new tileset
        if (!quietMode)
            print("Initial Ecosystem Tilemap:");
        printTileset(false);
    }

    /**
     * Create a new animal at a random empty tile.
     * 
     * @param parent The new animal's parent. The new animal should be of the same
     *               type as the parent animal.
     */
    private void createNewAnimal(Animal parent) {
        Tile randomTile = randomTile(10);

        if (randomTile == null) {
            if (!quietMode)
                print("Could not find a random tile. Is the ecosystem full?");

            return;
        }

        Animal newAnimal = parent.createOffspring(randomTile);
        newAnimal.setTraits(parent);
        randomTile.setContained(newAnimal);
    }

    /**
     * Create and populate a tile at a given row and column.
     * A bear has a 10% chance to be created and a fish has a 20% chance to be
     * created.
     * 
     * @param row The row to create the tile at.
     * @param col The column to create the tile at.
     * @return The populated tile.
     */
    private Tile populateTile(int row, int col) {
        Tile tile = new Tile(row, col);

        // Populate with bear
        if (RandomUtils.randomFloat() < bearChance) {
            tile.setContained(new Bear(tile));
        }
        // Populate with fish
        else if (RandomUtils.randomFloat() < fishChance) {
            tile.setContained(new Fish(tile));
        }

        return tile;
    }

    /**
     * Waits for input to be given by the user and performs operations based on that
     * input.
     * This contains the primary user interaction logic.
     */
    private void awaitInput() {
        // Make sure input scanner is created
        if (inputScanner == null) {
            inputScanner = new Scanner(System.in);
        }

        // Print the command options to the user
        print("\nAwaiting input... (enter 'h' or 'help' for command list)"
                + (quietMode
                        ? "    Stealthy Operation Mode is engaged, enter 'p' or 'print' to force print the ecosystem."
                        : ""));

        // Wait for next input string
        String nextCommand = inputScanner.nextLine().toLowerCase();

        // Remove any digits from the command
        String commandStr = nextCommand.replace(" ", "").replace(".", "");
        for (int i = 0; i < 10; i++) {
            commandStr = commandStr.replace(i + "", "");
        }

        // Perform operations based on command
        switch (commandStr) {
            case "q", "quit":
                // Quit command sent, quit the program
                System.exit(0);

                break;
            case "h", "help":
                // Help command sent, print the help info
                printHelp();
                awaitInput();

                break;
            case "p", "print":
                // Print command sent, print the ecosystem
                printTileset(true);
                awaitInput();

                break;
            case "c", "chance":
                // Chances command sent, set the spawn chances and reset

                // Check if new chances were provided
                if (commandStr != nextCommand) {
                    String[] numStrs = nextCommand.replace(commandStr, "").replaceFirst(" ", "").split(" ");

                    // Check if they provided two arguments
                    if (numStrs.length < 2) {
                        print("Please provide two chance values for the chance command. Nothing was changed.");
                        awaitInput();
                        break;
                    }

                    try {
                        fishChance = Float.parseFloat(numStrs[0]);
                    } catch (NumberFormatException e) {
                        print("The value you provided for the new fish chance (" + numStrs[0]
                                + ") is not a number! Fish chance was not changed.");
                    }

                    try {
                        bearChance = Float.parseFloat(numStrs[1]);
                    } catch (NumberFormatException e) {
                        print("The value you provided for the new bear chance (" + numStrs[1]
                                + ") is not a number! Bear chance was not changed.");
                    }

                    // Reset the simulation
                    print("Resetting simulation with a new size of " + SIZE + "...");
                    iteration = 0;
                    lastFish = 0;
                    lastBears = 0;

                    // Populate the tileset again
                    populateTileset();
                } else {
                    print("No chances provided for the chance command. Please provide two decimal numbers separated by spaces (fish and bear chance).");
                }

                // Await input from the user
                awaitInput();
                break;
            case "r", "reset":
                // Reset command sent, recreate and repopulate the tileset

                // Check if a new size was provided
                if (commandStr != nextCommand) {
                    String numStr = nextCommand.replace(commandStr, "").replace(" ", "");

                    try {
                        SIZE = Integer.parseInt(numStr);

                        if (SIZE >= 50) {
                            print("WARNING: The size you entered is very large! Printing the ecosystem each step will take a long time. Please consider reducing the simulation size. Stealthy Operation Mode has been automatically engaged for your convenience. (Hint: To manually enagage Stealthy Operation Mode, run the simualation with the argument '-q' or '-quiet' for silent operation.)\n");

                            if (!quietMode) {
                                print("Stealthy Operation Mode (TM) has been automatically engaged\n");
                                quietMode = true;
                            }
                        } else {
                            quietMode = false;
                        }
                    } catch (NumberFormatException e) {
                        print("The value you provided for the new size is not an integer! Size was not changed.");
                    }
                }

                print("Resetting simulation with a new size of " + SIZE + "...");
                iteration = 0;
                lastFish = 0;
                lastBears = 0;

                // Populate the tileset again
                populateTileset();

                // Await input from the user
                awaitInput();

                break;
            case "a", "auto":
                // Auto step command sent, automatically step for the given number of times
                // (break out if one animal goes extinct)

                // Check if a new size was provided
                if (commandStr != nextCommand) {
                    String numStr = nextCommand.replace(commandStr, "").replace(" ", "");

                    try {
                        int steps = Integer.parseInt(numStr);

                        if (steps >= 100) {
                            print("WARNING: The number of steps you entered is very large! This may take a while!\n");
                        }

                        print("Auto-step running for " + steps + " steps...");

                        for (int i = 0; i <= steps; i++) {
                            print("\nCurrent auto-step: " + i);
                            tick(false);

                            if (lastBears <= 1 || lastFish <= 1) {
                                print("Only one animal of a species remains! Dropping out of auto-step prematurely.");
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        print("The value you provided for the number of steps is not an integer! Nothing happened.");
                    }
                } else {
                    print("Please provide a number for the number of times you wants to step the simulation.");
                }

                // Await input from the user
                awaitInput();

                break;
            case "", " ", "next":
                // Next iteration command sent, perform another tick
                tick(true);

                break;
        }
    }

    /**
     * Prints the help menu with possible commands.
     */
    private void printHelp() {
        print("""
                Possible commands:
                    'h' or 'help' - Show this menu.
                    'q' or 'quit' - Quit the program.
                    'p' or 'print' - Print the current state of the simulation, regardless of Stealthy Operation Mode.
                    'next' or no input - Iterate the ecosystem one step.
                    'a <size (integer)>' or 'auto <size (integer)>' - Enter auto-step for the given number of steps. The simulation will automatically drop out of auto-step if a species can no longer reproduce. The number of steps must be an integer.
                    'r <size (integer)>' or no 'reset <size (integer)>' - Reset the ecosystem. If you provide a size parameter then the simulation size will be set to that value. Must be an integer!
                    'c <size (decimal)> <size (decimal)>' or no 'chance <size (decimal)> <size (decimal)>' - Set the spawn chances of fish and bears to that decimal number. Between 0.0 and 1.0. Must be an number!
                """);
    }

    /**
     * Choose a random tile adjacent to the given tile.
     * 
     * @param from The target tile to choose from.
     * @return The random adjacent tile.
     */
    private Tile chooseAdjacentTile(Tile from) {
        int row = from.getRow();
        int col = from.getColumn();

        int randomChoice = RandomUtils.randomInt(0, 4);

        switch (randomChoice) {
            case 0: // up
                if (row + 1 < SIZE - 1) {
                    // print("Moved up");
                    return tiles[row + 1][col];
                }
            case 1: // down
                if (row - 1 > 0) {
                    // print("Moved down");
                    return tiles[row - 1][col];
                }
            case 2: // right
                if (col + 1 < SIZE - 1) {
                    // print("Moved right");
                    return tiles[row][col + 1];
                }
            case 3: // left
                if (col - 1 > 0) {
                    // print("Moved left");
                    return tiles[row][col - 1];
                }
        }

        // if no choice worked, return null
        return null;
    }

    /**
     * Return a random empty tile.
     * 
     * @param attempts The number of times to attempt to find a random empty tile.
     * @return A random, empty tile. Returns null if no empty tile is found.
     */
    private Tile randomTile(int attempts) {
        Tile emptyTile = null;
        int attempt = 0;

        // While no empty tile is found and we still have attempts left.
        while (emptyTile == null && attempt < attempts) {
            int randRow = RandomUtils.randomInt(0, SIZE);
            int randCol = RandomUtils.randomInt(0, SIZE);

            Tile randTile = tiles[randRow][randCol];

            // If the tile is empty
            if (randTile.getContained() == null) {
                emptyTile = randTile;
            }

            attempt++;
        }

        return emptyTile;
    }

    /**
     * Print the ecosystem tileset. Used to visualize the current state of the
     * ecosystem.
     * 
     * @param force Force printing, regardless of Quiet Mode
     */
    private void printTileset(boolean force) {
        int fishPopulation = 0;
        int bearPopulation = 0;

        double fishStrength = 0;
        double bearStrength = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String tileString = tiles[row][col].toString();

                // Check if we should be printing the tileset
                if (!quietMode || force) {
                    if (col == 0)
                        inprint("|" + tileString + "|");
                    else
                        inprint(tileString + "|");
                }

                String type = tileString.toLowerCase();

                if (type.equals("f")) {
                    // Increase the population counters
                    fishPopulation++;

                    // Sum up the strengths of the fish
                    fishStrength += tiles[row][col].getContained().getStrength();
                }

                if (type.equals("b")) {
                    // Increase the population counters
                    bearPopulation++;

                    // Sum up the strengths of the bears
                    bearStrength += tiles[row][col].getContained().getStrength();
                }
            }

            // Check if we should be printing the tileset
            if (!quietMode || force) {
                print("");
            }
        }

        int deltaFish = fishPopulation - lastFish;
        int deltaBears = bearPopulation - lastBears;

        print("Fish Population: " + fishPopulation + "  (delta of " + deltaFish + " fish)");
        print("Bear Population: " + bearPopulation + "  (delta of " + deltaBears + " bears)");

        if (fishPopulation != 0 && bearPopulation != 0) {
            fishStrength = fishStrength / fishPopulation;
            bearStrength = bearStrength / bearPopulation;

            print("Average Fish Strength: " + fishStrength);
            print("Average Fish Strength: " + bearStrength);
        }

        lastFish = fishPopulation;
        lastBears = bearPopulation;
    }
}