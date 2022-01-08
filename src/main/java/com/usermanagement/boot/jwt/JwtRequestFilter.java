package com.usermanagement.boot.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		final String headerJWT =  request.getHeader("Authorization");
		
		String username =null;
		String jwt = null;
		
		if(headerJWT != null && headerJWT.startsWith("Bearer ")) {
			jwt =  headerJWT.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		 if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	            if (jwtUtil.validateToken(jwt, userDetails)) {
//	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//	                        userDetails, null, userDetails.getAuthorities());
//	                usernamePasswordAuthenticationToken
//	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            	
	            	   UsernamePasswordAuthenticationToken authentication = jwtUtil.getAuthentication(jwt, SecurityContextHolder.getContext().getAuthentication(), userDetails);
	                   //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	                   authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                 
	                   SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        }
		 filterChain.doFilter(request, response);
	}

}
