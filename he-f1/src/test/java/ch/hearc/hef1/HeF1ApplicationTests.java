package ch.hearc.hef1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
class HeF1ApplicationTests {

	@Test
	void contextLoads() {
		new TestAssertTrue().testAssertTrue();
	}

}
