package ch.hearc.hef1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HeF1ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void simpleTest() {
		new TestAssertTrue().testAssertTrue();
	}

}
