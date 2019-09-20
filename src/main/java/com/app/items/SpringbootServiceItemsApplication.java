package com.app.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RibbonClient(name = "service-product")
@EnableFeignClients
@SpringBootApplication
public class SpringbootServiceItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceItemsApplication.class, args);
	}

}
