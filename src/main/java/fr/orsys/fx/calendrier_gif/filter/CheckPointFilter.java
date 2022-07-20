package fr.orsys.fx.calendrier_gif.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CheckPointFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("ok " +new Date());
		// le filtre passe au filtre suivant ou au controller
		long msDepart = new Date().getTime();
        request.setAttribute("msDepart", msDepart);
		chain.doFilter(request, response);
		
	}

	
	
}
