package com.hope.root.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hope.root.config.TokenProvider;
import com.hope.root.model.AuthToken;
import com.hope.root.model.LoginUser;
import com.hope.root.model.UserDetails;
import com.hope.root.service.RootService;
import com.hope.root.service.impl.RootServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private RootService userService;

    Logger logger  = LoggerFactory.getLogger(AuthenticationController.class);; 
    		
    
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) throws AuthenticationException {
    	logger.info("Inside login");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        logger.info("After authentiation");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        UserDetails details = (UserDetails)authentication.getPrincipal();
        logger.info("End Login");
        return ResponseEntity.ok(new AuthToken(token,details.getRole(),details.getUsername()));
    }

}
