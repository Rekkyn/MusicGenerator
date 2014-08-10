package rekkyn.musicgen;

import java.util.ArrayList;

import rekkyn.musicgen.MidiFile.Track;

public class Song {
    public ArrayList<Chord> progression;
    
    public Song setProgression(ArrayList<Chord> progression) {
        this.progression = progression;
        return this;
    }
    
    public void add(Playable p, MidiFile mf, Track track) {
        p.play(mf, this, track);
    }
}
