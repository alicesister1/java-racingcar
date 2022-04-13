package racing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import racing.domain.Car;
import racing.utils.Counter;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CarTest {

    @DisplayName("이름 5글자를 초과하면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"Super Car", "Porsche", "Sports SUV"})
    void carMoveForward(String name) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Car(name));
    }

    @DisplayName("getName()은 자동차 이름을 반환")
    @ParameterizedTest
    @ValueSource(strings = {"carA", "carB", "car!", "1car2"})
    void getName(String name) {
        Car carA = new Car(name);
        assertThat(carA.getName()).isEqualTo(name);
    }

    @DisplayName("자동차의 이동횟수 초기값은 0")
    @Test
    void getCounter() {
        Car carA = new Car("carA");
        assertThat(carA.getCounter()).isEqualTo(new Counter(0));
    }

    @DisplayName("전략에 따라 자동차 전진 또는 멈춤")
    @ParameterizedTest
    @MethodSource("provideSource")
    void carMoveForward(Boolean isMovable, Counter expected) {
        Car carA = new Car("carA");
        carA.run(() -> isMovable);
        assertThat(carA.getCounter()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideSource() {
        return Stream.of(
                Arguments.of(true, new Counter(1)),
                Arguments.of(false, new Counter(0))
        );
    }
}