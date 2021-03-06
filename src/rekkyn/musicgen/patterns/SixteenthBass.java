package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class SixteenthBass extends Playable {
    
    Note prevNote = Note.C0;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (Chord chord : song.progression) {
            if (chord instanceof ResetChord) {
                prevNote = Note.C0;
                continue;
            }
            Note note = new Note(chord.root.num).closestTo(prevNote == Note.C0 ? Note.C3 : prevNote).clamp(Note.C2, Note.C4);
            
            int chordLength = chord.length;
            while (chordLength > 0) {
                playChord(new Note[] { note, note.plus(12) }, Length.SIXTEENTH);
                playChord(new Note[] { note, note.plus(12) }, Length.SIXTEENTH);
                playChord(new Note[] { note, note.plus(12) }, Length.EIGHTH);
                
                chordLength -= Length.QUARTER;
            }
            prevNote = note;
            
        }
    }
    
}
