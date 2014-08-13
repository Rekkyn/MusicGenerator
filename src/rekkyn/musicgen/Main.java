package rekkyn.musicgen;

import java.io.IOException;

import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Position;
import rekkyn.musicgen.patterns.Arpeggios;
import rekkyn.musicgen.patterns.TransitionBass;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setKey(Root.C, Scale.MINOR).setProgression(flibbert);
        
        song.add(new Arpeggios(), mf, Track.CHORDS);
        song.add(new TransitionBass(true), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
    public static final Chord[] believin = new Chord[] { Chord.pos(Position.I), Chord.pos(Position.V), Chord.pos(Position.VI),
            Chord.pos(Position.IV), Chord.pos(Position.I), Chord.pos(Position.V), Chord.pos(Position.III), Chord.pos(Position.IV) };
    
    public static final Chord[] rekkyn = new Chord[] { Chord.pos(Position.I), Chord.pos(Position.VI), Chord.pos(Position.III),
            Chord.pos(Position.V).majThird(true), Chord.pos(Position.I), Chord.pos(Position.VI), Chord.pos(Position.III),
            Chord.pos(Position.V).majThird(true) };
    
    public static final Chord[] OneSixFourFive = new Chord[] { Chord.pos(Position.I), Chord.pos(Position.VI), Chord.pos(Position.IV),
            Chord.pos(Position.V), Chord.pos(Position.I), Chord.pos(Position.VI), Chord.pos(Position.IV), Chord.pos(Position.V) };
    
    public static final Chord[] flibbert = new Chord[] { new Chord(Root.C).minor(), new Chord(Root.Fs).minor(), new Chord(Root.C).minor(),
            new Chord(Root.G), new Chord(Root.C).minor(), new Chord(Root.Ab).length(Length.HALF), new Chord(Root.G).length(Length.HALF),
            new Chord(Root.C).minor(), new Chord(Root.G) };
    
}
