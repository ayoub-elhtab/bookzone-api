package com.ayoub.BookZone.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "BookZone REST API",
				description = "The BookZone REST API provides a comprehensive solution for managing a collection of books. It supports full CRUD operations—creation, retrieval, updating and deletion."
		),
		servers = {
				@Server(
						description = "Local Development Environment",
						url = "http://localhost:8081"
				)
		}
)
public class OpenApiConfig {

}
