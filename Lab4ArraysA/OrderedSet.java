/** An interface that defines operations in an ordered set. */

public interface OrderedSet {

  public boolean isEmpty(); 
  public boolean contains(int n);
  public void insert(int n);
  public void remove(int n);
  public void sort(int alg);
  public void print();

}
