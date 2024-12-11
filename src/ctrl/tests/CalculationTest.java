package ctrl.tests;

import org.junit.jupiter.api.*;
import ctrl.Calculation;
import model.Expression;
import static org.junit.jupiter.api.Assertions.*;

public class CalculationTest {

    Expression exp[];
    Calculation calc[];

    @BeforeEach
    void setup() {
        exp = new Expression[] {
            new Expression("3+5*2"),
            new Expression("8/4-2"),
            new Expression("3+5*2-4"),
            new Expression("35"),
            new Expression("3+5*2-"),
            new Expression("3/0"),
            new Expression("100+100")
        };
        calc = new Calculation[exp.length];
        for (int i = 0; i < exp.length; i++) {
            calc[i] = new Calculation(exp[i]);
        }
    }

    @Test
    void testEvaluate() {
        assertEquals(13, calc[0].evaluate());
        assertEquals(0, calc[1].evaluate());
        assertEquals(9, calc[2].evaluate());
        assertEquals(35, calc[3].evaluate());
        assertThrows(IllegalArgumentException.class, () -> calc[4].evaluate());
        assertThrows(ArithmeticException.class, () -> calc[5].evaluate());
        assertEquals(200, calc[6].evaluate());
    }

    @Test
    void testPostfix() {
        assertArrayEquals(calc[0].postfix().toArray(String[]::new), new String[] { "3", "5", "2", "*", "+" });
        assertArrayEquals(calc[1].postfix().toArray(String[]::new), new String[] { "8", "4", "/", "2", "-" });
        assertArrayEquals(calc[2].postfix().toArray(String[]::new), new String[] { "3", "5", "2", "*", "+", "4", "-" });
        assertArrayEquals(calc[3].postfix().toArray(String[]::new), new String[] { "35" });
        assertThrows(IllegalArgumentException.class, () -> calc[4].postfix());
        assertArrayEquals(calc[5].postfix().toArray(String[]::new), new String[] { "3", "0", "/" });
        assertArrayEquals(calc[6].postfix().toArray(String[]::new), new String[] { "100", "100", "+" });
    }

}
