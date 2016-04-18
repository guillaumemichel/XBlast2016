package ch.epfl.xblast.server.painter;

import java.util.Objects;

import ch.epfl.xblast.server.GameState;

public final class Level {
    private final BoardPainter boardPainter;
    private final GameState gamestate;
    
    public static final Level DEFAULT_LEVEL=null;//a faire wtf
    
    public Level(BoardPainter boardPainter, GameState gamestate) throws NullPointerException{
        this.boardPainter=Objects.requireNonNull(boardPainter);
        this.gamestate=Objects.requireNonNull(gamestate);
    }
    
    public BoardPainter boardPainter(){
        return boardPainter;
    }
    
    public GameState gameState(){
        return gamestate;
    }
    
    

}
