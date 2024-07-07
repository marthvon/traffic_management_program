package CustomTypes;
import CustomTypes.CustomException.EmptyListException;

public class DoubleEndedLinkedList implements ILinkedList {
    private class ListNode {
        Object mData;
        ListNode next;

        public ListNode(Object val) {
            mData = val;
            next = null;
        }

        public ListNode(Object val, ListNode p_next) {
            mData = val;
            next = p_next;
        }
    }
    private ListNode root = null;
    private ListNode tail = null;
    private int size = 0;

    public DoubleEndedLinkedList() {}

    public boolean isEmpty() {
        return root == null;
    }

    public void insertFirst(Object val) {
        ++size;
        root = new ListNode(val, root);
        if(tail == null)
            tail = root;
    }

    public void insertLast(Object val) {
        ++size;
        if(isEmpty()) {
            tail = new ListNode(val);
            root = tail;
            return;
        }
        tail.next = new ListNode(val);
        tail = tail.next;
    }

    public void insertBefore(Object val, Object find) throws EmptyListException {
        ListNode temp = findNodeBefore(find);
        if(temp == null)
            return;
        ++size;
        temp.next = new ListNode(val, temp.next);
    }

    public Object peekFirst() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoubleEndedLinkedList");
        return root.mData;
    }

    public Object peekLast() {
        if(isEmpty())
            ; //throw
        return tail.mData;
    }

    public Object peek(Object val) throws EmptyListException {
        ListNode temp = findNode(val);
        if(temp == null)
            return null;
        return temp.next == null? null : temp.next.mData;
    }

    public Object popFirst() throws EmptyListException {
        Object temp = peekFirst();
        removeFirst();
        return temp;
    }

    public Object popLast() throws EmptyListException {
        Object temp = peekLast();
        removeLast();
        return temp;
    }
    
    public void removeFirst() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoubleEndedLinkedList");
        --size;
        if(tail == root)
            tail = null;
        root = root.next;
    }

    public void removeLast() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoubleEndedLinkedList");
        --size;
        ListNode temp = root;
        if(temp.next == null) {
            root = null;
            tail = null;
            return;
        }
        while(temp.next.next != null)
            temp = temp.next;
        temp.next = null;
        tail = temp;
    }

    public void remove(Object val) throws EmptyListException {
        ListNode temp = findNodeBefore(val);
        if(temp == null)
            return;
        --size;
        temp.next = temp.next == null? null : temp.next.next;
    }

    public boolean find(Object val) throws EmptyListException {
        return isEmpty()? false : findNode(val) != null;
    }

    public ListNode findNode(Object val) throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoubleEndedLinkedList");
        ListNode temp = root;
        do {
            if(temp.mData == val)
                return temp;
        } while((temp = temp.next) != null);
        return null;
    }

    public ListNode findNodeBefore(Object val) throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoubleEndedLinkedList");
        for(ListNode curr = root, prev = curr; curr.next != null; curr = curr.next) {
            if(curr.mData == val)
                return prev;
            prev = curr;
        }
        return null;
    }

    public void printList() {
        System.out.println("Double Ended List values:\n-------------------------");
        for(ListNode temp = root; temp != null; temp = temp.next)
            System.out.println(temp.mData);
        System.out.println("Finished...");
    }

    public int size() {
        return size;
    }
}
