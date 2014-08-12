package rekkyn.musicgen;

import java.util.ArrayList;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Positions;

public class Song {
    public ArrayList<Chord> progression;
    public Root key;
    public int[] scale;
    
    public Song setProgression(Object[] progression) {
        for (Object element : progression) {
            if (element instanceof Positions) {
                int rootNum = key.num + scale[((Positions) element).position];
                
            }
        }
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
    
}
