package com.olamide.startup_progress_tracker.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.olamide.startup_progress_tracker.exceptions.EntityNotFoundException;
import com.olamide.startup_progress_tracker.utils.ResponseHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private ResponseHandler responseHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException exception) {
            responseHandler.notFound(response, "User does not exist");
        } catch (JWTVerificationException exception) {
            responseHandler.forbidden(response, "Invalid jwt token");
        } catch (RuntimeException exception) {
            responseHandler.badRequest(response, "Bad request, please try again");
        }
    }
}
