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
        
        Song song = new Song().setKey(Root.C, Scale.MINOR).setProgression(
                new Chord[] { Chord.pos(Position.I), Chord.pos(Position.VII), Chord.pos(Position.VI), Chord.pos(Position.V).majThird(true),
                        Chord.pos(Position.III), Chord.pos(Position.VI), Chord.pos(Position.V).majThird(true).length(Length.WHOLE * 2),
                        Chord.pos(Position.I) });
        
        song.add(new Arpeggios(), mf, Track.CHORDS);
        song.add(new TransitionBass(false), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
