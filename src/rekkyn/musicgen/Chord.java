package rekkyn.musicgen;

import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Position;

public class Chord {
    
    public Root root;
    public Note rootNote;
    public int length = Length.WHOLE;
    
    public Note third;
    public Note fifth;
    public Note seventh;
    
    private int thirdInt = 4;
    private int fifthInt = 7;
    private int seventhInt = 11;
    
    public Chord(Root root) {
        this.root = root;
        rootNote = new Note(root);
        rebuild();
    }
    
    public Chord() {}
    
    public static PositionChord pos(Position pos) {
        return new PositionChord(pos);
    }
    
    public Chord minor() {
        thirdInt = 3;
        seventhInt = 10;
        rebuild();
        return this;
    }
    
    public Chord flat7() {
        seventhInt = 10;
        rebuild();
        return this;
    }
    
    public Chord dim() {
        thirdInt = 3;
        fifthInt = 6;
        rebuild();
        return this;
    }
    
    public Chord length(int length) {
        this.length = length;
        return this;
    }
    
    public Chord setRoot(Note root) {
        if (root.num % 12 != rootNote.num % 12) {
            try {
                throw new Exception("Wrong chord root");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }
        rootNote = root;
        rebuild();
        return this;
    }
    
    private void rebuild() {
        third = rootNote.plus(thirdInt);
        fifth = rootNote.plus(fifthInt);
        seventh = rootNote.plus(seventhInt);
    }
    
    public void setIntervals(int third, int fifth, int seventh) {
        thirdInt = third;
        fifthInt = fifth;
        seventhInt = seventh;
        rebuild();
    }
    
    public boolean containsNote(Note note) {
        return note.num % 12 == rootNote.num % 12 || note.num % 12 == third.num % 12 || note.num % 12 == fifth.num % 12
                || note.num % 12 == seventh.num % 12;
    }
    
    /** @param note
     * @return Index of the note. 0: root, 1: third, 2: fifth, 3: seventh, -1:
     *         not found in chord */
    public int indexOfNote(Note note) {
        if (!containsNote(note)) return -1;
        if (note.num % 12 == rootNote.num % 12) return 0;
        if (note.num % 12 == third.num % 12) return 1;
        if (note.num % 12 == fifth.num % 12) return 2;
        if (note.num % 12 == seventh.num % 12) return 3;
        return -1;
    }
    
    public static class ResetChord extends Chord {
        public ResetChord() {}
    }
    
    public static class PositionChord extends Chord {
        
        public Position pos;
        
        public Boolean majThird;
        
        public PositionChord(Position pos) {
            this.pos = pos;
        }
        
        public Chord majThird(boolean majThird) {
            this.majThird = majThird;
            return this;
        }
    }
}
