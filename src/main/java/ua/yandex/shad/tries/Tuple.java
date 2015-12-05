package ua.yandex.shad.tries;

public class Tuple {
    private final String term;
    final int weight;

    public Tuple(String term, int weight) {
        if (term == null) {
            throw new IllegalArgumentException();
        }
        this.term   = term;
        this.weight = weight;
    }  
    
    public String getTerm() {
        return term;
    }
    
    public int getWeight() {
        return weight;
    }
}
