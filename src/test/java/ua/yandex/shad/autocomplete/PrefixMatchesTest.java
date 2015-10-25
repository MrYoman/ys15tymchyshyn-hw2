
package ua.yandex.shad.autocomplete;

import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.collections.Queue;
import java.util.Iterator;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.tries.Tuple;
 
@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {

    @Test
    public void testAddWhenStringsAreEmptyAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 0;
        
        int result = prefixMatches.add();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenStringsHaveNoSpaceAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 4;
        
        int result = prefixMatches.add("qwe", "vgyx", "edw", "qsffgnjvd");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenStringsHaveSpacesAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 6;
        
        int result = prefixMatches.add("qkfe wehi", "iijv g oi",
                                        "edw", "qsf jvnd");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenIsOnlyOneWordAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 1;
        
        int result = prefixMatches.add("luyfjyxmhfchshd");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenIsOnlyOneWordWithSpacesAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 4;
        
        int result = prefixMatches.add("luy fj yxmh f ch      shd     hgj");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenIsStringConsistedOfSpacesAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 3;
        
        int result = prefixMatches.add("        ", "hui", "    ", "  ",
                                       "jjkbkjbkjbkjb", "jhvufutc");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenAllWordsAreSmallerThanTwoAndDictionaryIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 0;
        
        int result = prefixMatches.add("        ", "hi", "    ", "  ",
                                       "j kb kj   bk jb k jb", "jh vu c");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenThereAreSpacesInTheEndOfStringAndDictIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 2;
        
        int result = prefixMatches.add("ydyt    ", "njkn      ", "j     ");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenThereAreSpacesInTheStartOfStringAndDictIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int  expResult = 2;
        
        int result = prefixMatches.add("    ydyt", "     njkn", "    j");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testAddWhenDictionaryIsNotEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        prefixMatches.add("pon  ydyt", "knkln   njkn", "ojpe  j");
        
        int  expResult = 9;
        
        int result = prefixMatches.add("po n  yd yt", "knk  klnlln  klnnnjkn",
                                                    "ojpeuy  j");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testContainsWhenTrue() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        prefixMatches.add("pon  ydyt", "knkln   njkn", "ojpe  j", 
                          "po n  yd yt", "knk  klnlln  klnnnjkn", "ojpeuy  j");
        boolean expResult = true;
        boolean result = prefixMatches.contains("knkln");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testContainsWhenFalse() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        prefixMatches.add("pon  ydyt", "knkln   njkn", "ojpe  j", 
                          "po n  yd yt", "knk  klnlln  klnnnjkn", "ojpeuy  j");
        boolean expResult = false;
        boolean result = prefixMatches.contains("ydyhhhhh");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testDeleteWhenTrue() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        prefixMatches.add("pon  ydyt", "knkln   njkn", "ojpe  j", 
                          "po n  yd yt", "knk  klnlln  klnnnjkn", "ojpeuy  j");
        boolean expResult = true;
        boolean result = prefixMatches.delete("klnlln");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testDeleteWhenFalse() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        prefixMatches.add("pon  ydyt", "knkln   njkn", "ojpe  j", 
                          "po n  yd yt", "knk  klnlln  klnnnjkn", "ojpeuy  j");
        boolean expResult = false;
        boolean result = prefixMatches.delete("knklnyyyhgf");
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testSizeWhenEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        int expResult = 0;
        int result    = prefixMatches.size();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testSizeWhenIsNotEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        prefixMatches.add("ugeiufgu iubub ewfib  f ekw d sd ", "iue", "ifg");
        
        int expResult = 6;
        int result    = prefixMatches.size();
        
        assertEquals(result, expResult);
    }
    
    @Test
    public void testWordsWithPrefixWhenDictIsEmpty() {
        PrefixMatches prefixMatches = new PrefixMatches();
        
        Queue<String> iter;
        iter = (Queue<String>)prefixMatches.wordsWithPrefix("qwe");
        
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefixWhenDictHasSuchWords() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.add("qwert qweijbiksd", "joijo qwefgh", "qwe qweuhb");
        
        Queue<String> iter;
        iter = (Queue<String>)prefixMatches.wordsWithPrefix("qwe");
        
        assertEquals("qwe", iter.dequeue());
        assertEquals("qwert", iter.dequeue());
        assertEquals("qwefgh", iter.dequeue());
        assertEquals("qweuhb", iter.dequeue());
        assertEquals(iter.isEmpty(), true);
    }
    
    @Test
    public void testWordsWithPrefixWhenDictHasNotSuchWords() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.add("qwert qweijbiksd", "joijo qwefgh", "qwe qweuhb");
        
        Queue<String> iter;
        iter = (Queue<String>)prefixMatches.wordsWithPrefix("poqwe");
        
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefixWhenPrefixHasLessThenTwoSymbols() {
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.add("qwert qweijbiksd", "joijo qwefgh", "qwe qweuhb");
        
        Queue<String> iter;
        iter = (Queue<String>)prefixMatches.wordsWithPrefix("q");
        
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
}