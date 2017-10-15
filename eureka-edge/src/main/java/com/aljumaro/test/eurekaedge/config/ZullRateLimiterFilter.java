package com.aljumaro.test.eurekaedge.config;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZullRateLimiterFilter extends ZuulFilter {

	private static final HttpStatus TOO_MANY_REQUESTS = HttpStatus.TOO_MANY_REQUESTS;
	private final RateLimiter rateLimiter;

	public ZullRateLimiterFilter(RateLimiter rateLimiter) {
		super();
		this.rateLimiter = rateLimiter;
	}

	@Override
	public Object run() {
		try {
			RequestContext currentContext = RequestContext.getCurrentContext();
			HttpServletResponse response = currentContext.getResponse();

			if (!rateLimiter.tryAcquire()) {
				String reasonPhrase = TOO_MANY_REQUESTS.getReasonPhrase();
				int value = TOO_MANY_REQUESTS.value();

				response.setContentType(MediaType.TEXT_PLAIN);
				response.setStatus(value);

				currentContext.setSendZuulResponse(false);

				throw new ZuulException(reasonPhrase, value, reasonPhrase);
			}
		} catch (Exception e) {
			ReflectionUtils.rethrowRuntimeException(e);
		}

		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
