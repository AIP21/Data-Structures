import java.util.Random;

/** Test program for the IntegerSet class */

public class Tester {

  public static void main(String args[]) {
    int sortAlgorithmToUse = Integer.parseInt(args[0]);
    if (!(0 <= sortAlgorithmToUse && sortAlgorithmToUse <= 1)) {
      sortAlgorithmToUse = 0; // default is selection sort
    }

    int x = 16;
    int y = 11;

    IntegerSet A = new IntegerSet();
    A.insert(x); // The integer x is inserted into A so that
                 // A = {16}

    System.out.print("The set A = ");
    A.print();

    A.insert(y); // The integer y is inserted into A so that
                 // A = {16, 11}

    A.insert(23); // The integer 23 is inserted into A so that
                  // A = {16, 11, 23}

    System.out.print("The set A = ");
    A.print();

    A.insert(11); // The integer 11 is inserted into A but A
                  // should remain unchanged.

    System.out.print("The set A = ");
    A.print();

    System.out.println("A.contains(23) = " + A.contains(23));

    A.remove(11); // The integer 11 is removed from A so that
                  // A = {16, 23}

    System.out.print("The set A = ");
    A.print();

    System.out.println("A.contains(11) = " + A.contains(11));

    IntegerSet B = new IntegerSet();

    for (int i = 0; i < 20; i++)
      B.insert(19 - i);

    B.remove(18);
    B.insert(55);

    System.out.print("The set B = ");
    B.print();

    B.sort(sortAlgorithmToUse); // The integers of B are now sorted.

    System.out.print("The set B = ");
    B.print();

    // Repopulate B with too many elements to test the overflow fix
    B = new IntegerSet();

    for (int i = 0; i < 1000; i++) {
      B.insert(999 - i);
    }

    B.insert(1000);

    B.print();

    // Repopulate B again (so its smaller),
    // quicksort it, and then check its correctness
    B = new IntegerSet();

    Random rand = new Random();
    for (int i = 0; i < 20; i++) {
      B.insert(rand.nextInt(100));
    }

    B.quickSort();

    B.print();

    System.out.println("B is " + (B.isSorted() ? "sorted" : "NOT sorted"));
  }
}