package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.chars.CharacterMatrix;

public class Solution14 implements Solution<CharacterMatrix, Integer> {

    @Override
    public Integer solveP1(CharacterMatrix input) throws ImplementationException {
        return calculateLoad(tilt(input));
    }

    @Override
    public Integer solveP2(CharacterMatrix input) throws ImplementationException {
        var matrix = input;
        for(int i = 0; i < 1_000_000_000; i++) {
            var spinned = spinAndTilt(matrix);
            if(spinned.equals(matrix)) {
                break;
            }
            matrix = spinned;
            if(i % 1_000_000 == 0) {
                System.out.println(i + " cycles done...");
            }
        }
        return calculateLoad(matrix);
    }

    static int calculateLoad(CharacterMatrix matrix) {
        return matrix.stream()
                .mapToInt(val -> val.value() == 'O' ? matrix.length - val.coordinates().y() : 0)
                .sum();
    }

    static CharacterMatrix spinAndTilt(CharacterMatrix matrix) {
        var northTilt = tilt(matrix);
        var westTilt = tilt(northTilt.spin90degClockwise());
        var southTilt = tilt(westTilt.spin90degClockwise());
        var eastTilt = tilt(southTilt.spin90degClockwise());
        return eastTilt.spin90degClockwise();
    }

    static CharacterMatrix tilt(CharacterMatrix matrix) {
        var newMatrix = matrix.copy();
        for(int y = 0; y < newMatrix.length; y++) {
            for(int x = 0; x < newMatrix.width; x++) {
                char c = newMatrix.at(x, y);
                if(y > 0 && c == 'O') {
                    for(int i = y - 1; i >= 0; i--) {
                        char above = newMatrix.at(x, i);
                        if(i == 0 && above == '.') {
                            newMatrix = newMatrix.replaceCharAt(x, y, '.');
                            newMatrix = newMatrix.replaceCharAt(x, i, 'O');
                            break;
                        }
                        if(above == '#' || above == 'O') {
                            newMatrix = newMatrix.replaceCharAt(x, y, '.');
                            newMatrix = newMatrix.replaceCharAt(x, i + 1, 'O');
                            break;
                        }
                    }
                }
            }
        }
        return newMatrix;
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
