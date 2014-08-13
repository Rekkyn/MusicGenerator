package rekkyn.musicgen;

import java.io.IOException;

import rekkyn.musicgen.Chord.ResetChord;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;
import rekkyn.musicgen.Reference.Root;
import rekkyn.musicgen.Scale.Position;
import rekkyn.musicgen.patterns.ClosestChord;
import rekkyn.musicgen.patterns.TransitionBass;

public class Main {
    
    public static void main(String[] args) throws IOException {
        MidiFile mf = new MidiFile();
        
        Song song = new Song().setKey(Root.Fs, Scale.MINOR).setProgression(
                new Chord[] { Chord.pos(Position.I), Chord.pos(Position.VI), Chord.pos(Position.IV),
                        Chord.pos(Position.VII).length(Length.HALF), new Chord(Root.F).dim().length(Length.HALF), new ResetChord(),
                        Chord.pos(Position.I),
                        Chord.pos(Position.VI), Chord.pos(Position.IV), Chord.pos(Position.VII).length(Length.HALF),
                        new Chord(Root.F).dim().length(Length.HALF), Chord.pos(Position.I) });
        
        song.add(new ClosestChord(Length.WHOLE), mf, Track.CHORDS);
        song.add(new TransitionBass(true), mf, Track.BASS);
        
        mf.writeToFile("test.mid");
    }
    
}
