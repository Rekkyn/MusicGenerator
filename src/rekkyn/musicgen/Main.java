package rekkyn.musicgen;

import java.io.IOException;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.patterns.TestBass;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setKey(Root.F, Scale.MAJOR);
        
        song.add(new TestBass(), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
