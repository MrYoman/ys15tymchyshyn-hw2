/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.yandex.shad.autocomplete;

import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.collections.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrefixMatches {

    private static final int DEFAULT_NUM_OF_DIFF_LEN_IN_WORDS_WITH_PREFIX = 3;
    
    private final Trie trie = new RWayTrie();
    
    public int add(String... strings) {
        //throw new UnsupportedOperationException("Not supported yet.");
        
        if (strings.length == 0) {
            return trie.size();
        }
        
        for (String str : strings) {
            int prev = 0;
            int length = str.length();
            for (int i = 0; i < length; i++) {
                if (str.charAt(i) == ' ') {
                    if (i - prev > 2) {
                        trie.add(new Tuple(str.substring(prev, i), i - prev));
                    }
                    prev = i + 1;
                }
            }
            if (length - prev > 2) {
                trie.add(new Tuple(str.substring(prev), length - prev));
            }
        }
        
        return trie.size();
    }

    public boolean contains(String word) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return trie.contains(word);
    }

    public boolean delete(String word) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return wordsWithPrefix(pref,
                               DEFAULT_NUM_OF_DIFF_LEN_IN_WORDS_WITH_PREFIX);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (pref == null || pref.length() < 2
                || ((RWayTrie) trie).isEmpty() || k == 0) {
            return new TrieIterable();
        }
        
        Iterator<String> iterator = trie.wordsWithPrefix(pref).iterator();
        TrieIterable words = new TrieIterable();
        TrieIterator iter = new TrieIterator(iterator, k);
        words.setIterator(iter);
        
        return words;
    }

    public int size() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return trie.size();
    }
    
    private class TrieIterable implements Iterable<String> {

        TrieIterator iterator = new TrieIterator(null ,0);
        
        @Override
        public Iterator<String> iterator() {
            return iterator;
        }
        
        public void setIterator(TrieIterator iterator) {
            this.iterator = iterator;
        }
        
    }
    
    private class TrieIterator implements Iterator<String> {

        private Iterator<String> iterator;
        private String nextElem;
        private int prevLen = 0;
        private int count;
        
        public TrieIterator(Iterator<String> iterator, int count) {
            this.iterator = iterator;
            this.count = count;
            if (iterator != null && iterator.hasNext()) {
                this.nextElem = iterator.next();
            }
        }
        
        @Override
        public boolean hasNext() {
            if (iterator == null || nextElem == null 
                    || nextElem.length() != prevLen && count == 0) {
                return false;
            }
            return true;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            String elem = nextElem;
            
            if (!iterator.hasNext()) {
                nextElem = null;
            }
            else {
                nextElem = iterator.next();
            }
            
            if (elem.length() != prevLen) {
                count--;
                prevLen = elem.length();
            }
            
            return elem;
        }
        
    }
    
}
