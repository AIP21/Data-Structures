package utils;

/**
 * CE class - Stands for Class Extended
 * 
 * Has some extra utility functions I like to use to help speed up development.
 * 
 * @author Alexander Irausquin-Petit (September 2023)
 */
public abstract class CE {

    /**
     * Alias for System.out.println();
     * 
     * @param toPrint The object to print.
     */
    public static void print(Object toPrint) {
        System.out.println(toPrint.toString());
    }

    /**
     * Alias for System.out.print();
     * Basically print but with no newline.
     * 
     * @param toPrint The object to print.
     */
    public static void inprint(Object toPrint) {
        System.out.print(toPrint.toString());
    }
}