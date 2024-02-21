package utils;

public class LineTypeUtil {
    public boolean isFloat(String line) {
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

    public boolean isInteger(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
