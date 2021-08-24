package com.moneystats.MoneyStats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = MoneyStatsApplication.class)
class MoneyStatsApplicationTests {

	@Test
	void contextLoads() {
	}

}
