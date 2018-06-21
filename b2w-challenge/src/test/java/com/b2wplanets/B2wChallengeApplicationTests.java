package com.b2wplanets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan({"java.controller"})
public class B2wChallengeApplicationTests {

	@Test
	public void contextLoads() {
	}

}
