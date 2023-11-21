package StackADT;

public class ArrayStack<E> implements Stack<E> {
    private static final int CAPACITY = 1000;
    private E[] data;
    private int topIndex = -1;

    /**
     * Creates a new ArrayStack object with a given capacity.
     * 
     * @param capacity
     */
    public ArrayStack(int capacity) {
        this.data = (E[]) new Object[capacity];
    }

    /**
     * Creates a new ArrayStack object with the default capacity of 1000.
     * 
     * @param capacity
     */
    public ArrayStack() {
        this(CAPACITY);
    }

    /**
     * @throws IllegalStateException If this array stack is unable to add another
     *                               element.
     */
    @Override
    public void push(E e) {
        if (size() + 1 == data.length) {
            throw new IllegalStateException("ArrayStack is already at capacity. Cannot add more elements.");
        }

        // Set the data in the array
        // Also increments the top index counter
        data[++topIndex] = e;
    }

    /**
     * @throws IllegalStateException If there is no element to pop.
     */
    @Override
    public E pop() {
        // Check if we have an element to pop
        if (isEmpty()) {
            throw new IllegalStateException("ArrayStack is empty. Cannot pop an element.");
        }

        // Get the top element
        E toPop = data[topIndex];

        // Remove from data array
        // Also decrements the top index counter
        data[topIndex--] = null;

        // Return the popped element
        return toPop;
    }

    /**
     * @throws IllegalStateException If there is no element to peek at.
     */
    @Override
    public E top() {
        // Check if we have an element to peek at
        if (isEmpty()) {
            throw new IllegalStateException("ArrayStack is empty. Cannot peek at top element.");
        }

        // Get and return the top element
        return data[topIndex];
    }

    @Override
    public int size() {
        // Return the top index + 1
        return this.topIndex + 1;
    }

    @Override
    public boolean isEmpty() {
        // Return true if the top index is 0, or the start of the array
        return this.topIndex == 0;
    }
}