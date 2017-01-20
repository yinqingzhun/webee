package com.yqz.websolution.web.config;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yqz.websolution.web.annotation.ApiVersion;
import com.yqz.websolution.web.condition.ApiVersionRequestCondition;
import com.yqz.websolution.web.condition.ApiVersionRequestCondition.ApiVersionExpression;
import com.yqz.websolution.web.handler.SignatureHandlerInterceptor;

@Configuration
@ComponentScan(value = "com.yqz.websolution.web", useDefaultFilters = false, includeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
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
				return new ApiVersionRequestCondition(new ApiVersionExpression(v.value(), v.order()));
			}

			@Override
			protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
				ApiVersion v = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
				if (v == null)
					return null;
				return new ApiVersionRequestCondition(new ApiVersionExpression(v.value(), v.order()));
			}
		};
		AntPathMatcher pathMatcher = new AntPathMatcher();
		pathMatcher.setCaseSensitive(false);
		m.setPathMatcher(pathMatcher);
		m.setInterceptors(new SignatureHandlerInterceptor());
		return m;
	}

	@Bean
	public PreferencesPlaceholderConfigurer preferencesPlaceholderConfigurer() {
		PreferencesPlaceholderConfigurer c = new PreferencesPlaceholderConfigurer();
		c.setLocations(new ClassPathResource("database.properties"));
		return c;
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
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));

		JacksonXmlModule module = new JacksonXmlModule();
		// and then configure, for example:
		module.setDefaultUseWrapper(false);
		XmlMapper xmlMapper = new XmlMapper(module);

		converters.add(new MappingJackson2XmlHttpMessageConverter(xmlMapper));
		// converters.add(new Jaxb2RootElementHttpMessageConverter());
	}

}
