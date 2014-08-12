package rekkyn.musicgen.patterns;

import rekkyn.musicgen.*;
import rekkyn.musicgen.MidiFile.Track;
import rekkyn.musicgen.Reference.Length;

public class TestBass extends Playable {
    
    Note prevNote = Note.C0;
    
    @Override
    public void play(MidiFile mf, Song song, Track track) {
        super.play(mf, song, track);
        
        Note note = Note.C4;
        for (int i = 0; i < 20; i++) {
            playNote(note, Length.EIGHTH);
            note = song.getNextNoteFromScale(note, -1);
        }
    }
}
