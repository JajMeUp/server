package com.jajteam.jajmeup;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JajMeUp.class})
@ActiveProfiles(profiles = "test")
@Transactional
public abstract class AbstractITest {
}
