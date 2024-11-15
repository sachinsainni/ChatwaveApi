package com.sachin.socket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import com.sachin.security.JwtTokenUtil;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, 
                                   ServerHttpResponse response, 
                                   WebSocketHandler wsHandler, 
                                   Map<String, Object> attributes) throws Exception {
        // Extract JWT token from query parameters
        String query = request.getURI().getQuery();
        System.out.println("Query String: " + query);  // Debugging line

        if (query != null) {
            // Use URLDecoder to properly decode the query string
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("token=")) {
                    String token = param.substring(6); // Get the token value
                    System.out.println("Extracted Token: " + token);  // Debugging line

                    // Validate JWT
                    if (jwtTokenUtil.validateToken(token)) {
                        String username = jwtTokenUtil.getUsernameFromToken(token);
                        attributes.put("username", username);
                        return true;  // Allow the handshake
                    }
                }
            }
        }

        // If token is invalid or missing, reject the handshake
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return false;
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, 
                               ServerHttpResponse response, 
                               WebSocketHandler wsHandler, 
                               @Nullable Exception exception) {
        // This can remain empty unless you need to handle post-handshake actions
    }
}
