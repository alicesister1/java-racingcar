package racing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racing.domain.Car;
import racing.domain.Cars;
import racing.domain.Position;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CarsTest {

    private Cars testCars = null;

    @BeforeEach
    void setUp() {
        List<String> names = IntStream.range(0, 3)
                .mapToObj(i -> String.format("car%d", i))
                .collect(Collectors.toList());
        testCars = new Cars(names);
    }

    @DisplayName("자동차들은 이동후 Position 증가한다")
    @Test
    void carMoveForward() {
        testCars.run(new CarHaveToMoveStrategyImpl());
        assertThat(testCars.getCars()).allMatch(car -> car.getPosition().getCurrentPosition() == 1);
    }

    @DisplayName("특정 위치에 해당하는 자동차를 반환한다")
    @Test
    void getCarsEqualsPositionTest() {
        testCars.run(new CarHaveToMoveStrategyImpl());
        assertThat(testCars.getCarsEqualsPosition(new Position(1))).hasSize(3);
    }

    @DisplayName("제일 많이 전진한 자동차의 위치 가져온다")
    @Test
    void getMaxPositionTest() {
        List<Car> cars = testCars.getCars();
        Car car = cars.get(cars.size() - 1);
        for (int i = 0; i < 2; i++) {
            car.run(new CarHaveToMoveStrategyImpl());
        }

        assertThat(testCars.getMaxPosition().getCurrentPosition()).isEqualTo(2);
    }
}