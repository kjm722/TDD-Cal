import org.example.SimpleCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleCalculatorTest {
    SimpleCalculator simpleCalculator = new SimpleCalculator();

    @Test
    @DisplayName("((3 + 5) * 5 + -10) * 10 / 5")
    public void t1(){
        int re = simpleCalculator.run(3,5,10);

        assertThat(re).isEqualTo(60);
    }
}
