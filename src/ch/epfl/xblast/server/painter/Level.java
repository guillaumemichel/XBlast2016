package ch.epfl.xblast.server.painter;

import ch.epfl.xblast.server.GameState;

public final class Level {
    private final BoardPainter boardPainter;
    private final GameState gamestate;
    
    public Level(BoardPainter boardPainter, GameState gamestate){
        this.boardPainter=boardPainter;
        this.gamestate=gamestate;
    }

}
