package ch.hearc.hef1;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestAssertTrue {

    @Test
    public void testAssertTrue() {
        Assertions.assertTrue(true);
    }
}
