package com.sunkara.inv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.sunkara.inv.repository.mongodb")
@Configuration
public class MongoDBConfig {

}
