package CustomTypes;

import CustomTypes.CustomException.EmptyListException;

public class DoublyLinkedList implements ILinkedList {
    public class ListNode {
        Object mData;
        ListNode next = null;
        ListNode prev = null;

        public ListNode(Object val) {
            mData = val;
            next = null;
            prev = null;
        }

        public ListNode(Object val, ListNode p_next) {
            mData = val;
            next = p_next;
            prev = null;
        }

        public ListNode(Object val, ListNode p_next, ListNode p_prev) {
            mData = val;
            next = p_next;
            prev = p_prev;
        }
    }
    public ListNode root = null;
    private ListNode tail = null;
    private int size = 0;

    public DoublyLinkedList() {}

    public boolean isEmpty() {
        return root == null;
    }

    public void insertFirst(Object val) {
        ++size;
        if(isEmpty()) {
            root = new ListNode(val);
            tail = root;
            return;
        }
        root = new ListNode(val, root);
        root.next.prev = root;
    }

    public void insertLast(Object val) {
        ++size;
        if(isEmpty()) {
            tail = new ListNode(val);
            root = tail;
            return;
        }
        tail = new ListNode(val, null, tail);
        tail.prev.next = tail;
    }

    public void insertBefore(Object el, Object find) throws EmptyListException{
        ListNode temp = findNode(find);
        if(temp == null)
            return;
        ++size;
        ListNode res = new ListNode(el, temp, temp.prev);
        if(temp.prev != null)
            temp.prev.next = res;
        else
            root = res;
        temp.prev = res;
    }

    public Object peekFirst() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoublyLinkedList");
        return root.mData;
    }

    public Object peekLast() throws EmptyListException{
        if(isEmpty())
            throw new EmptyListException("DoublyLinkedList");
        return tail.mData;
    }

    public Object peek(Object val) throws EmptyListException {
        ListNode temp = findNode(val);
        if(temp == null)
            return null;
        return temp.next == null? null : temp.next.mData;
    }

    public void removeFirst() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoublyLinkedList");
        --size;
        if(root == tail)
            tail = null;
        else
            root.next.prev = null;
        root = root.next;
    }

    public void removeLast() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoublyLinkedList");
        --size;
        if(root == tail)
            root = null;
        else
            tail.prev.next = null;
        tail = tail.prev;
    }

    public void remove(Object val) throws EmptyListException {
        ListNode temp = findNode(val);
        if(temp == null)
            return;
        --size;

        if(temp == root)
            root = temp.next;
        else
            temp.prev.next = temp.next;

        if (temp == tail)
            tail = temp.prev;
        else
            temp.next.prev = temp.prev;
    }

    public void removeNode(ListNode node) {
        if(node == null)
            return;
        --size;

        if(node == root)
            root = node.next;
        else
            node.prev.next = node.next;

        if (node == tail)
            tail = node.prev;
        else
            node.next.prev = node.prev;
    }
    
    public void printList() {
        System.out.println("Doubly Linked List values:\n--------------------------");
        for(ListNode temp = root; temp != null; temp = temp.next)
            System.out.println(temp.mData);
        System.out.println("Finished...");
    }

    public int size() {
        return size;
    }

    public boolean find(Object val) throws EmptyListException {
        return isEmpty()? false : findNode(val) != null;
    }

    public ListNode findNode(Object val) throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException("DoublyLinkedList");
        ListNode temp = root;
        do {
            if(temp.mData == val)
                return temp;
        } while((temp = temp.next) != null);
        return null;
    }
}
