package ua.yandex.shad.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    
    private Node first = null; 
    private Node last  = null; 
    private int size;  
    
    private class Node {
        private Item item;
        private Node next;
    }
    
    public boolean isEmpty() {
        return first == null;
    } 
    
    public int size() {
        return size; 
    }
    
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        size++;
    }
    
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        size--;
        return item;
    }
    
    public Item getFirstItem() {
        return first.item;
    }
    
    public Iterator<Item> iterator() { 
        return new ListIterator(); 
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current     = first;
        private Node previous    = null;
        private Node prePrevious = null;
        
        public boolean hasNext() { 
            return current != null; 
        }
        
        public void remove() {
            if (previous != null) {
                if (prePrevious != null) {
                    prePrevious.next = current;
                }
                else {
                    first = current;
                }
                previous.next = null;
                previous = prePrevious;
            }
            else {
                throw new NoSuchElementException();
            }
        }
        
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item   = current.item;
            prePrevious = previous;
            previous    = current;
            current     = current.next; 
            return item;
        }
    } 

}