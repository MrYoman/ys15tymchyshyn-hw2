package ua.yandex.shad.collections;

import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.InputMismatchException;

public class QueueTest {
        
    @Test
    public void testEnqueueBySize() {
        Queue<String> queue = new Queue<String>();
        queue.enqueue("mml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        int expResult = 3;
        int result    = queue.size();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testEnqueueByDequeue() {
        Queue<String> queue = new Queue<String>();
        queue.enqueue("mml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        String expResult = "mml";
        String result;
        result = queue.dequeue();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testDequeueWhenIsNotEmpty() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        String expResult = "jdjml";
        queue.dequeue();
        String result = queue.dequeue();
        
        assertEquals(result, expResult);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testDequeueWhenIsEmpty() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }
    
    @Test
    public void testGetFirstItem() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        String expResult = "mpinklmlml";
        String result = queue.getFirstItem();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testIsEmptyWhenAlwaysWasEmpty() {
        Queue<String> queue = new Queue<String>();
        
        boolean expResult = true;
        boolean result    = queue.isEmpty();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testIsEmptyWhenWasNotAlwaysEmpty() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        
        boolean expResult = true;
        boolean result    = queue.isEmpty();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testIsEmptyWhenNotEmpty() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        queue.dequeue();
        queue.dequeue();
        
        boolean expResult = false;
        boolean result    = queue.isEmpty();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testIteratorHasNextTrue() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        Iterator<String> iter = queue.iterator();
        
        boolean expResult = true;
        boolean result    = iter.hasNext();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIteratorHasNextFalse() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        Iterator<String> iter = queue.iterator();
        iter.next();
        iter.next();
        iter.next();
        
        boolean expResult = false;
        boolean result    = iter.hasNext();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIteratorNextWhenNextExists() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        Iterator<String> iter = queue.iterator();
        iter.next();
        
        String expResult = "jdjml";
        String result    = iter.next();
        
        assertEquals(expResult, result);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIteratorNextWhenNextDoesNotExist() {
        Queue<String> queue = new Queue<String>();
        
        queue.enqueue("mpinklmlml");
        queue.enqueue("jdjml");
        queue.enqueue("lsdbl");
        
        Iterator<String> iter = queue.iterator();
        iter.next();
        iter.next();
        iter.next();
      
        iter.next();
    }
    
}