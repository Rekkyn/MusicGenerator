package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class PopcornBass extends Playable {
    
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
            chord.setRoot(note);
            Note octave = note.plus(12);
            
            int chordLength = chord.length;
            while (chordLength - Length.QUARTER > 0) {
                playNote(note, Length.EIGHTH);
                playNote(octave, Length.SIXTEENTH);
                playNote(chord.fifth, Length.SIXTEENTH);
                
                chordLength -= Length.QUARTER;
            }
            playNote(note, Length.SIXTEENTH);
            playNote(chord.third, Length.SIXTEENTH);
            playNote(chord.fifth, Length.SIXTEENTH);
            playNote(octave, Length.SIXTEENTH);
            
            prevNote = note;
        }
    }
    
}
