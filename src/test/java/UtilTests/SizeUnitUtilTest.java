package UtilTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.SizeUnitUtil;

public class SizeUnitUtilTest {
    private final SizeUnitUtil sizeUnitUtil = new SizeUnitUtil();

    @Test
    public void convertToBiggerUnitsShouldReturnRightString() {
        Assertions.assertEquals("0 Б", sizeUnitUtil.convertToBiggerUnits(0));
        Assertions.assertEquals("123 Б", sizeUnitUtil.convertToBiggerUnits(123));
        Assertions.assertEquals("12,06 КБ", sizeUnitUtil.convertToBiggerUnits(12345));
        Assertions.assertEquals("1,18 МБ", sizeUnitUtil.convertToBiggerUnits(1234567));
        Assertions.assertEquals("1,15 ГБ", sizeUnitUtil.convertToBiggerUnits(1234567890));
    }
}
