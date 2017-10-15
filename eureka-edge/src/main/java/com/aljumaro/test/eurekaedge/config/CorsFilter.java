package com.aljumaro.test.eurekaedge.config;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class CorsFilter implements Filter {

	private final Log log = LogFactory.getLog(getClass());

	private final Map<String, List<ServiceInstance>> catalog = new ConcurrentHashMap<>();

	private final DiscoveryClient discoveryClient;

	public CorsFilter(DiscoveryClient discoveryClient) {
		super();
		this.discoveryClient = discoveryClient;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = HttpServletResponse.class.cast(res);
		HttpServletRequest request = HttpServletRequest.class.cast(req);
		String originHeaderValue = originFor(request);
		boolean clientAllowed = isClientAllowed(originHeaderValue);

		if (clientAllowed) {
			response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeaderValue);
		}

		chain.doFilter(req, res);
	}

	private String originFor(HttpServletRequest request) {
		return StringUtils.hasText(request.getHeader(HttpHeaders.ORIGIN)) ? request.getHeader(HttpHeaders.ORIGIN)
				: request.getHeader(HttpHeaders.REFERER);
	}

	private boolean isClientAllowed(String origin) {
		if (StringUtils.hasText(origin)) {
			URI originUri = URI.create(origin);
			int port = originUri.getPort();

			return this.catalog.keySet().stream()
					.anyMatch(matchesServiceURL(originUri.getHost() + ':' + (port <= 0 ? 80 : port)));
		}
		return false;
	}

	private Predicate<? super String> matchesServiceURL(String match) {
		// @formatter:off
		
		return serviceId -> this.catalog.get(serviceId).stream().map(si -> si.getHost() + ':' + si.getPort())
				.anyMatch(hp -> hp.equalsIgnoreCase(match));

		// @formatter:on
	}

	@EventListener(HeartbeatEvent.class)
	void onHeartbeatEvent(HeartbeatEvent event) {
		log.info("Refreshing catalog");
		this.refreshCatalog();
	}

	private void refreshCatalog() {
		discoveryClient.getServices().forEach(svc -> {
			List<ServiceInstance> instances = this.discoveryClient.getInstances(svc);
			instances.stream().map(si -> si.getServiceId()).forEach(log::info);
			this.catalog.put(svc, instances);
		});
	}

	// @formatter:off
	@Override
	public void destroy() {}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	// @formatter:on

}
