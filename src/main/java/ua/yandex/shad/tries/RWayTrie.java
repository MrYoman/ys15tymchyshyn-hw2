package ua.yandex.shad.tries;

import ua.yandex.shad.collections.Queue;

public class RWayTrie implements Trie {
    static final int  ALPHABET_SIZE          = 26;
    static final char FIRST_CHAR_IN_ALPHABET = 'a';
    
    private WordTree root = new WordTree();
    private boolean  successDelete;

    public static class WordTree {    
        public int weight;
        public WordTree[] subtree = new WordTree[ALPHABET_SIZE];
    }
    /*
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }
*/
    
    public WordTree getRoot() {
        return root;
    }
    
    private WordTree put(WordTree currentVertex, Tuple wordWeightPair,
                               int distance) {
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
        //throw new UnsupportedOperationException("Not supported yet.");
        if (t.getTerm().length() > 0 && t.getWeight() > 0) {
            root = put(root, t, 0);
        }
    }

    @Override
    public boolean contains(String word) {
        //throw new UnsupportedOperationException("Not supported yet.");
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
        //throw new UnsupportedOperationException("Not supported yet.");
        if (word == null || word.length() == 0) {
            return false;
        }
        
        successDelete = false;
        
        root = delete(root, word, 0); 
        
        return successDelete;
    }

    private WordTree get(WordTree vertex, String word, int distance) { 
        if (vertex == null || word == null) {
            return null;
        }
        
        if (distance == word.length()) {
            return vertex;
        }
        
        int nextIndex = word.charAt(distance) - FIRST_CHAR_IN_ALPHABET;
        return get(vertex.subtree[nextIndex], word, distance + 1);
    }
    
    @Override
    public Iterable<String> words() {
        //throw new UnsupportedOperationException("Not supported yet.");
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
        //throw new UnsupportedOperationException("Not supported yet.");
        if (isEmpty()) {
            return new Queue<String>();
        }
        
        Queue<String>     q     = new Queue<String>();
        Queue<WordTree[]> way   = new Queue<WordTree[]>();
        Queue<String>     words = new Queue<String>();
        
        WordTree prefixVertex = get(root, prefix, 0);
        if (prefixVertex == null) {
            return new Queue<String>();
        }
        if (prefixVertex.weight != 0) {
            q.enqueue(prefix);
        }
        
        way.enqueue(prefixVertex.subtree);
        words.enqueue(prefix);
        
        while (!way.isEmpty()) {
            WordTree[] currentSubtree = way.dequeue();
            String     currentWord    = words.dequeue();
            
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                if (currentSubtree[i] != null) {
                    if (currentSubtree[i].weight != 0) {
                        q.enqueue(currentWord 
                                      + (char)(i + FIRST_CHAR_IN_ALPHABET));
                    }
                    way.enqueue(currentSubtree[i].subtree);
                    words.enqueue(currentWord 
                                      + (char)(i + FIRST_CHAR_IN_ALPHABET));
                }
            }
        }
        
        return q; 
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
        //throw new UnsupportedOperationException("Not supported yet."); 
        return size(root); 
    }

}
