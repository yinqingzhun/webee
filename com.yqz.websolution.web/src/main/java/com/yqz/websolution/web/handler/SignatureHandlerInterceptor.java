package com.yqz.websolution.web.handler;

import java.net.InetAddress;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SignatureHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String remoteIp = request.getRemoteHost();
		InetAddress localAddress = InetAddress.getLocalHost();

		if (remoteIp.equals("127.0.0.1") || remoteIp.equals("localhost") || remoteIp.equals("0:0:0:0:0:0:0:1")
				|| localAddress.getHostAddress().equals(remoteIp)
				|| Arrays.stream(InetAddress.getAllByName(InetAddress.getLocalHost().getHostName()))
						.anyMatch(p -> p.getHostAddress().equals(remoteIp)))
			return true;

		if (!StringUtils.hasText(request.getParameter("sign"))) {
			response.setStatus(400);
			response.getWriter().append("no sign");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
