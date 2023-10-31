/**
 * A singly linked list.
 * This is a truly dynamic array that grows and shrinks its memory size as you
 * add and remove elements.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public class SinglyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int length;

    // Default constructor
    public SinglyLinkedList() {

    }

    /**
     * Returns the size of the list.
     * 
     * @return The number of elements in the list.
     */
    public int size() {
        return length;
    }

    /**
     * Returns whether or not there are no elements in the list.
     * 
     * @return True if the list is empty, otherwise false.
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * Returns the first element in the list.
     * Returns null if the list is empty.
     * 
     * @return The first element.
     */
    public E first() {
        if (isEmpty()) {
            return null;
        }

        // No need to null check here
        return head.getElement();
    }

    /**
     * Returns the last element in the list.
     * Returns null if the list is empty.
     * 
     * @return The last element.
     */
    public E last() {
        if (isEmpty()) {
            return null;
        }

        // No need to null check here
        return tail.getElement();
    }

    /**
     * Add an element to the start of the list.
     * Returns null if the list is empty.
     * 
     * @param data The element to add.
     */
    public void addFirst(E data) {
        // Create a new node
        Node<E> newNode = new Node<E>(data, head);

        // Set new head
        head = newNode;

        // Make sure tail is referenced if this is the first ever node
        if (length == 0) {
            tail = newNode;
        }

        // Increase the length
        length++;
    }

    /**
     * Add an element to the end of the list.
     * Returns null if the list is empty.
     * 
     * @param data The element to add.
     */
    public void addLast(E data) {
        // Create a new node
        Node<E> newNode = new Node<E>(data, null);

        // Set current tail's next node to the new node
        if (tail != null) {
            tail.setNext(newNode);
        }

        // Set tail to the new ndoe
        tail = newNode;

        // Make sure head is referenced if this is the first ever node
        if (length == 0) {
            head = newNode;
        }

        // Increase the length
        length++;
    }

    /**
     * Remove the first element in the list and return it.
     * Returns null if the list is empty.
     * 
     * @return The element that was removed, or null if the list is empty.
     */
    public E removeFirst() {
        // If the list is empty then return null
        if (length == 0) {
            return null;
        }

        // Get the first (head) element
        Node<E> first = head;

        // Set the head to the first element's next node
        // This has the effect of removing it from the list
        head = head.getNext();

        // Decrease the length
        length--;

        // If this was the last node, make sure tail also
        // no longer references the removed node
        if (length == 0) {
            tail = null;
        }

        // Return the removed element's data
        return first.getElement();
    }

    /**
     * Reverses the order of the elements in the list.
     * In-place operation.
     */
    public void reverse() {
        // This is in place because elements are removed
        // from one list and added to the other one

        SinglyLinkedList<E> reversed = new SinglyLinkedList<>();

        // Swap over the elements in this lsit to the reversed list
        // in a reverse order
        while (!this.isEmpty()) {
            reversed.addFirst(this.removeFirst());
        }

        // Copy the elements in reversed back into this list
        while (!reversed.isEmpty()) {
            this.addLast(reversed.removeFirst());
        }
    }

    /**
     * This method creates and returns
     * an entirely new list that is the result of concatenating the elements of
     * other onto the end of this list.
     * Leaves both lists unmodified.
     * 
     * @param other The other list to concatenate onto the end of this list.
     * @return The concatenated list. Null if the list types are not the same.
     */
    public SinglyLinkedList<E> concat(SinglyLinkedList<E> other) {
        // Make sure the types are the same
        if (!this.getClass().equals(other.getClass())) {
            return null;
        }

        // Create a new list
        SinglyLinkedList<E> newList = new SinglyLinkedList<E>();

        Node<E> currentNode = this.head;

        // Go through every value in this list and COPY the node over
        while (currentNode != null) {
            newList.addLast(currentNode.getElement());

            currentNode = currentNode.getNext();
        }

        currentNode = other.head;

        // Go through every value in the other list and COPY the node over
        while (currentNode != null) {
            newList.addLast(currentNode.getElement());

            currentNode = currentNode.getNext();
        }

        // Return the new list
        return newList;
    }

    /**
     * Whether or not every element in this list is equal
     * to every value in another list, in the same order.
     * Returns false if other is null.
     * 
     * @param other The list to compare to
     * @return True if this list is equal to the other list, otherwise false.
     */
    public boolean equals(SinglyLinkedList<E> other) {
        // If other is null, return false
        if (other == null) {
            return false;
        }

        // If the classes are different, return false
        if (other.getClass() != this.getClass()) {
            return false;
        }

        SinglyLinkedList<E> otherList = (SinglyLinkedList<E>) other;

        // If the lengths are different, return false
        if (otherList.size() != this.size()) {
            return false;
        }

        Node<E> a = this.head;
        Node<E> b = other.head;

        // Check all the elements to make sure they are equal
        while (a != null) {
            // If the elements are different, return false
            if (!a.getElement().equals(b.getElement())) {
                return false;
            }

            a = a.getNext();
            b = b.getNext();
        }

        // If we've reached here, that means both lists
        // are equal so return true
        return true;
    }

    /**
     * Calls the toString method of each piece of data contained by this list.
     * 
     * @return A string representing the list.
     */
    @Override
    public String toString() {
        // Current node
        Node<E> node = head;

        if (node == null) {
            return "[]";
        }

        // String builder
        StringBuilder builder = new StringBuilder("[");

        // Append the value of the data's toString for each node
        while (node.getNext() != null) {
            builder.append(node.getElement().toString() + ", ");

            node = node.getNext();
        }

        if (node != null) {
            builder.append(node.getElement().toString());
        }

        builder.append("]");

        return builder.toString();
    }

    // Nested node class
    private static class Node<E> {
        private E element;
        private Node<E> next;

        // Constructor
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Getters
        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        // Setters
        public void setNext(Node<E> n) {
            next = n;
        }
    }
}