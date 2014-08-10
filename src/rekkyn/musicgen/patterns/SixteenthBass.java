package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class SixteenthBass extends Playable {
    
    Note prevNote = Note.C0;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (Chord chord : song.progression) {
            Note note = new Note(chord.root.num).closestTo(prevNote == Note.C0 ? Note.C3 : prevNote).clamp(Note.C2, Note.C4);
            
            int chordLength = chord.length.lengthInt;
            while (chordLength > 0) {
                playChord(new Note[] { note, note.octave() }, Length.SIXTEENTH);
                playChord(new Note[] { note, note.octave() }, Length.SIXTEENTH);
                playChord(new Note[] { note, note.octave() }, Length.EIGHTH);
                
                chordLength -= Length.QUARTER.lengthInt;
            }
            prevNote = note;
            
        }
    }
    
}
