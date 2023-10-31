// a client program to test the SinglyLinkedList class

import java.util.Random;

public class SLLClient2 {
    public static void main(String[] args) {
        // create an empty singly linked list to hold whole numbers
        SinglyLinkedList<Integer> numList = new SinglyLinkedList<>();

        // random object to generate random sequence of integers
        // seeded with 0 to make sure everyone has the same sequence,
        // at least initially
        Random rgen = new Random();
        if (args.length > 0) {
            rgen.setSeed(Integer.parseInt(args[0]));
        }
        int min = -5, max = 10;

        System.out.println("Initial list contents: " + numList);

        int numAdditions = 8;
        for (int i = 0; i < numAdditions / 2; i++) {
            Integer n = min + rgen.nextInt(max - min);
            System.out.println("Adding " + n + " to the front ...");
            numList.addFirst(n);
        }
        System.out.println("List contents after front additions: " + numList + "\n");
        for (int i = 0; i < numAdditions / 2; i++) {
            Integer n = min + rgen.nextInt(max - min);
            System.out.println("Adding " + n + " to the end ...");
            numList.addLast(n);
        }
        System.out.println("List contents after end additions: " + numList + "\n");

        // create a second SLL for the removed items
        SinglyLinkedList<Integer> numsRemoved = new SinglyLinkedList<>();

        int numRemovals = 5;
        for (int i = 0; i < numRemovals; i++) {
            System.out.print("Removing ");
            if (numList.first() != null) {
                Integer n = numList.removeFirst();
                System.out.print(n + " from the front ...");
                numsRemoved.addFirst(n);
            }
            System.out.println();
        }

        System.out.println("NumList after removals: " + numList);
        System.out.println("Numbers removed: " + numsRemoved);
        System.out.println();

        // create another list for all the numbers
        SinglyLinkedList<Integer> allNums = new SinglyLinkedList<>();

        // remove ALL the numbers from numList and add to the front of allNums
        while (!numList.isEmpty()) {
            allNums.addFirst(numList.removeFirst());
        }
        System.out.println("numList after removals to allNums: " + numList);
        System.out.println("allNums after getting values from numList: " + allNums);
        System.out.println();

        // remove ALL the numbers from numsRemoved and add to the end of allNums
        while (!numsRemoved.isEmpty()) {
            allNums.addLast(numsRemoved.removeFirst());
        }
        System.out.println("numsRemoved after removals to allNums: " + numsRemoved);
        System.out.println("allNums after getting values from numsRemoved: " + allNums);

        // Test concat
        SinglyLinkedList<Integer> numList1 = new SinglyLinkedList<>();

        for (int i = 0; i < numAdditions / 2; i++) {
            Integer n = min + rgen.nextInt(max - min);
            System.out.println("Adding " + n + " to the front ...");
            numList1.addFirst(n);
        }

        System.out.println("numList1 after resetting: " + numList1);

        SinglyLinkedList<Integer> numList2 = new SinglyLinkedList<>();

        for (int i = 0; i < numAdditions / 2; i++) {
            Integer n = min + rgen.nextInt(max - min);
            System.out.println("Adding " + n + " to the front ...");
            numList2.addFirst(n);
        }

        System.out.println("numList2 after resetting: " + numList2);

        SinglyLinkedList<Integer> concatenated = numList1.concat(numList2);

        System.out.println("after concatenating: " + concatenated + "   size: " + concatenated.size());

        // Test reverse
        concatenated.reverse();

        System.out.println("after reversing: " + concatenated + "   size: " + concatenated.size());
    }
}