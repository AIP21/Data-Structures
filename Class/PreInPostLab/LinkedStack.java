/**
  * Implements a stack data structure using
  * a linked-list as the underlying data-structure.
  */

public class LinkedStack<E> implements Stack<E>
{
  // the data itself
  private SinglyLinkedList<E> stack;

  /**
    * constructs an empty stack
    */
  public LinkedStack()
  { stack = new SinglyLinkedList<>(); }

  /**
    * returns the number of objects in the stack
    *
    * @return the number of items in the stack
    */
  @Override
  public int size(){ return stack.getSize(); }

  /**
    * returns true if the stack is empty and false o/w
    *
    * @return true if stack is empty, false otherwise
    */
  @Override
  public boolean isEmpty(){ return stack.isEmpty(); }

  /**
    * pushes element e onto the stack
    *
    * @param e the object being added to the stack
    */
  @Override
  public void push(E e){ stack.addFirst(e); }

  /**
    * returns the item at the top of the stack,
    * null if stack is empty.
    *
    * @return item at top of stack, null if empty
    */
  @Override
  public E top(){ return stack.first(); }

  /**
    * removes and returns the top element in
    * the stack (null if empty)
    *
    * @return removes and returns top element (null if empty)
    */
  @Override
  public E pop()
  { return stack.removeFirst(); }
}
