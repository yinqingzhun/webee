package com.yqz.websolution.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;

public class RestHandlerExceptionResolver extends AbstractHandlerMethodExceptionResolver {

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception ex) {

		if (request.getHeader("accept").toLowerCase().contains("application/json")) {
			

		} else if (request.getHeader("accept").toLowerCase().contains("application/xml")) {

		}

		return null;

	}

}
