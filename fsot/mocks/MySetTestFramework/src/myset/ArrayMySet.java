package myset;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SQ
 */
public class ArrayMySet extends AbstractMySet {

    private ArrayList contents;
    private int maximumSize;

    public ArrayMySet() throws MySetException {
        this(MAX_SIZE);
    }

    public ArrayMySet(int maximumSize) throws MySetException {
        checkSize(maximumSize);
        initialiseToEmpty(maximumSize);
    }

    @SuppressWarnings(value = "unchecked")
    public void add(Object obj) throws MySetException {
        checkForSpace();
        if (!contents.contains(obj)) {
            contents.add(obj);
        }
    }

    private void checkForSpace()
            throws MySetException {
        if (maximumSize == contents.size()) {
            throw new MySetException("Attempting to add to full MySet");
        }
    }

    public boolean contains(Object obj) {
        return contents.contains(obj);
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public void remove(Object obj) {
        contents.remove(obj);
    }

    public int size() {
        return contents.size();
    }

    public Iterator iterator() {
        return contents.iterator();
    }

    private void initialiseToEmpty(int maximumSize) {
        this.maximumSize = maximumSize;
        contents = new ArrayList();
    }
}
