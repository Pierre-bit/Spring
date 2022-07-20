package fr.orsys.fx.calendrier_gif.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class CheckSessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// on test si l'url debute par /calendrier et alors que la session Http contient
		// bien un objet utilisateur
		// dans la négative on redirige vers le filtre suivant
		// dans l'affirmative on invoque le filtre suivant ou le controlleur final
		if (((HttpServletRequest) request).getRequestURI().startsWith("/calendrier")
				&& ((HttpServletRequest) request).getSession().getAttribute("utilisateur") == null) {
			System.out.println("Pas de session " + new Date());
			((HttpServletResponse) response).sendRedirect("/index");
		}

		else {
			chain.doFilter(request, response);
		}

	}

}
