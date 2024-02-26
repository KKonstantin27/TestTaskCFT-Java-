package utils;

import enums.TypeOfLine;

public class LineTypeUtil {
    public TypeOfLine defineTypeOfLine(String line) {
        if (isFloat(line)) {
            return TypeOfLine.FLOAT;
        } else if (isInteger(line)) {
            return TypeOfLine.INTEGER;
        } else {
            return TypeOfLine.STRING;
        }
    }

    private boolean isFloat(String line) {
        try {
            if (line.contains(".")) {
                Double.parseDouble(line);
                return true;
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
