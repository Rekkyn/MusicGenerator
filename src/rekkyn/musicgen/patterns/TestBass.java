package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class TestBass extends Playable {
    
    Note prevNote = Note.C0;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (Chord chord : song.progression) {
            if (chord instanceof ResetChord) {
                prevNote = Note.C0;
                continue;
            }
            Note note = new Note(chord.root.num).closestTo(prevNote == Note.C0 ? Note.C4 : prevNote).clamp(Note.C3, Note.C4);
            Note seventh = note.plus(chord.seventh);
            Note octave = note.plus(12);
            
            int chordLength = chord.length;
            while (chordLength - Length.HALF > 0) {
                playNote(note, Length.EIGHTH);
                playNote(octave, Length.EIGHTH);
                playNote(seventh, Length.EIGHTH);
                playNote(seventh.plus(-12), Length.EIGHTH);
                
                chordLength -= Length.HALF;
            }
            playNote(octave, Length.EIGHTH);
            playNote(note, Length.EIGHTH);
            chordLength -= Length.QUARTER;
            if (chordLength > 0) {
                playNote(seventh, Length.EIGHTH);
                playNote(octave, Length.EIGHTH);
            }
            
            prevNote = note;
        }
    }
}
