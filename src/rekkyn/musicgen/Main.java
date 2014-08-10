package rekkyn.musicgen;

import java.io.IOException;
import java.util.ArrayList;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.patterns.BluegrassBass;
import rekkyn.musicgen.patterns.ClosestChord;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setProgression(new ArrayList<Chord>() {
            {
                add(new Chord(Root.C).length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.B).dim().length(Length.QUARTER));
                add(new Chord(Root.A).minor().length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.G).length(Length.QUARTER));
                add(new Chord(Root.F).length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.Fs).length(Length.QUARTER));
                add(new Chord(Root.G).length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.B).dim().length(Length.QUARTER));
                add(new Chord(Root.C).length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.B).dim().length(Length.QUARTER));
                add(new Chord(Root.A).minor().length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.G).length(Length.QUARTER));
                add(new Chord(Root.F).length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.Fs).length(Length.QUARTER));
                add(new Chord(Root.G).length(Length.HALF + Length.QUARTER));
                add(new Chord(Root.B).dim().length(Length.QUARTER));
            }
        });
        
        song.add(new ClosestChord(Length.WHOLE), mf, Track.CHORDS);
        song.add(new BluegrassBass(), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
