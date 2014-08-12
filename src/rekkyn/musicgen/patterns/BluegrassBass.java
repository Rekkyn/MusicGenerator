package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class BluegrassBass extends Playable {
    
    Note prevNote = Note.C0;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (Chord chord : song.progression) {
            if (chord instanceof ResetChord) {
                prevNote = Note.C0;
                continue;
            }
            Note note = new Note(chord.root.num).closestTo(prevNote == Note.C0 ? Note.C4 : prevNote).clamp(Note.C3, Note.C4.plus(5));
            chord.setRoot(note);
            Note lowFifth = chord.fifth.plus(-12);
            
            int chordLength = chord.length;
            while (chordLength - Length.HALF >= 0) {
                playNote(note, Length.EIGHTH);
                playChord(new Note[] { chord.third, chord.fifth }, Length.EIGHTH);
                playNote(lowFifth, Length.EIGHTH);
                playChord(new Note[] { chord.third, chord.fifth }, Length.EIGHTH);
                
                chordLength -= Length.HALF;
            }
            
            if (chordLength > 0) {
                playNote(note, Length.EIGHTH);
                playChord(new Note[] { chord.third, chord.fifth }, Length.EIGHTH);
            }
            
            prevNote = note;
        }
    }
}
