package chess.piece.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.chess.board.ChessBoard;
import spring.chess.board.ChessBoardCreater;
import spring.chess.board.Route;
import spring.chess.location.Location;
import spring.chess.piece.type.Knight;
import spring.chess.team.Team;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class KnightTest {
    @Test
    @DisplayName("갈 수 있는 곳 테스트")
    void canMove1() {
        ChessBoard chessBoard = ChessBoardCreater.create();

        Knight knight = new Knight(Team.BLACK);

        Location now = new Location(8, 'g');
        Location leftAfter = new Location(7, 'e');

        Route route = new Route(Collections.EMPTY_MAP, now, leftAfter);
        boolean leftAfterActual = knight.canMove(route);
        assertThat(leftAfterActual).isTrue();
    }

    @Test
    @DisplayName("갈 수 있는 곳 테스트")
    void canMove2() {
        ChessBoard chessBoard = ChessBoardCreater.create();

        Knight knight = new Knight(Team.BLACK);

        Location now = new Location(8, 'g');
        Location rightAfter = new Location(6, 'h');

        Route route = new Route(Collections.EMPTY_MAP, now, rightAfter);

        boolean rightAfterActual = knight.canMove(route);
        assertThat(rightAfterActual).isTrue();
    }

    @Test
    @DisplayName("갈 수 없는 곳 테스트")
    void canMove() {
        ChessBoard chessBoard = ChessBoardCreater.create();

        Knight knight = new Knight(Team.BLACK);

        Location now = new Location(8, 'g');
        Location cantAfter = new Location(2, 'c');

        Route route = new Route(Collections.EMPTY_MAP, now, cantAfter);
        boolean cantActual = knight.canMove(route);
        assertThat(cantActual).isFalse();
    }

}