package rekkyn.musicgen;

import java.io.IOException;

import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Positions;
import rekkyn.musicgen.patterns.BluegrassBass;
import rekkyn.musicgen.patterns.ClosestChord;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setKey(Root.C, Scale.MAJOR).setProgression(
                new Object[] { Positions.I, Positions.V, Positions.VI, Positions.IV, new ResetChord(), Positions.I, Positions.V,
                        Positions.III,
                        Positions.IV });
        
        song.add(new ClosestChord(Length.WHOLE), mf, Track.CHORDS);
        song.add(new BluegrassBass(), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
