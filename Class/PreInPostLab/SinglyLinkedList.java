/**
  * implements a singly-linked list data type
  * containing data of type E
  */

public class SinglyLinkedList<E>
{
  // private internal node class for data items
  private static class Node<E>
  {
    private Node<E> next;
    private E data;

    public Node(E e, Node<E> n)
    {
      next = n;
      data = e;
    }
    public E getData(){ return data; }
    public Node<E> getNext(){ return next; }
    public void setNext(Node<E> n){ next = n; }
  }

  private Node<E> head;
  private Node<E> tail;
  private int size;

  /** creates an empty SLL */
  public SinglyLinkedList(){}

  /**
    * returns the number of elements in the list
    *
    * @return the size of the list
    */
  public int getSize(){ return size; }

  /**
    * returns true if the list is empty, false o/w
    *
    * @return true if list is empty and false otherwise
    */
  public boolean isEmpty(){ return size == 0; }

  /**
    * returns the data at the head of the list,
    * null if the list is empty
    *
    * @return the data at the head of the list, null if empty
    */
  public E first()
  {
    if(isEmpty()){ return null; }
    else{ return head.getData(); }
  }

  /**
    * returns the last piece of data in the list,
    * null if empty
    *
    * @return the last data in the list, null if empty
    */
  public E last()
  {
    if(isEmpty()){ return null; }
    else{ return tail.getData(); }
  }

  /**
    * inserts a new element at the head of the list
    *
    * @param e the data for the new node
    */
  public void addFirst(E e)
  {
    head = new Node<>(e, head);
    if(size == 0){ tail = head; }
    size++;
  }

  /**
    * inserts a new element at the tail of the list
    *
    * @param e the data being inserted at the tail
    */
  public void addLast(E e)
  {
    Node<E> temp = new Node<>(e, null);
    if(size == 0){ head = temp; }
    else{ tail.setNext(temp); }
    tail = temp;
    size++;
  }

  /**
    * removes the first element and returns it
    *
    * @return the data removed from the head of the list
    */
  public E removeFirst()
  {
    if(isEmpty())
    { return null; }
    E result = head.getData();
    head = head.getNext();
    size--;
    if(size == 0){ tail = null; }
    return result;
  }
}
