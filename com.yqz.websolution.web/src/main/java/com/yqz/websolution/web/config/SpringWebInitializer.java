package com.yqz.websolution.web.config;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.yqz.websolution.web.filter.CaseInsensitiveRequestParameterNameFilter;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { SpringRootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		Dynamic filter = servletContext.addFilter(CaseInsensitiveRequestParameterNameFilter.class.getSimpleName(),
				CaseInsensitiveRequestParameterNameFilter.class);
		filter.addMappingForUrlPatterns(null, true, "/*");

	}

}
