package rekkyn.musicgen;

import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;

public class Chord {
    
    public Root root;
    public boolean major = true;
    public int length = Length.WHOLE;
    
    public Chord(Root root) {
        this.root = root;
    }
    
    public Chord() {}
    
    public Chord minor() {
        major = false;
        return this;
    }
    
    public Chord length(int length) {
        this.length = length;
        return this;
    }
    
    public static class ResetChord extends Chord {
        public ResetChord() {}
    }
}
