package rekkyn.musicgen.patterns;

import java.util.ArrayList;

import rekkyn.musicgen.*;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class ClosestChord extends Playable {
    
    public ArrayList<Note> notes = new ArrayList<Note>();
    public ArrayList<Note> prevNotes = new ArrayList<Note>();
    
    int length = Length.WHOLE;
    
    public ClosestChord(int length) {
        this.length = length;
    }
    
    public void getNotes(Chord chord) {
        Note rootNote = new Note(chord.root.num).closestTo(Note.C5);
        
        ArrayList<Note> newnotes = new ArrayList<Note>();
        newnotes.add(rootNote);
        newnotes.add(rootNote.plus(chord.third));
        newnotes.add(rootNote.plus(chord.fifth));
        
        Note closest = newnotes.get(0);
        if (!prevNotes.isEmpty()) {
            for (Note newNote : newnotes) {
                if (Note.distanceBetweenNotes(prevNotes.get(0), newNote) <= Note.distanceBetweenNotes(prevNotes.get(0), closest)) {
                    closest = prevNotes.get(0).plus(Note.relDistanceBetweenNotes(prevNotes.get(0), newNote));
                }
            }
        }
        
        if (!newnotes.contains(closest)) {
            if (closest.num < newnotes.get(0).num) {
                newnotes.set(0, newnotes.get(0).plus(-12));
                newnotes.set(1, newnotes.get(1).plus(-12));
                newnotes.set(2, newnotes.get(2).plus(-12));
            } else if (closest.num > newnotes.get(0).num) {
                newnotes.set(1, newnotes.get(1).plus(12));
                newnotes.set(0, newnotes.get(0).plus(12));
                newnotes.set(2, newnotes.get(2).plus(12));
            }
        }
        
        if (newnotes.indexOf(closest) != 0) {
            for (int i = 0; i < newnotes.size(); i++) {
                if (newnotes.indexOf(closest) == 1) {
                    notes.clear();
                    notes.add(newnotes.get(1));
                    notes.add(newnotes.get(2));
                    notes.add(newnotes.get(0).plus(12));
                } else if (newnotes.indexOf(closest) == 2) {
                    notes.clear();
                    notes.add(newnotes.get(2));
                    notes.add(newnotes.get(0).plus(12));
                    notes.add(newnotes.get(1).plus(12));
                }
            }
        } else {
            notes = newnotes;
        }
        
    }
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (Chord chord : song.progression) {
            getNotes(chord);
            int chordLength = chord.length;
            while (chordLength > 0) {
                int playLength;
                if (chordLength > length) {
                    playLength = length;
                } else {
                    playLength = chordLength;
                }
                
                Note[] noteArray = new Note[notes.size()];
                playChord(notes.toArray(noteArray), playLength);
                
                chordLength -= playLength;
                
            }
            prevNotes = (ArrayList<Note>) notes.clone();
            notes.clear();
        }
    }
    
}
