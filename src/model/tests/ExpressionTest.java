package model.tests;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.*;

import model.Expression;
import static model.Expression.*;

public class ExpressionTest {

    Expression[] e;

    @BeforeEach
    void setup() {
        e = new Expression[] {
            new Expression("3+5*2"), //0
            new Expression("3**5"),
            new Expression("3+5*2-4"),
            new Expression("35"),
            new Expression("3+5*2-"),
            new Expression("/34+3"), //5
            new Expression("-34+3"),
            new Expression(),
            new Expression("+"),
            new Expression("*"),
            new Expression("3.4"), //10
            new Expression("3..2"),
            new Expression("."),
            new Expression("4.3.2"),
            new Expression("3.4+2"),
            new Expression("33.4+24.3"), //15
            new Expression("()"),
            new Expression(")"),
            new Expression("(3+2)*10"),
            new Expression("(4*3)(4)"),
            new Expression("((2)"), //20
            new Expression("(4+3)+2*5)"),
            new Expression("(*33)"),
            new Expression("3^7")
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

        str = "a";
        assertFalse(e[0].add(str));
        assertTrue("3+5*23+".equals(e[0].toString()));
        assertFalse(e[1].add(str));
        assertTrue("3**53+".equals(e[1].toString()));

        str = ".";
        assertTrue(e[0].add(str));
        assertTrue("3+5*23+0.".equals(e[0].toString()));

        e[0].add("4");

        str = "(";
        assertTrue(e[0].add(str));
        assertTrue("3+5*23+0.4*(".equals(e[0].toString()));

        e[0].add("3*2");

        str = ")";
        assertTrue(e[0].add(str));
        assertTrue("3+5*23+0.4*(3*2)".equals(e[0].toString()));
    }

    @Test
    void testPop() {
        assertTrue(e[0].pop());
        assertTrue("3+5*".equals(e[0].toString()));
        assertTrue(e[0].pop());
        assertTrue("3+5".equals(e[0].toString()));
        assertTrue(e[0].pop());
        assertTrue("3+".equals(e[0].toString()));
        assertTrue(e[0].pop());
        assertTrue("3".equals(e[0].toString()));
        assertTrue(e[0].pop());
        assertTrue("".equals(e[0].toString()));
        assertFalse(e[0].pop());
        assertTrue("".equals(e[0].toString()));
        assertFalse(e[7].pop());
        assertTrue("".equals(e[7].toString()));
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
        assertFalse(e[16].isValid());
        assertFalse(e[17].isValid());
        assertTrue(e[18].isValid());
        assertFalse(e[19].isValid());
        assertFalse(e[20].isValid());
        assertFalse(e[21].isValid());
        assertFalse(e[22].isValid());
        assertTrue(e[23].isValid());
    }

    @Test
    void testGetSegments() {
        //cases with exceptions are tested in testIsValid(). See above
        Function<Expression, String[]> toArray = exp -> exp.getSegments().toArray(String[]::new);
        assertArrayEquals(new String[] {"3", "+", "5", "*", "2"}, toArray.apply(e[0]));
        assertArrayEquals(new String[] {"3", "+", "5", "*", "2", "-", "4"}, toArray.apply(e[2]));
        assertArrayEquals(new String[] {"35"}, toArray.apply(e[3]));
        assertArrayEquals(new String[] {"-", "34", "+", "3"}, toArray.apply(e[6]));
        assertArrayEquals(new String[] {"3.4"}, toArray.apply(e[10]));
        assertArrayEquals(new String[] {"3.4", "+", "2"}, toArray.apply(e[14]));
        assertArrayEquals(new String[] {"33.4", "+", "24.3"}, toArray.apply(e[15]));
        assertArrayEquals(new String[] {"(", "3", "+", "2", ")", "*", "10"}, toArray.apply(e[18]));
        assertArrayEquals(new String[] {"3", "^", "7"}, toArray.apply(e[23]));

        assertThrows(IllegalArgumentException.class, () -> e[1].getSegments());
    }

    @Test
    void testIsOperator() {
        assertTrue(isOperator("+"));
        assertTrue(isOperator("-"));
        assertTrue(isOperator("*"));
        assertTrue(isOperator("/"));
        assertTrue(isOperator("^"));
        assertFalse(isOperator("3"));
        assertFalse(isOperator("**"));
    }

    @Test
    void testValidSeg() {
        assertTrue(validSeg("()53749"));
        assertTrue(validSeg(")"));
        assertTrue(validSeg("0"));
        assertFalse(validSeg("a1243"));
        assertFalse(validSeg("0_2"));
        assertTrue(validSeg("."));
        assertTrue(validSeg("-"));
        assertTrue(validSeg("/"));
        assertFalse(validSeg("\\"));
        assertTrue(validSeg("(3+3)*2/4-7/4"));
    }
}
