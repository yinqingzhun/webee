package com.yqz.websolution.web.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestHandlerExceptionResolver extends AbstractHandlerMethodExceptionResolver {
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(RestHandlerExceptionResolver.class);

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception ex) {

		if (request.getHeader("accept").toLowerCase().contains("application/json")) {

		} else if (request.getHeader("accept").toLowerCase().contains("application/xml")) {

		}

		if (ex != null && (AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ResponseBody.class) != null
				|| AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ResponseBody.class) != null)) {
			try {
				HashMap<String, String> map = new HashMap<>();
				map.put("message", ex.getClass().getSimpleName() + " occoured." + ex.getMessage());

				response.getWriter().append(new ObjectMapper().writeValueAsString(map)).flush();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}

		return new ModelAndView();

	}

}
