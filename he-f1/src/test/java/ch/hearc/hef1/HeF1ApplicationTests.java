package ch.hearc.hef1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:test.properties")
class HeF1ApplicationTests {

	@Test
	void contextLoads() {
		new TestAssertTrue().testAssertTrue();
	}

}
