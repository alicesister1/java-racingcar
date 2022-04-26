package racing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racing.domain.strategies.CarMoveStrategy;
import racing.domain.strategies.CarMoveStrategyImpl;
import racing.domain.strategies.CustomRandomImpl;
import racing.service.RacingCarGame;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RacingCarGameTest {

    private CarMoveStrategy createMoveStrategy() {
        return new CarMoveStrategyImpl(new CustomRandomImpl());
    }

    @DisplayName("이동횟수를 1 보다 작게 입력하면 예외를 던진다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void runThrowException(int numberOfMoves) {
        RacingCarGame game = new RacingCarGame();
        List<String> carNames = List.of("carA", "carB");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> game.run(numberOfMoves, carNames, createMoveStrategy()))
                .withMessageMatching("이동횟수는 \\d 이상이어야 합니다");
    }

    @DisplayName("정상적으로 게임 실행 시 우승 자동차 1대 이상")
    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void testGameRun(int numberOfMoves) {
        List<String> names = List.of("carA", "carB");
        RacingCarGame racingCarGame = new RacingCarGame();
        racingCarGame.run(numberOfMoves, names, createMoveStrategy());
        List<String> winner = racingCarGame.getWinner();
        assertThat(winner).hasSizeGreaterThan(0);
    }
}