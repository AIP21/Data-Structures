/**
 * A stack is a collection of things, objects that are
 * inserted and removed according to the last-in first-out
 * (LIFO) principle.
 */

public interface Stack<E>
{
  /**
   * Returns the number of elements in the stack.
   *
   * @return number of elements in the stack
   */
  int size();

  /**
   * Tests whether the stack is empty.
   *
   * @return true if the stack is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Inserts an element at the top of the stack.
   *
   * @param element   the element to be inserted
   */
  void push(E element);

  /**
   * Returns, but does not remove, the element at
   * the top of the stack.
   *
   * @return top element in the stack (or null if empty)
   */
  E top();

  /**
   * Removes and returns the top element from the stack.
   *
   * @return element removed (or null if empty)
   */
  E pop();
}
