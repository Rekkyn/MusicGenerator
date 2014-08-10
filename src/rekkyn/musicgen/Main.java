package rekkyn.musicgen;

import java.io.IOException;
import java.util.ArrayList;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.patterns.SixteenthBass;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setProgression(new ArrayList<Chord>() {
            {
                add(new Chord(Root.C));
                add(new Chord(Root.G));
                add(new Chord(Root.A).minor());
                add(new Chord(Root.F));
                add(new Chord(Root.C));
                add(new Chord(Root.G));
                add(new Chord(Root.E).minor());
                add(new Chord(Root.F));
            }
        });
        
        song.add(new SixteenthBass(), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
