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
            new Expression("*")
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
        for (int i = 0; i < e.length; i++) {
            switch(i) {
                case 0:
                case 2:
                case 3:
                case 6:
                    assertTrue(e[i].isValid());
                    break;
                default:
                    assertFalse(e[i].isValid());
            }
        }
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
    }
}
