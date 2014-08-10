package rekkyn.musicgen;

public class Reference {
    public enum Root {
        Ab(8), A(9), As(10), Bb(10), B(11), C(0), Cs(1), Db(1), D(2), Ds(3), Eb(3), E(4), F(5), Fs(6), Gb(6), G(7), Gs(8);
        
        public int num;
        
        Root(int num) {
            this.num = num;
        }
    }
    
    public static final class Length {
        public static final int SIXTEENTH = 4;
        public static final int EIGHTH = 8;
        public static final int QUARTER = 16;
        public static final int HALF = 32;
        public static final int WHOLE = 64;
    }
    
}
