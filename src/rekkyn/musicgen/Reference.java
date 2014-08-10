package rekkyn.musicgen;

public class Reference {
    public enum Root {
        Ab(8), A(9), As(10), Bb(10), B(11), C(0), Cs(1), Db(1), D(2), Ds(3), Eb(3), E(4), F(5), Fs(6), Gb(6), G(7), Gs(8);
        
        public int num;
        
        Root(int num) {
            this.num = num;
        }
    }
    
    public enum Length {
        SIXTEENTH(4), EIGHTH(8), QUARTER(16), HALF(32), WHOLE(64);
        
        public final int lengthInt;
        
        Length(int length) {
            lengthInt = length;
        }
    }
    
}
