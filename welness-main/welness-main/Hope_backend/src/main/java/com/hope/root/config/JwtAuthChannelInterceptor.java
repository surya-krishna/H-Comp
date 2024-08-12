package com.hope.root.config;

import static com.hope.root.model.Constants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtAuthChannelInterceptor implements ChannelInterceptor {

	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
    	System.out.println("===============authToken****************");
    	StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        
        String token = accessor.getFirstNativeHeader("Token");
        
        String username=null;
        if (token != null) {
            
            System.out.println("===============authToken"+token);
            try {
            	token = token.replace(TOKEN_PREFIX,"");
                username = jwtTokenUtil.getUsernameFromToken(token);
                System.out.println("===============authToken ====="+ username);
            } catch (IllegalArgumentException e) {
            	System.out.println("an error occured during getting username from token"+ e);
            } catch (ExpiredJwtException e) {
            	System.out.println("the token is expired and not valid anymore"+ e);
            } catch(SignatureException e){
            	System.out.println("Authentication Failed. Username or Password not valid.");
            }
        } else {
        	System.out.println("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, userDetails)) {
            	
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                System.out.println("authenticated user " + username + ", setting security context"+authentication);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication);
            }
        }


        return message;
    }

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preReceive(MessageChannel channel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
