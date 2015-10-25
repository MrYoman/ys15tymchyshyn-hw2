package ua.yandex.shad.tries;

import ua.yandex.shad.collections.Queue;

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
public class RWayTrieTest {
    
    @Mock private Tuple emptyStringZeroWeight     = Mockito.mock(Tuple.class);
    @Mock private Tuple emptyStringPositiveWeight = Mockito.mock(Tuple.class);
    @Mock private Tuple stringWithZeroWeight      = Mockito.mock(Tuple.class);
    @Mock private Tuple stringWithOneChar         = Mockito.mock(Tuple.class);
    @Mock private Tuple stringWithTwoChars        = Mockito.mock(Tuple.class);
    @Mock private Tuple bigStringWithVal          = Mockito.mock(Tuple.class);
    
    @Mock private Tuple wordHello                 = Mockito.mock(Tuple.class);
    @Mock private Tuple wordHelloPeople           = Mockito.mock(Tuple.class);
    @Mock private Tuple wordHelloPeoFuckYou       = Mockito.mock(Tuple.class);
    @Mock private Tuple wordHelloMyDearFriend     = Mockito.mock(Tuple.class);
    @Mock private Tuple wordWelcomePeople         = Mockito.mock(Tuple.class);

    private static final String BIG_STRING = "dfoiueiowbfibfuibuiebfb"
                + "ouiwefownfijnfkjbwsenilufuhdeikbiewbikdbiulhfilhrdibfikkj";
    
    @Before
    public void initializeMocks() {
        
        when(emptyStringZeroWeight.getTerm()).thenReturn("");
        when(emptyStringZeroWeight.getWeight()).thenReturn(0);
        
        when(emptyStringPositiveWeight.getTerm()).thenReturn("");
        when(emptyStringPositiveWeight.getWeight()).thenReturn(100);
        
        when(stringWithZeroWeight.getTerm()).thenReturn("yui");
        when(stringWithZeroWeight.getWeight()).thenReturn(0);
                
        when(stringWithOneChar.getTerm()).thenReturn("c");
        when(stringWithOneChar.getWeight()).thenReturn(5);
        
        when(stringWithTwoChars.getTerm()).thenReturn("df");
        when(stringWithTwoChars.getWeight()).thenReturn(10);
        
        when(bigStringWithVal.getTerm()).thenReturn("dfoiueiowbfibfuibuiebfb"
                + "ouiwefownfijnfkjbwsenilufuhdeikbiewbikdbiulhfilhrdibfikkj");
        when(bigStringWithVal.getWeight()).thenReturn(4565);
        
        when(wordHello.getTerm()).thenReturn("hello");
        when(wordHello.getWeight()).thenReturn(10);
        
        when(wordHelloPeople.getTerm()).thenReturn("hellopeople");
        when(wordHelloPeople.getWeight()).thenReturn(10);
        
        when(wordHelloPeoFuckYou.getTerm()).thenReturn("hellopeofuckyou");
        when(wordHelloPeoFuckYou.getWeight()).thenReturn(10);
        
        when(wordWelcomePeople.getTerm()).thenReturn("welcomepeople");
        when(wordWelcomePeople.getWeight()).thenReturn(10);
        
        when(wordHelloMyDearFriend.getTerm()).thenReturn("hellomydearfriend");
        when(wordHelloMyDearFriend.getWeight()).thenReturn(15);
    }

    @Test
    public void testAddEmptyStringZeroWeight() {
        /*
        when(emptyStringZeroWeight.getTerm()).thenReturn("");
        when(emptyStringZeroWeight.getWeight()).thenReturn(0);
        */
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(emptyStringZeroWeight);
        RWayTrie.WordTree root = rWayTrie.getRoot();
        
        int expResult = 0;
        
        assertEquals(root.getWeight(), expResult);
        for (int i = 0; i < 26; i++) {
            assertEquals(root.getSubtree()[i], null);
        }
    }
    
    @Test
    public void testAddEmptyStringPositiveWeight() {
        /*
        when(emptyStringPositiveWeight.getTerm()).thenReturn("");
        when(emptyStringPositiveWeight.getWeight()).thenReturn(100);
        */
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(emptyStringPositiveWeight);
        RWayTrie.WordTree root = rWayTrie.getRoot();
        
        int expResult = 0;
        
        assertEquals(root.getWeight(), expResult);
        for (int i = 0; i < 26; i++) {
            assertEquals(root.getSubtree()[i], null);
        }
    }
    
    @Test
    public void testAddStringWithZeroWeight() {
        /*
        when(emptyStringPositiveWeight.getTerm()).thenReturn("");
        when(emptyStringPositiveWeight.getWeight()).thenReturn(100);
        */
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(stringWithZeroWeight);
        RWayTrie.WordTree root = rWayTrie.getRoot();
        
        int expResult = 0;
        
        assertEquals(root.getWeight(), expResult);
        for (int i = 0; i < 26; i++) {
            assertEquals(root.getSubtree()[i], null);
        }
    }
    
    @Test
    public void testAddStringOfOneChar() {
        
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(stringWithOneChar);
        RWayTrie.WordTree root = rWayTrie.getRoot();
        
        int expResult = 5;
        
        assertEquals(root.getSubtree()[2].getWeight(), expResult);
        for (int i = 0; i < 26; i++) {
            assertEquals(root.getSubtree()[2].getSubtree()[i], null);
        }
    }
 
    @Test
    public void testAddStringOfTwoChars() {
        
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(stringWithTwoChars);
        RWayTrie.WordTree root = rWayTrie.getRoot();
        
        int expResult = 10;
        
        assertEquals(root.getSubtree()[3].getSubtree()[5].getWeight(), 
                                                                expResult);
        for (int i = 0; i < 26; i++) {
            assertEquals(root.getSubtree()[3].getSubtree()[5].getSubtree()[i], 
                                                                        null);
        }
    }
    
    @Test
    public void testAddWhenStringIsBig() {
        
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(bigStringWithVal);
        RWayTrie.WordTree root = rWayTrie.getRoot();
        
        int expResult = 4565;
  
        for (int i = 0; i < BIG_STRING.length(); i++) {
            int c = BIG_STRING.charAt(i) - 'a';
            root = root.getSubtree()[c];
        }
        
        assertEquals(root.getWeight(), expResult);
        for (int i = 0; i < 26; i++) {
            assertEquals(root.getSubtree()[i], null);
        }
    }
    
    @Test
    public void testAddDifferentWords() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        int expResult = 4;
        
        assertEquals(rWayTrie.size(), expResult);
    }
    
    @Test
    public void testContainsWhenContain() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.contains(wordHelloPeople.getTerm()), expResult);
    }
    
    @Test
    public void testContainsWhenDoesNotContain() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.contains(wordHello.getTerm()), expResult);
    }
    
    @Test
    public void testContainsWhenDoesNotContainAndWordIsBig() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.contains(BIG_STRING), expResult);
    }
    
    @Test
    public void testContainsWhenContainAndWordIsBig() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(bigStringWithVal);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.contains(BIG_STRING), expResult);
    }
    
    @Test
    public void testContainsWhenContainAndWordIncludesInLagerWord() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.contains(wordHello.getTerm()), expResult);
    }
    
    @Test
    public void testContainsWhenWordIsNull() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.contains(null), expResult);
    }
    
    
    @Test
    public void testContainsWhenWordIsEmpty() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.contains(""), expResult);
    }
    
    @Test
    public void testContainsWhenContainWordWhichHasOneChar() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        rWayTrie.add(stringWithOneChar);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.contains(stringWithOneChar.getTerm()), expResult);
    }
    
    @Test
    public void testContainsWhenNotContainWordWhichHasOneChar() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.contains(stringWithOneChar.getTerm()), expResult);
    }
    
    public void testContainsWhenContainWordWhichHasTwoChars() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        rWayTrie.add(stringWithTwoChars);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.contains(stringWithTwoChars.getTerm()), 
                                                                expResult);
    }
    
    @Test
    public void testContainsWhenNotContainWordWhichHasTwoChars() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.contains(stringWithTwoChars.getTerm()), 
                                                                expResult);
    }
    
    @Test
    public void testDeleteWhenWordExists() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.delete(wordHelloPeople.getTerm()), expResult);
    }
    
    @Test
    public void testDeleteWhenWordDoesNotExist() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.delete(wordHelloPeople.getTerm()), expResult);
    }
    
    @Test
    public void testDeleteWhenWordDoesNotExistAndInclnclicudesInOtherWord() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.delete(wordHello.getTerm()), expResult);
    }
    
    @Test
    public void testDeleteWhenWordIsBigAndExists() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        rWayTrie.add(bigStringWithVal);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.delete(BIG_STRING), expResult);
    }
    
    @Test
    public void testDeleteWhenWordIsBigAndDoesNotExist() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.delete(BIG_STRING), expResult);
    }
    
    @Test
    public void testDeleteWhenWordIncludesInOtherWord() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = true;
        
        assertEquals(rWayTrie.delete(wordHello.getTerm()), expResult);   
    }
    
    @Test
    public void testDeleteWhenWordIsNull() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.delete(null), expResult);
    }
    
    @Test
    public void testDeleteWhenWordIsEmpty() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        boolean expResult = false;
        
        assertEquals(rWayTrie.delete(""), expResult);
    }
    
    @Test
    public void testSizeWhenSizeIsZero() {
        RWayTrie rWayTrie = new RWayTrie();
        
        int expResult = 0;
        
        assertEquals(rWayTrie.size(), expResult);
    }
    
    @Test
    public void testSizeWhenWordIsEmpty() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(emptyStringZeroWeight);
        
        int expResult = 0;
        
        assertEquals(rWayTrie.size(), expResult);
    }
    
    @Test
    public void testSizeWhenIsOneWord() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        
        int expResult = 1;
        
        assertEquals(rWayTrie.size(), expResult);
    }
    
    @Test
    public void testSizeWhenIsDifferentWords() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordWelcomePeople);
        
        int expResult = 4;
        
        assertEquals(rWayTrie.size(), expResult);
    }
    
    @Test
    public void testIsEmptyTrue() {
        RWayTrie rWayTrie = new RWayTrie();
        
        boolean expResult = true;
        boolean result    = rWayTrie.isEmpty();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsEmptyFalse() {
        RWayTrie rWayTrie = new RWayTrie();
        
        rWayTrie.add(wordHello);
        
        boolean expResult = false;
        boolean result    = rWayTrie.isEmpty();
        
        assertEquals(expResult, result);
    }
        
    @Test
    public void testWordsWhenDictIsEmpty() {
        RWayTrie rWayTrie = new RWayTrie();
        
        Queue<String> iter = (Queue<String>)rWayTrie.words();
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWhenDictHasDifferentWords() {
        RWayTrie rWayTrie = new RWayTrie();
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordHelloMyDearFriend);
        rWayTrie.add(wordWelcomePeople);

        Queue<String> iter = (Queue<String>)rWayTrie.words();
        
        assertEquals(iter.dequeue(), wordHello.getTerm());
        assertEquals(iter.dequeue(), wordHelloPeople.getTerm());
        assertEquals(iter.dequeue(), wordWelcomePeople.getTerm());
        assertEquals(iter.dequeue(), wordHelloPeoFuckYou.getTerm());
        assertEquals(iter.dequeue(), wordHelloMyDearFriend.getTerm());
    }
    
    @Test
    public void testWordsWithPrefixWhenThereAreNoWordsWithSuchPrefix() {
        RWayTrie rWayTrie = new RWayTrie();
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);
        rWayTrie.add(wordHelloMyDearFriend);
        rWayTrie.add(wordWelcomePeople);

        Queue<String> iter = (Queue<String>)rWayTrie.wordsWithPrefix("yo");
        
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefixWhenThereAreWordsWithSuchPrefix() {
        RWayTrie rWayTrie = new RWayTrie();
        rWayTrie.add(wordHello);
        rWayTrie.add(wordHelloMyDearFriend);
        rWayTrie.add(wordWelcomePeople);
        rWayTrie.add(wordHelloPeople);
        rWayTrie.add(wordHelloPeoFuckYou);

        Queue<String> iter = (Queue<String>)rWayTrie.wordsWithPrefix("hello");
        
        assertEquals(iter.dequeue(), wordHello.getTerm());
        assertEquals(iter.dequeue(), wordHelloPeople.getTerm());
        assertEquals(iter.dequeue(), wordHelloPeoFuckYou.getTerm());
        assertEquals(iter.dequeue(), wordHelloMyDearFriend.getTerm());
    }
    
    @Test
    public void testWordsWithPrefixWhenIsEmpty() {
        RWayTrie rWayTrie = new RWayTrie();

        Queue<String> iter = (Queue<String>)rWayTrie.wordsWithPrefix("yo");
        
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testWordsWithPrefixWhenPrefixIsNull() {
        RWayTrie rWayTrie = new RWayTrie();

        Queue<String> iter = (Queue<String>)rWayTrie.wordsWithPrefix(null);
        
        boolean expResult = true;
        boolean result    = iter.isEmpty();
        
        assertEquals(expResult, result);
    }
    
}