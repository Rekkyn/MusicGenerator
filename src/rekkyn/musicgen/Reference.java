package rekkyn.musicgen;

public class Reference {
    public enum Root {
        Ab(8), A(9), As(10), Bb(10), B(11), C(0), Cs(1), Db(1), D(2), Ds(3), Eb(3), E(4), F(5), Fs(6), Gb(6), G(7), Gs(8);
        
        public int num;
        
        Root(int num) {
            this.num = num;
        }
        
        public static Root getRootFromNum(int num) {
            while (num >= 12)
                num -= 12;
            switch (num) {
                case 0:
                    return C;
                case 1:
                    return Cs;
                case 2:
                    return D;
                case 3:
                    return Ds;
                case 4:
                    return E;
                case 5:
                    return F;
                case 6:
                    return Fs;
                case 7:
                    return G;
                case 8:
                    return Gs;
                case 9:
                    return A;
                case 10:
                    return As;
                case 11:
                    return B;
            }
            return null;
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
