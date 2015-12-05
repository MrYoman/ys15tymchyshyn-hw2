package ua.yandex.shad.tries;

import ua.yandex.shad.collections.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RWayTrie implements Trie {
    static final int  ALPHABET_SIZE          = 26;
    static final char FIRST_CHAR_IN_ALPHABET = 'a';
    
    private WordTree root = new WordTree();
    private boolean  successDelete;

    private static class WordTree {    
        private int weight;
        private WordTree[] subtree = new WordTree[ALPHABET_SIZE];
    }
    
    private WordTree put(WordTree vertex, Tuple wordWeightPair,
                               int distance) {
        WordTree currentVertex = vertex;
        
        if (currentVertex == null) {
            currentVertex = new WordTree();
        }
        
        if (wordWeightPair.getTerm().length() == distance) {
            currentVertex.weight = wordWeightPair.getWeight();
            return currentVertex;
        }
        
        int nextVertex = wordWeightPair.getTerm().charAt(distance)
                                                    -FIRST_CHAR_IN_ALPHABET;
        currentVertex.subtree[nextVertex] = put(currentVertex.subtree[
                                                nextVertex],
                                                wordWeightPair, distance + 1);
        
        return currentVertex;
    }


    @Override
    public void add(Tuple t) {
        if (t.getTerm().length() > 0 && t.getWeight() > 0) {
            root = put(root, t, 0);
        }
    }

    @Override
    public boolean contains(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        
        WordTree vertex = root;
        int length      = word.length();
        
        for (int i = 0; i < length; i++) {
            int nextIndex = word.charAt(i) - FIRST_CHAR_IN_ALPHABET;
            
            vertex = vertex.subtree[nextIndex];
            if (vertex == null) {
                return false;
            }
        }
        
        if (vertex.weight == 0) {
            return false;
        }
        
        return true;
    }
    
    private WordTree delete(WordTree vertex, String word, int distance) {
        if (vertex == null) {
            return null;
        }
        
        if (distance == word.length()) {
            if (vertex.weight != 0) {
                successDelete = true;
                vertex.weight = 0;
            }
        }
        else { 
            int nextIndex = word.charAt(distance) - FIRST_CHAR_IN_ALPHABET;
            vertex.subtree[nextIndex] = delete(vertex.subtree[nextIndex], word, 
                                            distance + 1);
        }
        
        if (vertex.weight != 0) {
            return vertex;
        }
        
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (vertex.subtree[i] != null) {
                return vertex;
            }
        }
        
        return null; 
    }
    
    @Override
    public boolean delete(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        
        successDelete = false;
        
        root = delete(root, word, 0); 
        
        return successDelete;
    }

    private WordTree get(WordTree vert, String word, int dist) { 
        if (vert == null) {
            return null;
        }
        
        if (dist == word.length()) {
            return vert;
        }
        
        int nextIndex = word.charAt(dist) - FIRST_CHAR_IN_ALPHABET;
        return get(vert.subtree[nextIndex], word, dist + 1);
    }
    
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix(""); 
    }
    
    public boolean isEmpty() {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (root.subtree[i] != null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Iterable<String> wordsWithPrefix(String prefix) {
        TreeIterator iterator = new TreeIterator(prefix);
        TreeIterable iterable = new TreeIterable();
        iterable.setIterator(iterator);
        
        return iterable;
    }
    
    private class TreeIterable implements Iterable<String> {

        private TreeIterator iterator;
        
        public void setIterator(TreeIterator iter) {
            this.iterator = iter;
        }
        
        @Override
        public Iterator<String> iterator() {
            return new TreeIterator(iterator);
        }
        
    }
    
    private class TreeIterator implements Iterator<String> {
        
        private String word;
        private TreeIterator next;
        
        private Queue<WordTree[]> way   = new Queue<WordTree[]>();
        private Queue<String>     words = new Queue<String>();
        
        private int lastCheckedLetterIndex = ALPHABET_SIZE;
        private WordTree[] currSubtree;
        private String currWord;
        
        public TreeIterator() {
            word = null;
            next = null;
        }
        
        public TreeIterator(TreeIterator iterator) {
            this.next = iterator.next;
            this.word = iterator.word;
            this.currSubtree = iterator.currSubtree;
            this.currWord = iterator.currWord;
            this.lastCheckedLetterIndex = iterator.lastCheckedLetterIndex;
            this.way = iterator.way;
            this.words = iterator.words;
        }
        
        public TreeIterator(String prefix) {
            if (isEmpty() || prefix == null) {
                return;
            }
            
            this.next = new TreeIterator();
        
            WordTree prefixVertex = get(root, prefix, 0);
            
            if (prefixVertex == null) {
                return;
            }
            
            this.way.enqueue(prefixVertex.subtree);
            this.words.enqueue(prefix);
            
            if (prefixVertex.weight != 0) {
                this.next.word = prefix;
            }
            else {
                this.next.word = this.calculateNextWord();
            }
        }
        
        public void setNextIterator(TreeIterator iterator) {
            this.next = iterator;
        }
        
        public void setWord(String wordStr) {
            this.word = wordStr;
        }
        
        private String calculateNextWord() {
            String searchedWord = "";
            
            while (!way.isEmpty()) {
                if (this.lastCheckedLetterIndex == ALPHABET_SIZE) {
                    this.currSubtree = way.dequeue();
                    this.currWord    = words.dequeue();
                    this.lastCheckedLetterIndex = -1;
                }
                
                int i = this.lastCheckedLetterIndex + 1;
                boolean flag = false;
                for (; i < ALPHABET_SIZE; i++) {
                    if (currSubtree[i] != null) {
                        
                        way.enqueue(currSubtree[i].subtree);
                        words.enqueue(currWord
                                      + (char) (i + FIRST_CHAR_IN_ALPHABET));
                        
                        if (currSubtree[i].weight != 0) {
                            searchedWord = currWord
                                        + (char) (i + FIRST_CHAR_IN_ALPHABET);
                            flag = true;
                            break;
                        }
                    }
                }
                this.lastCheckedLetterIndex = i;
                if (flag) {
                    break;
                }
            }
            
            return searchedWord;
        }
                
        @Override
        public boolean hasNext() {
            return next != null && next.word != null && !next.word.equals("");
        }

        @Override
        public String next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            word = next.word;
            next = next.next;
            if (next == null) {
                next = new TreeIterator();
            }
            
            next.word = this.calculateNextWord();
            
            return word;
        }
        
    }
    
    private int size(WordTree vertex) { 
        if (vertex == null) {
            return 0;
        }
        
        int currentSize = 0;
        if (vertex.weight != 0) {
            currentSize++;
        }
        
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            currentSize += size(vertex.subtree[i]);
        }
        
        return currentSize; 
    }
    
    @Override
    public int size() {
        return size(root); 
    }

}
