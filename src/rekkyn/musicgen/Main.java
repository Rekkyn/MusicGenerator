package rekkyn.musicgen;

import java.io.IOException;

import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Positions;
import rekkyn.musicgen.patterns.ClosestChord;
import rekkyn.musicgen.patterns.TransitionBass;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setKey(Root.C, Scale.MINOR).setProgression(
                new Object[] { Positions.I, Positions.VI, Positions.III, Positions.VII, new ResetChord(), Positions.I, Positions.VI,
                        Positions.III, new Chord(Root.G) });
        
        song.add(new ClosestChord(Length.WHOLE), mf, Track.CHORDS);
        song.add(new TransitionBass(true), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
