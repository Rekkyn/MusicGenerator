package rekkyn.musicgen.patterns;

import java.util.Random;

import rekkyn.musicgen.*;
import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class Arpeggios extends Playable {
    
    private Note prevNote = Note.C6;
    private int prevPattern = 0;
    
    private Note minNote = Note.C5;
    private Note maxNote = Note.C7;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        for (Chord chord : song.progression) {
            if (chord instanceof ResetChord) {
                prevNote = Note.C5;
                continue;
            }
            
            int chordLength = chord.length;
            while (chordLength > 0) {
                playQuarter(chord);
                chordLength -= Length.QUARTER;
            }
        }
    }
    
    private void playQuarter(Chord chord) {
        Random rand = new Random();
        int randInt = rand.nextInt(4);
        Note start = prevNote.snapToChord(chord, false);
        int chordIndex = chord.indexOfNote(start);
        Note compareNote = null;
        switch (chordIndex) {
            case 0:
                compareNote = chord.rootNote;
                break;
            case 1:
                compareNote = chord.third;
                break;
            case 2:
                compareNote = chord.fifth;
                break;
        }
        
        chord.setRoot(chord.rootNote.plus(start.num - compareNote.num));
        
        if (start.num > maxNote.num) randInt = 1;
        if (start.num < minNote.num) randInt = 0;
        if (randInt == 2) {
            for (int i = 0; i < 2; i++) {
                chordIndex--;
                if (chordIndex < 0) {
                    chordIndex = 2;
                    chord.setRoot(chord.rootNote.plus(-12));
                }
            }
            randInt = 0;
        }
        if (randInt == 3) {
            for (int i = 0; i < 2; i++) {
                chordIndex++;
                if (chordIndex > 2) {
                    chordIndex = 0;
                    chord.setRoot(chord.rootNote.plus(12));
                }
            }
            randInt = 1;
        }
        
        prevPattern = randInt;
        
        if (randInt == 0) {
            playNote(start, Length.SIXTEENTH);
            for (int i = 0; i < 3; i++) {
                chordIndex++;
                if (chordIndex > 2) {
                    chordIndex = 0;
                    chord.setRoot(chord.rootNote.plus(12));
                }
                
                switch (chordIndex) {
                    case 0:
                        playNote(chord.rootNote, Length.SIXTEENTH);
                        prevNote = chord.third;
                        break;
                    case 1:
                        playNote(chord.third, Length.SIXTEENTH);
                        prevNote = chord.fifth;
                        break;
                    case 2:
                        playNote(chord.fifth, Length.SIXTEENTH);
                        prevNote = chord.rootNote.plus(12);
                        break;
                }
            }
        } else if (randInt == 1) {
            playNote(start, Length.SIXTEENTH);
            for (int i = 0; i < 3; i++) {
                chordIndex--;
                if (chordIndex < 0) {
                    chordIndex = 2;
                    chord.setRoot(chord.rootNote.plus(-12));
                }
                
                switch (chordIndex) {
                    case 0:
                        playNote(chord.rootNote, Length.SIXTEENTH);
                        prevNote = chord.fifth.plus(-12);
                        break;
                    case 1:
                        playNote(chord.third, Length.SIXTEENTH);
                        prevNote = chord.rootNote;
                        break;
                    case 2:
                        playNote(chord.fifth, Length.SIXTEENTH);
                        prevNote = chord.third;
                        break;
                }
            }
            
        }
    }
}
