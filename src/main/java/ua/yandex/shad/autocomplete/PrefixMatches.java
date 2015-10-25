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

public class PrefixMatches {

    private Trie trie = new RWayTrie();
    
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
        return wordsWithPrefix(pref, 3);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (pref == null || pref.length() < 2) {
            return new Queue<String>();
        }
        
        if (((RWayTrie)trie).isEmpty()) {
            return new Queue<String>();
        }
        
        Iterable<String> yo        = trie.wordsWithPrefix(pref);
        Iterator<String> wordsList = yo.iterator();
        Queue<String>    result    = new Queue<String>();

        int currLength, oldLength;
        String currWord;
            
        if (!wordsList.hasNext()) {
            return new Queue<String>();
        }
        
        currWord   = wordsList.next();
        currLength = currWord.length();
        oldLength  = currLength;
        
        int m = 0;
            
        do {
            result.enqueue(currWord);
            currWord   = wordsList.next();
            currLength = currWord.length();
            if (currLength != oldLength) {
                m++;
                oldLength = currLength;
            }
        } while(wordsList.hasNext() && m < k);

        return result;
    }

    public int size() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return trie.size();
    }
}
