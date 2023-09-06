package com.cokkiri.secondhand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.cokkiri.secondhand.global.auth.config.SecurityConfig;
import com.cokkiri.secondhand.global.auth.filter.JwtAuthorizationFilter;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtAuthHttpResponseManager;
import com.cokkiri.secondhand.global.exception.CustomExceptionHttpStatusCodeFactory;
import com.cokkiri.secondhand.global.exception.GlobalExceptionHandler;
import com.cokkiri.secondhand.user.controller.MyLocationController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan({"customExceptionHttpStatusCodeFactory", "globalExceptionHandler"})
@AutoConfigureMockMvc(addFilters = false)
@Import({CustomExceptionHttpStatusCodeFactory.class, GlobalExceptionHandler.class})
@WebMvcTest(controllers = MyLocationController.class
	, excludeFilters =
	{@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = {SecurityConfig.class, JwtAuthorizationFilter.class,
			JwtAuthHttpResponseManager.class
		})
	})
public @interface ApiTest {
}
