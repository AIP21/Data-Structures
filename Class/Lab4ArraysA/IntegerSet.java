import java.util.Random;

/**
 * This class implements an integer set that supports six operations, namely,
 * testing emptiness, membership, insertion, removal, sorting, and printing.
 */
public class IntegerSet implements OrderedSet {
  public static int MAX_SIZE = 1000; // Maximum capacity

  private int elements[];
  private int size; // The actual size of the array

  /**
   * Creates an array of MAX_SIZE capacity and initializes the set's size to
   * 0 (i.e., it creates an empty set).
   */
  public IntegerSet() {
    elements = new int[MAX_SIZE];
    size = 0;
  }

  /**
   * Returns true if the set contains no element.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns true if this set contains the integer n.
   * 
   * @param n The integer to check for
   */
  public boolean contains(int n) {
    // Check every element in the array
    for (int i = 0; i < size; i++) {

      // Check if the number in the array equals the target number (n)
      if (elements[i] == n) {
        // If they are equal then this set contains the target number (n), return true
        return true;
      }
    }

    // If we reached here, that means we found no matches and never returned true,
    // so return false
    return false;
  }

  /**
   * Returns true if this set is sorted from least to greatest.
   * Otherwise it returns false.
   * 
   * @return Whether or not this set is sorted in ascending order
   */
  public boolean isSorted() {
    // Loop through every item in the array (except for the last item)
    // CHeck if the item above it is less than the current item
    // If so, then we're not sorted
    for (int i = 0; i < size - 1; i++) {
      if (elements[i] > elements[i + 1]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Inserts an integer n into the array elements[] and increments size by 1.
   * 
   * @param n The integer to insert
   */
  public void insert(int n) {
    System.out.println("Inserting " + n + "...");

    // If n is already in the set, then there is no need to add it.
    if (contains(n)) {
      return;
    }

    // Make sure we don't overflow the array.
    // If adding n would overflow the array
    // then increase the size by 50%
    // and then insert n
    if (size + 1 > MAX_SIZE) {
      // Increase the size of the array by 50%
      MAX_SIZE = (int) (MAX_SIZE * 1.5);
      int[] newTemp = new int[MAX_SIZE];

      // Copy over the array contents
      for (int i = 0; i < size; i++) {
        newTemp[i] = elements[i];
      }

      // Set elements to newTemp
      elements = newTemp;
    }

    // Set the value at index 'size' of the array to n
    // We use size because it is an easy way to get the next empty index of the
    // array (since arrays start at index 0)
    elements[size] = n;

    // Increment size by one
    size++;
  }

  /**
   * Removes an integer n from the array elements[] and decrements size by 1.
   * 
   * @param n The integer to remove
   */
  public void remove(int n) {
    System.out.println("Removing " + n + "...");

    // If the array doesn't contain n, don't do anything
    // since there is nothing to remove
    if (!contains(n)) {
      return;
    }

    // Flag for when we need to cascade the elements down one index
    boolean cascading = false;

    // Starting at the first index, iterate through the array until we find the
    // integer to remove
    for (int i = 0; i < size; i++) {
      // We have not found the number to remove yet, keep checking
      if (!cascading) {
        // Test for match
        if (elements[i] == n) {
          // We found a match, start cascading every element down
          cascading = true;
        }
      }

      // If we are cascading, then set the element at the current index to the value
      // at the index above the current index
      if (cascading) {
        // If we are at the last element of the array, so set it to 0
        // This is done to make sure we don't error out if we're
        // at the last element of the array
        if (i == MAX_SIZE - 2) {
          elements[i] = 0;
        } else {
          elements[i] = elements[i + 1];
        }
      }
    }

    // Decrement size by one
    size--;
  }

  /**
   * Perform a quick sort on the set
   */
  public void quickSort() {
    // shuffle the array to mitigate possibility of worst case
    shuffle();
    // call the sort on the entire set
    quickSort(0, size - 1);
  }

  // Shuffle every element in the array elements[]
  private void shuffle() {
    Random rand = new Random();

    // For every element in the array,
    // swap it with another element at a random index
    for (int i = 0; i < size; i++) {
      int temp = elements[i];
      int randomIndex = rand.nextInt(size);

      elements[i] = elements[randomIndex];
      elements[randomIndex] = temp;
    }
  }

  // sorts the range [first, last] in integer array elements[]
  // in increasing order via the quick sort algorithm
  private void quickSort(int first, int last) {
    if (last > first) {
      // Partition the left and right side
      int p = partition(first, last);

      quickSort(first, p - 1); // Run quicksort on the left side
      quickSort(p + 1, last); // Run quicksort on the right side
    }
  }

  // Modifies the elements of the array so
  // that the items are rearranged into a half
  // that consists of values less than some pivot value,
  // and another half that are greater than or equal
  // to the pivot value, then places the pivot value
  // between the two halves, and returns the index
  // where the pivot value was placed.
  private int partition(int first, int last) {
    int pivot = elements[first]; // The value at the pivot index, just the first element
    int left = first + 1; // The starting index of this partition
    int right = last; // The ending index of this partition

    while (left <= right) {
      // While the value at left is less than or equal to pivot
      // and left is not equal to right,
      // increase left by one,
      // moving onto the next element in the left side
      // If this check fails, that means the value in
      // the left side is GREATER than pivot,
      // and should be moved to the right side
      while (elements[left] <= pivot && left <= right) {
        left++;
      }

      // While the value at right is greater than pivot
      // and left is not equal to right,
      // decrease right by one,
      // moving onto the prior element in the right side
      // If this check fails, that means the value in
      // the right side is LESS than pivot,
      // and should be moved to the left side
      while (elements[right] > pivot && left <= right) {
        right--;
      }

      // Make sure left is still less than right (fixed a bug)
      if (left < right) {
        // Swap the values at left and right
        // At this point, the left index should refer to a value that is greater than pivot
        // At this point, the right index should refer to a value that is less than pivot
        int temp = elements[left];
        elements[left] = elements[right];
        elements[right] = temp;
      }
    }

    // Place the pivot value between the two sides
    int temp = elements[right];
    elements[right] = pivot;
    elements[first] = temp;

    return right;
  }

  /**
   * Sorts the integer of the array elements[] in the increasing order
   * via the algorithm specified by parameter whichAlgorithm as follows:
   * 0 - selection sort
   * 1 - insertion sort
   * 
   * @param whichAlgorithm The selector for the sorting algorithm to be used
   */
  public void sort(int whichAlgorithm) {
    System.out.println("Sorting...");

    if (whichAlgorithm == 0) {
      selectionSort();
    } else if (whichAlgorithm == 1) {
      insertionSort();
    }
  }

  // sorts the integer array elements[] in increasing order via
  // the selection sort algorithm
  private void selectionSort() {
    // Go through every value in the array from 0 to size - 2
    for (int i = 0; i < size - 1; i++) {
      int lowestIndex = i;

      // Go through every value in the array starting at i + 1 and ending at size - 1
      for (int k = i + 1; k < size; k++) {
        // If the element at k is lower than the current lowest number
        if (elements[k] < elements[lowestIndex]) {
          lowestIndex = k;
        }
      }

      // Store the lowest value in the first index at this stage (i) by swapping them
      int temp = elements[lowestIndex];
      elements[lowestIndex] = elements[i];
      elements[i] = temp;
    }

    /**
     * Pseudocode:
     * Input: An array A of N comparable elements.
     * Output: The array A with elements rearranged in nondecreasing order.
     * 
     * for i from 1 to N - 2 do the following:
     * ... Temporarily store the index of the lowest value of A
     * ... in the range i + 1 to N - 1
     * ... Swap the element at the stored index with the element at index i
     */
  }

  // sorts the integer array elements[] in increasing order via
  // the insertion sort algorithm
  private void insertionSort() {
    // Go through every value in the array from index i to size - 1
    for (int i = 1; i < size; i++) {
      int temp = elements[i];

      // Stores the index to insert the temp value
      int j = i - 1;

      // Move the elements greater than the temp value
      // and between index 0 and i - 1 up by one index
      // This is done to make an empty space to insert the value at i
      while (j >= 0 && elements[j] > temp) {
        // Move the element up by one index
        elements[j + 1] = elements[j];

        // Decrement j by one
        j--;
      }

      // Insert the value at j + 1 into its proper position
      elements[j + 1] = temp;
    }

    /*
     * Pseudocode:
     * Input: An array A of N comparable elements
     * Output: The array A with elements rearranged in nondecreasing order
     * 
     * for i from 1 to N - 1 do the following:
     * ... Temporarily store the value of A at i
     * ... Move the values of A in the range 0 to N - 1 and greater than the temp
     * ... value up by one index (this is done to make an empty space in A)
     * ... Insert the temp value into A at the index that was just opened up
     */
  }

  /**
   * Prints all the elements of the array elements[]. Note that it uses
   * the size as the loop bound.
   */
  public void print() {
    for (int i = 0; i < size; i++)
      System.out.print(elements[i] + " ");
    System.out.println();
  }
}