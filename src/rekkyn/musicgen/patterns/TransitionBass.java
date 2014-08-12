package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class TransitionBass extends Playable {
    
    Note prevNote = Note.C0;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (int i = 0; i < song.progression.size(); i++) {
            Chord chord = song.progression.get(i);
            if (chord instanceof ResetChord) {
                prevNote = Note.C0;
                continue;
            }
            Note note = new Note(chord.root.num).closestTo(prevNote == Note.C0 ? Note.C4 : prevNote).clamp(Note.C3, Note.C5);
            
            int chordLength = chord.length;
            while (chordLength > Length.EIGHTH) {
                playNote(note, Length.EIGHTH);
                chordLength -= Length.EIGHTH;
            }
            if (i + 1 < song.progression.size()) {
                Chord nextChord = song.progression.get(i + 1);
                boolean reset = false;
                if (nextChord instanceof ResetChord && i + 2 < song.progression.size()) {
                    nextChord = song.progression.get(i + 2);
                    reset = true;
                }
                
                Note nextNote = new Note(nextChord.root).closestTo(reset ? Note.C3 : note).clamp(Note.C2, Note.C4);
                int interval = song.getIntervalBetweenNotes(note, nextNote);
                if (Math.abs(interval) <= 1)
                    playNote(note, Length.EIGHTH);
                else if (interval == 2)
                    playNote(song.getNextNoteFromScale(note, 1), Length.EIGHTH);
                else if (interval == -2) playNote(song.getNextNoteFromScale(note, -1), Length.EIGHTH);
                else
                    playNote(note, Length.EIGHTH);
                System.out.println(interval);
                
            } else {
                playNote(note, Length.EIGHTH);
            }
            prevNote = note;
            
        }
    }
}
