package com.yqz.websolution.web.config;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yqz.websolution.web.annotation.ApiVersion;
import com.yqz.websolution.web.condition.ApiVersionRequestCondition;
import com.yqz.websolution.web.condition.ApiVersionRequestCondition.ApiVersionExpression;
import com.yqz.websolution.web.handler.RestHandlerExceptionResolver;
import com.yqz.websolution.web.handler.SignatureHandlerInterceptor;

@Configuration
@ComponentScan(value = "com.yqz.websolution.web")
public class SpringWebConfig extends WebMvcConfigurationSupport {

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/views/");
		vr.setSuffix(".jsp");
		vr.setContentType("text/html;charset=utf-8");

		return vr;
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping m = new RequestMappingHandlerMapping() {

			@Override
			protected RequestCondition<?> getCustomMethodCondition(Method method) {
				ApiVersion v = AnnotationUtils.findAnnotation(method, ApiVersion.class);
				if (v == null)
					return null;
				return new ApiVersionRequestCondition(new ApiVersionExpression(v.value(), v.weight()));
			}

			@Override
			protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
				ApiVersion v = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
				if (v == null)
					return null;
				return new ApiVersionRequestCondition(new ApiVersionExpression(v.value(), v.weight()));
			}
		};
		AntPathMatcher pathMatcher = new AntPathMatcher();
		pathMatcher.setCaseSensitive(false);
		m.setPathMatcher(pathMatcher);
		m.setInterceptors(new SignatureHandlerInterceptor());
		return m;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		r.addStatusCode("error500", 500);
		r.setDefaultStatusCode(500);
		r.setDefaultErrorView("error500");
		return r;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// objectMapper.configOverride(java.util.Date.class)
		// .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd"));
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));

		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper xmlMapper = new XmlMapper(module);
		xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		converters.add(new MappingJackson2XmlHttpMessageConverter(xmlMapper));
	}

	@Override
	protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new ExceptionHandlerExceptionResolver());
		exceptionResolvers.add(new RestHandlerExceptionResolver());
		exceptionResolvers.add(new DefaultHandlerExceptionResolver());
		exceptionResolvers.add(new SimpleMappingExceptionResolver());
	}
}
