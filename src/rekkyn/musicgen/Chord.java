package rekkyn.musicgen;

import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;

public class Chord {
    
    public Root root;
    public boolean major = true;
    public Length length = Length.WHOLE;
    
    public Chord(Root root) {
        this.root = root;
    }
    
    public Chord minor() {
        major = false;
        return this;
    }
    
    public Chord length(Length length) {
        this.length = length;
        return this;
    }
}
