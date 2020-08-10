package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InstanceInformationService {

	private static final String ENV_INSTANCE_GUID = "WEBSITE_INSTANCE_ID";

	private static final String DEFAULT_ENV_INSTANCE_GUID = "UNKNOWN";

	//@Value(${ENVIRONMENT_VARIABLE_NAME:DEFAULT_VALUE})
	@Value("${" + ENV_INSTANCE_GUID + ":" + DEFAULT_ENV_INSTANCE_GUID + "}")
	private String instanceGuid;

	public String retrieveInstanceInfo() {
		return instanceGuid;
	}

}