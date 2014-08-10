package rekkyn.musicgen;

import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;

public class Chord {
    
    public Root root;
    public int length = Length.WHOLE;
    
    public int third = 4;
    public int fifth = 7;
    public int seventh = 11;
    
    public Chord(Root root) {
        this.root = root;
    }
    
    public Chord() {}
    
    public Chord minor() {
        third = 3;
        seventh = 10;
        return this;
    }
    
    public Chord flat7() {
        seventh = 10;
        return this;
    }
    
    public Chord dim() {
        third = 3;
        fifth = 6;
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
