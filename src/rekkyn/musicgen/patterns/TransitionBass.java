package rekkyn.musicgen.patterns;

import java.util.ArrayList;
import java.util.List;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class TransitionBass extends Playable {
    
    Note prevNote = Note.C0;
    private static final Note defaultNote = Note.C4;
    private static final Note minNote = Note.C3;
    private static final Note maxNote = Note.C5;
    
    boolean transitionOnSecond;
    
    public TransitionBass(boolean transitionOnSecond) {
        this.transitionOnSecond = transitionOnSecond;
    }
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (int i = 0; i < song.progression.size(); i++) {
            Chord chord = song.progression.get(i);
            if (chord instanceof ResetChord) {
                prevNote = Note.C0;
                continue;
            }
            Note note = new Note(chord.root.num).closestTo(prevNote == Note.C0 ? defaultNote : prevNote).clamp(minNote, maxNote);
            
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
                
                Note nextNote = new Note(nextChord.root).closestTo(reset ? defaultNote : note).clamp(minNote, maxNote);
                int interval = song.getIntervalBetweenNotes(note, nextNote);
                if (Math.abs(interval) <= 1 || interval == Integer.MAX_VALUE)
                    if (transitionOnSecond && Note.distanceBetweenNotes(note, nextNote) > 1) {
                        playNote(note.plus(interval), Length.EIGHTH);
                    } else
                        playNote(note, Length.EIGHTH);
                else if (interval == 2)
                    playNote(song.getNextNoteFromScale(note, 1), Length.EIGHTH);
                else if (interval == -2)
                    playNote(song.getNextNoteFromScale(note, -1), Length.EIGHTH);
                else {
                    int numNotes = 0;
                    List<Note> notes = new ArrayList<Note>();
                    if (interval > 0) {
                        for (int Int = 1; Int < interval; Int++) {
                            if (nextChord.containsNote(song.getNextNoteFromScale(note, Int))) {
                                numNotes++;
                                notes.add(song.getNextNoteFromScale(note, Int));
                            }
                        }
                    } else {
                        for (int Int = -1; Int > interval; Int--) {
                            if (nextChord.containsNote(song.getNextNoteFromScale(note, Int))) {
                                numNotes++;
                                notes.add(song.getNextNoteFromScale(note, Int));
                            }
                        }
                    }
                    
                    if (numNotes == 1) {
                        for (Note transitionNote : notes) {
                            if (nextChord.containsNote(transitionNote)) playNote(transitionNote, Length.EIGHTH);
                        }
                    } else {
                        Note[] chordNotes = new Note[4];
                        for (Note transitionNote : notes) {
                            if (nextChord.containsNote(transitionNote)) chordNotes[nextChord.indexOfNote(transitionNote)] = transitionNote;
                        }
                        if (chordNotes[3] != null)
                            playNote(chordNotes[3], Length.EIGHTH);
                        else if (chordNotes[1] != null)
                            playNote(chordNotes[1], Length.EIGHTH);
                        else if (chordNotes[2] != null)
                            playNote(chordNotes[2], Length.EIGHTH);
                        else if (chordNotes[0] != null) playNote(chordNotes[0], Length.EIGHTH);
                    }
                }
                
            } else {
                playNote(note, Length.EIGHTH);
            }
            prevNote = note;
            
        }
    }
}
