package com.jajteam.jajmeup;

import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.security.JwtAuthenticationToken;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JajMeUp.class})
@ActiveProfiles(profiles = "test")
@Transactional
public abstract class AbstractITest {

    public void authenticateUser(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(user, authorities));
    }
}
