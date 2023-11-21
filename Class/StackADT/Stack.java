package StackADT;

public interface Stack<E> {
    /**
     * Adds an element to the top of this stack.
     * 
     * @param e The element to add to this stack.
     */
    public void push(E e);

    /**
     * Returns the topmost element in this stack.
     * Removes the returned element from this stack.
     * 
     * @return The topmost element in this stack.
     */
    public E pop();

    /**
     * Returns the topmost element in this stack.
     * Does not remove the returned element from this stack.
     * 
     * @return The topmost element in this stack.
     */
    public E top();

    /**
     * Returns the number of elements in this stack.
     * 
     * @return The size of this stack.
     */
    public int size();

    /**
     * Returns whether or not this stack is empty.
     * 
     * @return True if this stack is empty, false otherwise.
     */
    public boolean isEmpty();
}