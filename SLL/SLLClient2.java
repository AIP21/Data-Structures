// a client program to test the SinglyLinkedList class

import java.util.Random;

public class SLLClient2 {
    
    public static void main(String[] args) {
         // create three empty singly linked lists to hold whole numbers
         SinglyLinkedList<Integer> nums0 = new SinglyLinkedList<>();
         SinglyLinkedList<Integer> nums1 = new SinglyLinkedList<>();
         SinglyLinkedList<Integer> nums2 = new SinglyLinkedList<>();

         // random object to generate random sequence of integers
         // along with the ability to seed the generator from a 
         // command line argument if so desired.
         Random rgen = new Random();
         if( args.length > 0 ){
             rgen.setSeed(Integer.parseInt(args[0]));
         }
         int min = -5, max = 6, numElements = 10;
         
         // display initially empty lists
         System.out.println("Initial list contents: nums1 --> " + nums1 + " and nums2 --> " + nums2);

         // populate both lists with radnom values in [min,max)
        for(int i = 0; i < numElements; i++){
            Integer val = rgen.nextInt(min, max);
            nums0.addFirst(val);
            nums1.addFirst(val);
        }
        for(int i = 0; i < numElements/2; i++){
            nums2.addFirst(rgen.nextInt(min, max));
        }

        // display both lists
        System.out.println("After filling: \nnums0 --> " + nums0 + "\nnums1 --> " + nums1 + "\nnums2 --> " + nums2);

        // create a new list and set it to nums1 concatenated to nums2
        // and a new list set to nums2 concatenated to nums1
        // then display them
        SinglyLinkedList<Integer> nums12 = nums1.concat(nums2);
        SinglyLinkedList<Integer> nums21 = nums2.concat(nums1);
        System.out.println("nums1 + nums2 --> " + nums12);
        System.out.println("nums2 + nums1 --> " + nums21);

        // reverse nums1 and display
        nums1.reverse();
        System.out.println("nums1 reversed: \nnums1 --> " + nums1);
        System.out.println("nums0 --> " + nums0);
        // compare nums1 to nums0 for inequality
        if( !nums1.equals(nums0) ){
            System.out.println("nums0 and nums1 are not equal");
        }

        // reverse nums1 again and then compare for equality
        nums0.reverse();
        System.out.println("nums1 --> " + nums1);
        System.out.println("nums0 --> " + nums0);
        if( nums1.equals(nums0) ){
            System.out.println("nums0 and nums1 are equal");
        }
    }

}
