package ua.yandex.shad.tries;

import static org.junit.Assert.*;
import org.junit.Test;

public class TupleTest {

    @Test
    public void testTupleGetTerm() {
        Tuple tuple = new Tuple("qwe", 80);
        assertEquals(tuple.getTerm(), "qwe");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTupleGetWhenWordIsNull() {
        Tuple tuple = new Tuple(null, 80);
    }
    
    @Test
    public void testTupleGetWhenWordIsEmpty() {
        Tuple tuple = new Tuple("", 80);
        assertEquals(tuple.getTerm(), "");
    }
    
    @Test
    public void testTupleGetWeight() {
        Tuple tuple = new Tuple("qwe", 80);
        assertEquals(tuple.getWeight(), 80);
    }
    
    @Test
    public void testTupleGetWeightWhenWeightIsZero() {
        Tuple tuple = new Tuple("qwe", 0);
        assertEquals(tuple.getWeight(), 0);
    }
    
}