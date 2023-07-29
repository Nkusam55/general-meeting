package com.generalbody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author narendra kusam
 */


@SpringBootApplication
public class GeneralMeetingApplication extends SpringBootServletInitializer{

	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
	   return application.sources(GeneralMeetingApplication.class);  
	}   
	
	public static void main(String[] args) {
	   SpringApplication.run(GeneralMeetingApplication.class, args);
	}

}
