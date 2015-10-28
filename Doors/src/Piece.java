
/**
 * The game piece class.
 */

///many many things could belong here for easiness, but maybe it'll end up slower.

public class Piece {

    private PieceEnum color; //
    private boolean inPlay; //if true the piece is in play, otherwise it's "on hand" waiting to be in play again.

    public Piece (PieceEnum color){
        this.color = color;
        this.inPlay = true;
    }

    public PieceEnum getColor() {
        return color;
    }

    public boolean isInPlay() {
        return inPlay;
    }

    public void setColor(PieceEnum color) {
        this.color = color;
    }
}
