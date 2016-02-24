package ch.epfl.xblast;

public enum Direction {
    W, E, S, N;
    
    public Direction opposite(){
        switch (this){
            case W:
                return E;
            case E:
                return W;
            case S:
                return N;
            case N:
                return S;
            default:
                return null;
        }
    }
    
    public boolean isHorizontal(){
        return (this==E||this==W);
    }
    
    public boolean isParallelTo(Direction that){
        return (this==that||this==that.opposite());
    }
}

