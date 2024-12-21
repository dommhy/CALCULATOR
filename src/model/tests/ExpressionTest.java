package model.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import model.Expression;

public class ExpressionTest {

    Expression[] e;

    @BeforeEach
    void setup() {
        e = new Expression[] {
            new Expression("3+5*2"),
            new Expression("3**5"),
            new Expression("3+5*2-4"),
            new Expression("35"),
            new Expression("3+5*2-"),
            new Expression("/34+3"),
            new Expression("-34+3"),
            new Expression(),
            new Expression("+"),
            new Expression("*"),
            new Expression("3.4"),
            new Expression("3..2"),
            new Expression("."),
            new Expression("4.3.2"),
            new Expression("3.4+2"),
            new Expression("33.4+24.3")
        };
    }

    @Test
    void testAdd() {
        String str = "3";
        assertTrue(e[0].add(str));
        assertTrue("3+5*23".equals(e[0].toString()));
        assertTrue(e[1].add(str));
        assertTrue("3**53".equals(e[1].toString()));
        assertTrue(e[7].add(str));
        assertTrue("3".equals(e[7].toString()));

        str = "+";
        assertTrue(e[0].add(str));
        assertTrue("3+5*23+".equals(e[0].toString()));
        assertTrue(e[1].add(str));
        assertTrue("3**53+".equals(e[1].toString()));
        assertTrue(e[7].add(str));
        assertTrue("3+".equals(e[7].toString()));

        str = "a";
        assertFalse(e[0].add(str));
        assertTrue("3+5*23+".equals(e[0].toString()));
        assertFalse(e[1].add(str));
        assertTrue("3**53+".equals(e[1].toString()));
        assertFalse(e[7].add(str));
        assertTrue("3+".equals(e[7].toString()));
    }

    @Test
    void testClear() {
        e[0].clear();
        assertTrue(e[0].toString().isEmpty());
        e[1].clear();
        assertTrue(e[1].toString().isEmpty());
        e[7].clear();
        assertTrue(e[7].toString().isEmpty());
    }

    @Test
    void testIsValid() {
        assertTrue(e[0].isValid());
        assertFalse(e[1].isValid());
        assertTrue(e[2].isValid());
        assertTrue(e[3].isValid());
        assertFalse(e[4].isValid());
        assertFalse(e[5].isValid());
        assertTrue(e[6].isValid());
        assertFalse(e[7].isValid());
        assertFalse(e[8].isValid());
        assertFalse(e[9].isValid());
        assertTrue(e[10].isValid());
        assertFalse(e[11].isValid());
        assertFalse(e[12].isValid());
        assertFalse(e[13].isValid());
        assertTrue(e[14].isValid());
        assertTrue(e[15].isValid());
    }

    @Test
    void testGetSegments() {
        assertArrayEquals(new String[] {"3", "+", "5", "*", "2"}, e[0].getSegments().toArray(String[]::new));
        assertThrows(IllegalArgumentException.class, () -> e[1].getSegments());
        assertArrayEquals(new String[] {"3", "+", "5", "*", "2", "-", "4"}, e[2].getSegments().toArray(String[]::new));
        assertArrayEquals(new String[] {"35"}, e[3].getSegments().toArray(String[]::new));
        assertThrows(IllegalArgumentException.class, () -> e[4].getSegments());
        assertThrows(IllegalArgumentException.class, () -> e[5].getSegments());
        assertArrayEquals(new String[] {"-", "34", "+", "3"}, e[6].getSegments().toArray(String[]::new));
        assertThrows(IllegalArgumentException.class, () -> e[7].getSegments());
        assertThrows(IllegalArgumentException.class, () -> e[8].getSegments());
        assertThrows(IllegalArgumentException.class, () -> e[9].getSegments());
        assertArrayEquals(new String[] {"3.4"}, e[10].getSegments().toArray(String[]::new));
        assertThrows(IllegalArgumentException.class, () -> e[11].getSegments());
        assertThrows(IllegalArgumentException.class, () -> e[12].getSegments());
        assertThrows(IllegalArgumentException.class, () -> e[13].getSegments());
        assertArrayEquals(new String[] {"3.4", "+", "2"}, e[14].getSegments().toArray(String[]::new));
        assertArrayEquals(new String[] {"33.4", "+", "24.3"}, e[15].getSegments().toArray(String[]::new));
    }
}
