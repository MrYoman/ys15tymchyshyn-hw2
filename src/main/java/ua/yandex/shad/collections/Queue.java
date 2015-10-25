package ua.yandex.shad.collections;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    
    private Node first; 
    private Node last; 
    private int size;  
    
    private class Node {
        Item item;
        Node next;
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
            throw new IndexOutOfBoundsException();
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
        private Node current = first;
        public boolean hasNext() { 
            return current != null; 
        }
        public void remove() { }
        public Item next() {
            if (current == null) {
                throw new IndexOutOfBoundsException();
            }
            Item item = current.item;
            current = current.next; 
            return item;
        }
    } 

}