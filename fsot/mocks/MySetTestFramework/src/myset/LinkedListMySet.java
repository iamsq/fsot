package myset;

import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SQ
 */
public class LinkedListMySet extends AbstractMySet {

    private Node head;
    private int currentSize;
    private int maximumSize;

    public LinkedListMySet() throws MySetException {
        this(MAX_SIZE);
    }

    public LinkedListMySet(int maximumSize) throws MySetException {
        checkSize(maximumSize);
        initialiseToEmpty(maximumSize);
    }

    private void initialiseToEmpty(int maximumSize) {
        this.maximumSize = maximumSize;
        head = null;
        currentSize = 0;
    }

    public void add(Object obj) throws MySetException {
        checkForSpace();
        if (!contains(obj)) {
            Node newNode = new Node(obj, head);
            head = newNode;
            currentSize++;
        }
    }

    private void checkForSpace()
            throws MySetException {
        if (maximumSize == currentSize) {
            throw new MySetException("Attempting to add to full MySet");
        }
    }

    public boolean contains(Object obj) {        
        for (Object value : this) {
            if (value.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return (currentSize == 0);
    }

    public void remove(Object obj) {
        Node previousNode = null;
        Node currentNode = head;

        do {
            if (currentNode.value.equals(obj)) {
                if (previousNode == null) { //head is the node to be removed
                    head = head.next; //reassign head                    
                } else {
                    previousNode.next = currentNode.next;                    
                }
                currentSize--;
                break;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        } while (currentNode != null);
    }

    public int size() {
        return currentSize;
    }

    public Iterator iterator() {
        return new LinkedListMySetIterator();
    }

    private static class Node {

        public Object value;
        public Node next;

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private class LinkedListMySetIterator implements Iterator {

        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Object next() {
            Object next = null;
            if (current != null) {
                next = current.value;
                current = current.next;
            }
            return next;
        }

        public void remove() {
            // do nothing
        }
    }
}
