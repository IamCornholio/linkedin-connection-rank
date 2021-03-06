package com.cornholio.linkedin.connectionrank.service;

import com.cornholio.linkedin.connectionrank.request.LinkedInRequest;
import com.cornholio.linkedin.connectionrank.service.token.TokenService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author nikhilagarwal
 */
public class LinkedInServiceBuilder {

	private final LinkedInService linkedInService;
	private final List<LinkedInRequest> requests;
	private final Set<String> scopes;
	private LinkedInServiceBuilder() {
		super();
		this.linkedInService = new LinkedInService();
		this.scopes = new HashSet<>();
		this.requests = new ArrayList<>();
	}

	public static LinkedInServiceBuilder newLinkedInService() {
		return new LinkedInServiceBuilder();
	}

	private String getScope() {
		final StringBuilder stringBuilder = new StringBuilder();
		for (final String scope : this.scopes) {
			stringBuilder.append(scope).append(",");
		}

		return stringBuilder.substring(0, stringBuilder.length() - 1);
	}

	public void signAllRequests() throws Exception {
		this.linkedInService.initiateService(this.getScope());
		this.requests.forEach(this.linkedInService::signRequest);
	}

	public <T extends LinkedInRequest> LinkedInServiceBuilder with(final T linkedInRequest) {
		this.requests.add(linkedInRequest);
		this.scopes.add(linkedInRequest.getScope());
		return this;
	}

	public LinkedInServiceBuilder with(final TokenService tokenService) {
		this.linkedInService.setTokenService(tokenService);
		return this;
	}
}