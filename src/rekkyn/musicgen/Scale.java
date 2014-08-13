package rekkyn.musicgen;

public class Scale {
    public static final int[] MAJOR = new int[] { 0, 2, 4, 5, 7, 9, 11 };
    public static final int[] MINOR = new int[] { 0, 2, 3, 5, 7, 8, 10 };
    
    public enum Position {
        I(0), II(1), III(2), IV(3), V(4), VI(5), VII(6);
        
        public int position;
        
        private Position(int position) {
            this.position = position;
        }
    }
}