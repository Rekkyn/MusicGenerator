package rekkyn.musicgen;

import rekkyn.musicgen.Reference.Root;

public class Note {
    
    public static final Note C0 = new Note(0);
    public static final Note C1 = new Note(12);
    public static final Note C2 = new Note(24);
    public static final Note C3 = new Note(36);
    public static final Note C4 = new Note(48);
    public static final Note C5 = new Note(60);
    public static final Note C6 = new Note(72);
    public static final Note C7 = new Note(84);
    
    public int num;
    
    public Note(Root root) {
        num = root.num;
    }
    
    public Note(int num) {
        this.num = num;
    }
    
    public Note closestTo(Note note) {
        int newNum = note.num + relDistanceBetweenNotes(note, this);
        return new Note(newNum);
    }
    
    public static int distanceBetweenNotes(Note a, Note b) {
        int n = Math.abs(a.num % 12 - b.num % 12);
        if (n > 6)
            return 12 - n;
        else
            return n;
    }
    
    public static int relDistanceBetweenNotes(Note a, Note b) {
        int n = b.num % 12 - a.num % 12;
        if (n > 6)
            return n - 12;
        else if (n < -6)
            return n + 12;
        else
            return n;
    }
    
    public Note clamp(Note min, Note max) {
        if (max.num - min.num < 12) try {
            throw new Exception("Max note and min note must be at least one octave apart");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (num >= min.num && num <= max.num) return this;
        
        while (num < min.num)
            num += 12;
        while (num > max.num)
            num -= 12;
        return this;
    }
    
    public Note plus(int i) {
        return new Note(num + i);
    }
    
    public Note snapToChord(Chord chord, boolean seventh) {
        int[] distAndIndex = new int[2];
        distAndIndex[0] = Note.distanceBetweenNotes(this, chord.rootNote);
        distAndIndex[1] = 0;
        if (Note.distanceBetweenNotes(this, chord.third) < distAndIndex[0]) {
            distAndIndex[0] = Note.distanceBetweenNotes(this, chord.third);
            distAndIndex[1] = 1;
        }
        if (Note.distanceBetweenNotes(this, chord.fifth) < distAndIndex[0]) {
            distAndIndex[0] = Note.distanceBetweenNotes(this, chord.fifth);
            distAndIndex[1] = 2;
        }
        if (seventh && Note.distanceBetweenNotes(this, chord.seventh) < distAndIndex[0]) {
            distAndIndex[0] = Note.distanceBetweenNotes(this, chord.seventh);
            distAndIndex[1] = 3;
        }
        
        if (distAndIndex[1] == 0)
            return plus(Note.relDistanceBetweenNotes(this, chord.rootNote));
        else if (distAndIndex[1] == 1)
            return plus(Note.relDistanceBetweenNotes(this, chord.third));
        else if (distAndIndex[1] == 2)
            return plus(Note.relDistanceBetweenNotes(this, chord.fifth));
        else if (distAndIndex[1] == 3) return plus(Note.relDistanceBetweenNotes(this, chord.seventh));
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Note && num == ((Note) obj).num) return true;
        return false;
    }
    
    @Override
    public String toString() {
        return "(" + num + "): " + Root.getRootFromNum(num % 12);
    }
    
}
