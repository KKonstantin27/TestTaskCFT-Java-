package utils;

import java.text.DecimalFormat;

public class SizeUnitUtil {
    public String convertToBiggerUnits(long size) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        double convertedSize;

        if (size >= 0 && size < 1024) {
            return size + " Б";
        } else if (size >= 1024 && size < 1048576) {
            convertedSize = size / 1024.0;
            return df.format(convertedSize) + " КБ";
        } else if (size >= 1048576 && size < 1073741824) {
            convertedSize = size / 1048576.0;
            return df.format(convertedSize) + " МБ";
        } else {
            convertedSize = size / 1073741824.0;
            return df.format(convertedSize) + " ГБ";
        }
    }
}
