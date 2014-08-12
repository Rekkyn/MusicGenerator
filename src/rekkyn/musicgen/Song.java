package rekkyn.musicgen;

import java.util.*;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Positions;

public class Song {
    public List<Chord> progression = new ArrayList<Chord>();
    public Root key;
    public int[] scale;
    
    public Song setProgression(Object[] progression) {
        for (int i = 0; i < progression.length; i++) {
            if (progression[i] instanceof Positions) {
                int rootNum = (key.num + scale[((Positions) progression[i]).position]) % 12;
                Chord chord = new Chord(Root.getRootFromNum(rootNum));
                Note root = new Note(rootNum);
                chord.third = getNextNoteFromScale(root, 2).num - rootNum;
                chord.fifth = getNextNoteFromScale(root, 4).num - rootNum;
                chord.seventh = getNextNoteFromScale(root, 6).num - rootNum;
                progression[i] = chord;
            }
        }
        Chord[] chordArray = Arrays.copyOf(progression, progression.length, Chord[].class);
        this.progression = Arrays.asList(chordArray);
        return this;
    }
    
    public void add(Playable p, MidiFile mf, Track track) {
        p.play(mf, this, track);
    }
    
    public Song setKey(Root key, int[] scale) {
        this.key = key;
        this.scale = scale;
        return this;
    }
    
    public Note getNextNoteFromScale(Note start, int interval) {
        int toAdd = 0;
        int pos = getPositionInScale(start);
        if (interval > 0) {
            for (int i = 0; i < interval; i++) {
                pos++;
                if (pos >= scale.length) {
                    pos = 0;
                    toAdd += 12 - scale[scale.length - 1];
                } else {
                    toAdd += scale[pos] - scale[pos - 1];
                }
            }
        } else {
            for (int i = 0; i > interval; i--) {
                pos--;
                if (pos < 0) {
                    pos = scale.length - 1;
                    toAdd -= 12 - scale[pos];
                } else {
                    toAdd += scale[pos] - scale[pos + 1];
                }
            }
            
        }
        
        return start.plus(toAdd);
    }
    
    public int getPositionInScale(Note note) {
        for (int i = 0; i < scale.length; i++) {
            if (note.num % 12 == (key.num + scale[i]) % 12) return i;
        }
        System.err.println("Note " + note + "is not in key " + key);
        return 0;
    }
    
    public int getIntervalBetweenNotes(Note a, Note b) {
        if (b.num > a.num) {
            int pos = 1;
            while (true) {
                if (getNextNoteFromScale(a, pos).equals(b)) return pos;
                pos++;
            }
        } else {
            int pos = -1;
            while (true) {
                if (getNextNoteFromScale(a, pos).equals(b)) return pos;
                pos--;
            }
        }
    }
    
}
