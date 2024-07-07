package CustomTypes;
import CustomTypes.CustomException.EmptyListException;

public interface ILinkedList {
    public boolean isEmpty();
    public void insertFirst(Object val);
    public void insertLast(Object val);
    public void insertBefore(Object val, Object find) throws EmptyListException;
    public Object peekFirst() throws EmptyListException;
    public Object peekLast() throws EmptyListException;
    public Object peek(Object val) throws EmptyListException;
    public void removeFirst() throws EmptyListException;
    public void removeLast() throws EmptyListException;
    public void remove(Object val) throws EmptyListException;
    public boolean find(Object val) throws EmptyListException;
    public void printList();
    public int size();
}
