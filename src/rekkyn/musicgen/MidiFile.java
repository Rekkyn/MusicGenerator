/*
  A simple Java class that writes a MIDI file

  (c)2011 Kevin Boone, all rights reserved
 */
package rekkyn.musicgen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class MidiFile {
    
    public ArrayList<Vector<int[]>> tracks = new ArrayList<Vector<int[]>>();
    
    // Standard MIDI file header
    static final int header[] = new int[] { 0x4d, 0x54, 0x68, 0x64, 0x00, 0x00, 0x00, 0x06, // header
        0x00, 0x01, // multiple tracks, synchronous
        0x00, 0x03, // three tracks
        0x00, 0x10, // 16 ticks per quarter
    };
    
    // track header
    static final int trackHeader[] = new int[] { 0x4d, 0x54, 0x72, 0x6B };
    
    // Standard footer
    static final int footer[] = new int[] { 0x01, 0xFF, 0x2F, 0x00 };
    
    // A MIDI event to set the tempo
    static final int tempoEvent[] = new int[] { 0x00, 0xFF, 0x51, 0x03,//
        0x05, 0xB8, 0xD8 // 160 BPM (to get this, find how many seconds per
        // beat, convert to microseconds, then into
        // hexidecimal, that split into the 3 bytes)
    };
    
    // A MIDI event to set the key signature. This is irrelent to
    // playback, but necessary for editing applications
    static final int keySigEvent[] = new int[] { 0x00, 0xFF, 0x59, 0x02, 0x00, // C
        0x00  // major
    };
    
    // A MIDI event to set the time signature. This is irrelent to
    // playback, but necessary for editing applications
    static final int timeSigEvent[] = new int[] { 0x00, 0xFF, 0x58, 0x04, 0x04, // numerator
        0x02, // denominator (2==4, because it's a power of 2)
        0x30, // ticks per click (not used)
        0x08  // 32nd notes per crotchet
    };
    
    // The collection of events to play, in time order
    protected Vector<int[]> bassTrack = new Vector<int[]>();
    protected Vector<int[]> chordTrack = new Vector<int[]>();
    protected Vector<int[]> melodyTrack = new Vector<int[]>();
    
    /** Construct a new MidiFile with an empty playback event list */
    public MidiFile() {
        tracks.add(chordTrack);
        tracks.add(bassTrack);
        tracks.add(melodyTrack);
    }
    
    /** Write the stored MIDI events to a file */
    public void writeToFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        
        fos.write(intArrayToByteArray(header));
        
        for (Vector<int[]> track : tracks) {
            
            fos.write(intArrayToByteArray(trackHeader));
            
            // Calculate the amount of track data
            // _Do_ include the footer but _do not_ include the
            // track header
            
            int size = tempoEvent.length + keySigEvent.length + timeSigEvent.length + footer.length;
            
            for (int i = 0; i < track.size(); i++)
                size += track.elementAt(i).length;
            
            // Write out the track data size in big-endian format
            // Note that this math is only valid for up to 64k of data
            // (but that's a lot of notes)
            int high = size / 256;
            int low = size - high * 256;
            fos.write((byte) 0);
            fos.write((byte) 0);
            fos.write((byte) high);
            fos.write((byte) low);
            
            // Write the standard metadata Ñ tempo, etc
            // At present, tempo is stuck at crotchet=60
            fos.write(intArrayToByteArray(tempoEvent));
            fos.write(intArrayToByteArray(keySigEvent));
            fos.write(intArrayToByteArray(timeSigEvent));
            
            // Write out the note, etc., events
            for (int i = 0; i < track.size(); i++) {
                fos.write(intArrayToByteArray(track.elementAt(i)));
            }
            
            // Write the footer and close
            fos.write(intArrayToByteArray(footer));
            
        }
        fos.close();
    }
    
    /** Convert an array of integers which are assumed to contain unsigned bytes
     * into an array of bytes */
    protected static byte[] intArrayToByteArray(int[] ints) {
        int l = ints.length;
        byte[] out = new byte[ints.length];
        for (int i = 0; i < l; i++) {
            out[i] = (byte) ints[i];
        }
        return out;
    }
    
    /** Store a note-on event */
    public void noteOn(int delta, int note, int velocity, Track track) {
        int[] data = new int[4];
        data[0] = delta;
        data[1] = 0x90 + Track.toInt(track);
        data[2] = note;
        data[3] = velocity;
        switch (track) {
            case BASS:
                bassTrack.add(data);
                break;
            case CHORDS:
                chordTrack.add(data);
                break;
            case MELODY:
                melodyTrack.add(data);
                break;
            default:
                System.err.println("PICK A CORRECT TRACK, SON!");
                break;
        }
    }
    
    /** Store a note-off event */
    public void noteOff(int delta, int note, Track track) {
        int[] data = new int[4];
        data[0] = delta;
        data[1] = 0x80 + Track.toInt(track);
        data[2] = note;
        data[3] = 0;
        switch (track) {
            case BASS:
                bassTrack.add(data);
                break;
            case CHORDS:
                chordTrack.add(data);
                break;
            case MELODY:
                melodyTrack.add(data);
                break;
            default:
                System.err.println("PICK A CORRECT TRACK, SON!");
                break;
        }
    }
    
    /** Store a program-change event at current position */
    public void progChange(int prog, Track track) {
        int[] data = new int[3];
        data[0] = 0;
        data[1] = 0xC0 + Track.toInt(track);
        data[2] = prog;
        switch (track) {
            case BASS:
                bassTrack.add(data);
                break;
            case CHORDS:
                chordTrack.add(data);
                break;
            case MELODY:
                melodyTrack.add(data);
                break;
            default:
                System.err.println("PICK A CORRECT TRACK, SON!");
                break;
        }
    }
    
    /** Store a note-on event followed by a note-off event a note length later.
     * There is no delta value Ñ the note is assumed to follow the previous one
     * with no gap. */
    public void noteOnOffNow(int duration, int note, int velocity, Track track) {
        noteOn(0, note, velocity, track);
        noteOff(duration, note, track);
    }
    
    public void noteSequenceFixedVelocity(int[] sequence, int velocity, Track track) {
        boolean lastWasRest = false;
        int restDelta = 0;
        for (int i = 0; i < sequence.length; i += 2) {
            int note = sequence[i];
            int duration = sequence[i + 1];
            if (note < 0) {
                // This is a rest
                restDelta += duration;
                lastWasRest = true;
            } else {
                // A note, not a rest
                if (lastWasRest) {
                    noteOn(restDelta, note, velocity, track);
                    noteOff(duration, note, track);
                } else {
                    noteOn(0, note, velocity, track);
                    noteOff(duration, note, track);
                }
                restDelta = 0;
                lastWasRest = false;
            }
        }
    }
    
    public enum Track {
        BASS, CHORDS, MELODY;
        public static int toInt(Track track) {
            switch (track) {
                case CHORDS:
                    return 0;
                case BASS:
                    return 1;
                case MELODY:
                    return 2;
                default:
                    return 0;
            }
        }
    }
}