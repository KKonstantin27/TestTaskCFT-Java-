package utilTests;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.LineTypeUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineTypeUtilTest {
    private final LineTypeUtil lineTypeUtil = new LineTypeUtil();

    @Test
    @Order(1)
    public void isFloatsShouldReturnTrueWhenArgumentIsFloat() {
        assertTrue(lineTypeUtil.isFloat("3.1415"));
        assertTrue(lineTypeUtil.isFloat(".1415"));
        assertTrue(lineTypeUtil.isFloat("1.528535047E-25"));
        assertTrue(lineTypeUtil.isFloat("-1.528535047E-25"));
        assertTrue(lineTypeUtil.isFloat("0.0"));
    }

    @Test
    @Order(2)
    public void isFloatsShouldReturnFalseWhenArgumentIsNotFloat() {
        assertFalse(lineTypeUtil.isFloat("12345"));
        assertFalse(lineTypeUtil.isFloat("0"));
        assertFalse(lineTypeUtil.isFloat("1234567890123456789"));
        assertFalse(lineTypeUtil.isFloat("Test String"));
        assertFalse(lineTypeUtil.isFloat(""));
        assertFalse(lineTypeUtil.isFloat(" "));
        assertFalse(lineTypeUtil.isFloat("/?{"));
        assertFalse(lineTypeUtil.isFloat("."));
    }

    @Test
    @Order(3)
    public void isIntegerShouldReturnTrueWhenArgumentIsInteger() {
        assertTrue(lineTypeUtil.isInteger("1234567890123456789"));
        assertTrue(lineTypeUtil.isInteger("-1234567890123456789"));
        assertTrue(lineTypeUtil.isInteger("12345"));
        assertTrue(lineTypeUtil.isInteger("0"));
    }

    @Test
    @Order(4)
    public void isIntegerShouldReturnFalseWhenArgumentIsNotInteger() {
        assertFalse(lineTypeUtil.isInteger("3.1415"));
        assertFalse(lineTypeUtil.isInteger(".12345"));
        assertFalse(lineTypeUtil.isInteger("0.0"));
        assertFalse(lineTypeUtil.isInteger("1.528535047E-25"));
        assertFalse(lineTypeUtil.isInteger("Test String"));
        assertFalse(lineTypeUtil.isInteger(""));
        assertFalse(lineTypeUtil.isInteger(" "));
        assertFalse(lineTypeUtil.isInteger("/?{"));
        assertFalse(lineTypeUtil.isInteger("."));
    }
}
