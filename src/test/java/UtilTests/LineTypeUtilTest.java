package UtilTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.LineTypeUtil;

public class LineTypeUtilTest {
    private final LineTypeUtil lineTypeUtil = new LineTypeUtil();

    @Test
    public void isFloatsShouldReturnTrueWhenArgumentIsFloat() {
        Assertions.assertTrue(lineTypeUtil.isFloat("3.1415"));
        Assertions.assertTrue(lineTypeUtil.isFloat(".1415"));
        Assertions.assertTrue(lineTypeUtil.isFloat("1.528535047E-25"));
        Assertions.assertTrue(lineTypeUtil.isFloat("-1.528535047E-25"));
        Assertions.assertTrue(lineTypeUtil.isFloat("0.0"));
    }

    @Test
    public void isFloatsShouldReturnFalseWhenArgumentIsNotFloat() {
        Assertions.assertFalse(lineTypeUtil.isFloat("12345"));
        Assertions.assertFalse(lineTypeUtil.isFloat("0"));
        Assertions.assertFalse(lineTypeUtil.isFloat("1234567890123456789"));
        Assertions.assertFalse(lineTypeUtil.isFloat("Test String"));
        Assertions.assertFalse(lineTypeUtil.isFloat(""));
        Assertions.assertFalse(lineTypeUtil.isFloat(" "));
        Assertions.assertFalse(lineTypeUtil.isFloat("/?{"));
        Assertions.assertFalse(lineTypeUtil.isFloat("."));
    }

    @Test
    public void isIntegerShouldReturnTrueWhenArgumentIsInteger() {
        Assertions.assertTrue(lineTypeUtil.isInteger("1234567890123456789"));
        Assertions.assertTrue(lineTypeUtil.isInteger("-1234567890123456789"));
        Assertions.assertTrue(lineTypeUtil.isInteger("12345"));
        Assertions.assertTrue(lineTypeUtil.isInteger("0"));
    }

    @Test
    public void isIntegerShouldReturnFalseWhenArgumentIsNotInteger() {
        Assertions.assertFalse(lineTypeUtil.isInteger("3.1415"));
        Assertions.assertFalse(lineTypeUtil.isInteger(".12345"));
        Assertions.assertFalse(lineTypeUtil.isInteger("0.0"));
        Assertions.assertFalse(lineTypeUtil.isInteger("1.528535047E-25"));
        Assertions.assertFalse(lineTypeUtil.isInteger("Test String"));
        Assertions.assertFalse(lineTypeUtil.isInteger(""));
        Assertions.assertFalse(lineTypeUtil.isInteger(" "));
        Assertions.assertFalse(lineTypeUtil.isInteger("/?{"));
        Assertions.assertFalse(lineTypeUtil.isInteger("."));
    }
}
