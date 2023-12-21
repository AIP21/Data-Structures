package StackADT;

import SLL.SinglyLinkedList;

public class LinkedStack<E> implements Stack<E> {
    private SinglyLinkedList<E> list;

    public LinkedStack() {
        list = new SinglyLinkedList<E>();
    }

    @Override
    public void push(E e) {
        // Add e to the start of the singly linked list
        this.list.addFirst(e);
    }

    @Override
    public E pop() {
        // Get and remove the first element of the singly linked list
        return list.removeFirst();
    }

    @Override
    public E top() {
        // Get and return the first element of the singly linked list
        return list.first();
    }

    @Override
    public int size() {
        // Get the size of the singly linked list
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        // Check if the singly linked list is empty
        return list.isEmpty();
    }

    public String toString() {
        // Return the singly linked list as a string
        return list.toString();
    }
}