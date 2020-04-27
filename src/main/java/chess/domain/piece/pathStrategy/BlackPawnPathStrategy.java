package chess.domain.piece.pathStrategy;

import static chess.domain.piece.direction.Direction.*;
import static chess.util.NullValidator.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.domain.board.Position;
import chess.domain.piece.direction.Direction;
import chess.exception.NotMovableException;

public class BlackPawnPathStrategy extends PathStrategy {
	private static final int NO_DISTANCE = 0;
	private static final int FORWARD_MIN_DISTANCE = -1;
	private static final int FORWARD_MAX_DISTANCE = -2;
	private static final int RIGHT_MAX_DISTANCE = 1;
	private static final int LEFT_MAX_DISTANCE = -1;
	private static final List<Direction> BLACK_PAWN_DIRECTIONS = Arrays.asList(SOUTH, SOUTHEAST, SOUTHWEST);

	@Override
	public void validateDistance(Position sourcePosition, Position targetPosition) {
		validateNull(sourcePosition, targetPosition);

		if (sourcePosition.isYPointEqualsSeven()) {
			if (isInvalidForwardPosition(sourcePosition, targetPosition) && isInvalidDiagonalPosition(sourcePosition,
				targetPosition)) {
				throw new NotMovableException(String.format("지정한 위치 %s는 검은색 폰이 이동할 수 없는 곳입니다.",
					targetPosition.getName()));
			}
			return;
		}

		if (isInvalidXPointGap(sourcePosition, targetPosition) || !sourcePosition.hasYGap(targetPosition,
			FORWARD_MIN_DISTANCE)) {
			throw new NotMovableException(String.format("지정한 위치 %s는 검은색 폰이 이동할 수 없는 곳입니다.",
				targetPosition.getName()));
		}
	}

	private boolean isInvalidForwardPosition(Position sourcePosition, Position targetPosition) {
		return !(sourcePosition.hasXGap(targetPosition, NO_DISTANCE) &&
			(sourcePosition.hasYGap(targetPosition, FORWARD_MIN_DISTANCE) ||
				(sourcePosition.hasYGap(targetPosition, FORWARD_MAX_DISTANCE))));
	}

	private boolean isInvalidDiagonalPosition(Position sourcePosition, Position targetPosition) {
		return !((sourcePosition.hasXGap(targetPosition, RIGHT_MAX_DISTANCE) ||
			sourcePosition.hasXGap(targetPosition, LEFT_MAX_DISTANCE) &&
				(sourcePosition.hasYGap(targetPosition, FORWARD_MIN_DISTANCE))));
	}

	private boolean isInvalidXPointGap(Position sourcePosition, Position targetPosition) {
		return !(sourcePosition.hasXGap(targetPosition, NO_DISTANCE) ||
			sourcePosition.hasXGap(targetPosition, LEFT_MAX_DISTANCE) ||
			sourcePosition.hasXGap(targetPosition, RIGHT_MAX_DISTANCE));
	}

	@Override
	public List<Position> findPaths(Position sourcePosition, Position targetPosition) {
		validateNull(sourcePosition, targetPosition);

		Direction direction = getDirection(sourcePosition, targetPosition);
		if (sourcePosition.isYPointEqualsSeven() && direction.isSouth()) {
			return createFirstPath(sourcePosition, targetPosition, direction);
		}

		List<Position> path = new ArrayList<>();
		path.add(targetPosition);
		return path;
	}

	private Direction getDirection(Position sourcePosition, Position targetPosition) {
		int xPointDirectionValue = sourcePosition.getXPointDirectionValueTo(targetPosition);
		int yPointDirectionValue = sourcePosition.getYPointDirectionValueTo(targetPosition);
		Direction direction = Direction.of(xPointDirectionValue, yPointDirectionValue);

		if (!BLACK_PAWN_DIRECTIONS.contains(direction)) {
			throw new NotMovableException(String.format("지정한 위치 %s는 검은색 폰이 이동할 수 없는 방향입니다.",
				targetPosition.getName()));
		}

		return direction;
	}

	private List<Position> createFirstPath(Position sourcePosition, Position targetPosition, Direction direction) {
		List<Position> firstPath = new ArrayList<>();

		Position currentPosition = sourcePosition;
		while (!currentPosition.equals(targetPosition)) {
			Position changePosition = currentPosition.changeTo(direction);
			currentPosition = changePosition;
			firstPath.add(changePosition);
		}

		return firstPath;
	}
}
