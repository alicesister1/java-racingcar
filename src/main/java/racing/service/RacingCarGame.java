package racing.service;

import racing.domain.Car;
import racing.domain.Cars;
import racing.domain.Position;
import racing.domain.strategies.CarMoveStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class RacingCarGame {
    private static final int MINIMUM_NUMBER_OF_MOVES = 1;
    private static final String EXCEPTION_MOVES_MESSAGE = "이동횟수는 %d 이상이어야 합니다";
    private static final String EXCEPTION_NAMES_MESSAGE = "자동차 이름은 필수값입니다";

    private Cars cars;

    private Cars makeCars(List<String> nameOfCars) {
        return new Cars(nameOfCars);
    }

    public void run(int numMoves, List<String> nameOfCars, CarMoveStrategy moveStrategy) {
        if (numMoves < MINIMUM_NUMBER_OF_MOVES) {
            throw new IllegalArgumentException(String.format(EXCEPTION_MOVES_MESSAGE, MINIMUM_NUMBER_OF_MOVES));
        }

        if (nameOfCars == null || nameOfCars.isEmpty()) {
            throw new IllegalArgumentException(EXCEPTION_NAMES_MESSAGE);
        }

        cars = makeCars(nameOfCars);
        cars.run(moveStrategy);
    }

    public List<String> getWinner() {
        Position maxPosition = cars.getMaxPosition();
        return cars.getCarsEqualsPosition(maxPosition)
                .stream().map(Car::getName)
                .collect(Collectors.toList());
    }

    public Cars getCars() {
        return cars;
    }
}
