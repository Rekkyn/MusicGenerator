package rekkyn.musicgen;

import rekkyn.musicgen.MidiFile.Track;

public class Playable {
    
    public Track track;
    public MidiFile mf;
    public Song song;
    
    public void play(MidiFile mf, Song song, Track track) {
        this.mf = mf;
        this.song = song;
        this.track = track;
    }
    
    public void playNote(Note note, int length) {
        mf.noteOnOffNow(length, note.num, 127, track);
    }
    
    public void playChord(Note[] notes, int length) {
        for (Note note : notes) {
            mf.noteOn(0, note.num, 127, track);
        }
        
        for (int i = 0; i < notes.length; i++) {
            if (i == 0)
                mf.noteOff(length, notes[i].num, track);
            else
                mf.noteOff(0, notes[i].num, track);
        }
    }
}
