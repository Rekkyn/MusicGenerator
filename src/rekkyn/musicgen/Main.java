package rekkyn.musicgen;

import java.io.IOException;
import java.util.ArrayList;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.patterns.ClosestChord;
import rekkyn.musicgen.patterns.PopcornBass;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setProgression(new ArrayList<Chord>() {
            {
                add(new Chord(Root.C).minor());
                add(new Chord(Root.Ab));
                add(new Chord(Root.Eb));
                add(new Chord(Root.Bb));
                add(new Chord(Root.C).minor());
                add(new Chord(Root.Ab));
                add(new Chord(Root.Eb));
                add(new Chord(Root.Bb).length(Length.HALF));
                add(new Chord(Root.B).dim().length(Length.HALF));
            }
        });
        
        song.add(new ClosestChord(Length.HALF), mf, Track.CHORDS);
        song.add(new PopcornBass(), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
