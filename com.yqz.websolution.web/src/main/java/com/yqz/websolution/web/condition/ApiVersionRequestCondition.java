package com.yqz.websolution.web.condition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;
import org.springframework.web.util.UrlPathHelper;

public final class ApiVersionRequestCondition extends AbstractRequestCondition<ApiVersionRequestCondition> {

	private Set<ApiVersionExpression> versions;

	public ApiVersionRequestCondition(ApiVersionExpression... version) {
		this.versions = Collections.unmodifiableSet(new LinkedHashSet<ApiVersionExpression>(Arrays.asList(version)));
	}

	public ApiVersionRequestCondition(Collection<ApiVersionExpression> versions) {
		if (versions == null)
			this.versions = Collections.unmodifiableSet(new LinkedHashSet<ApiVersionExpression>());
		this.versions = Collections.unmodifiableSet(new LinkedHashSet<ApiVersionExpression>(versions));
	}

	@Override
	public ApiVersionRequestCondition combine(ApiVersionRequestCondition other) {
		Set<ApiVersionExpression> set = new LinkedHashSet<ApiVersionExpression>(this.versions);
		set.addAll(other.versions);
		return new ApiVersionRequestCondition(set);
	}

	@Override
	public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String url = urlPathHelper.getLookupPathForRequest(request);
		Pattern regex = Pattern.compile("^\\/?(\\d{1,2})");
		Matcher m = regex.matcher(url);
		int version = 0;
		if (m.find()) {
			if (m.groupCount() > 0) {
				version = Integer.parseInt(m.group(1));
			}
		}

		ApiVersionExpression annotationVersion = getMaxApiVersionExpression(this.versions);
		if (version == annotationVersion.version)
			return new ApiVersionRequestCondition(annotationVersion);

		return null;
	}

	private ApiVersionExpression getMaxApiVersionExpression(Collection<ApiVersionExpression> collection) {
		Optional<ApiVersionExpression> m = this.versions.stream().max(ApiVersionExpression::compareTo);
		if (!m.isPresent())
			return null;
		return m.get();
	}

	@Override
	public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
		ApiVersionExpression m = getMaxApiVersionExpression(this.versions);
		ApiVersionExpression n = getMaxApiVersionExpression(other.versions);

		if (m != null && n != null) {
			return m.compareTo(n);
		} else if (m == null)
			return 1;
		else if (n == null)
			return -1;
		else
			return 0;

	}

	@Override
	protected Collection<?> getContent() {
		return this.versions;
	}

	@Override
	protected String getToStringInfix() {
		return "||";
	}

	public static class ApiVersionExpression implements Comparable<ApiVersionExpression> {
		private int version;
		private int weight;

		public ApiVersionExpression(int version, int order) {
			this.version = version;
			this.weight = order;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int order) {
			this.weight = order;
		}

		@Override
		public int compareTo(ApiVersionExpression o) {
			if (this.weight == o.weight)
				return this.weight - o.weight;
			else if(this.weight > o.weight)
				return 1;
			return -1;
		}

	}

}
