package com.unionman.springbootsecurityauth2.config;

import com.unionman.springbootsecurityauth2.domain.CustomUserDetail;
import com.unionman.springbootsecurityauth2.entity.WebUser;
import com.unionman.springbootsecurityauth2.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Zhifeng.Zeng
 * @description Security核心配置
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserMapper userMapper;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return (String username) -> {
            WebUser webUser = userMapper.findWebUserById(username);
            if (webUser != null) {
                CustomUserDetail customUserDetail = new CustomUserDetail();
                customUserDetail.setUserId(webUser.getUserId());
                customUserDetail.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(webUser.getPassword()));
                List<GrantedAuthority> list = AuthorityUtils.createAuthorityList(webUser.getPower()+"");
                customUserDetail.setAuthorities(list);
                log.info("customUserDetail:{}", customUserDetail);
                return customUserDetail;
            } else {//返回空
                return null;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
