/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dj.xtool.net.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which indicates that a method parameter should be bound to a web request header.
 * Supported for annotated handler methods in Servlet and Portlet environments.
 *
 * <p>If the method parameter is {@link java.util.Map Map&lt;String, String&gt;} or
 * {@link org.springframework.util.MultiValueMap MultiValueMap&lt;String, String&gt;},
 * or {@link org.springframework.http.HttpHeaders HttpHeaders} then the map is
 * populated with all header names and values.
 *
 * @author Juergen Hoeller
 * @since 3.0
 * see RequestMapping
 * see RequestParam
 * see CookieValue
 * see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 * see org.springframework.web.portlet.mvc.annotation.AnnotationMethodHandlerAdapter
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {

	/**
	 * The name of the request header to bind to.
	 */
	String value() default "";

	/**
	 * Whether the header is required.
	 * <p>Default is {@code true}, leading to an exception thrown in case
	 * of the header missing in the request. Switch this to {@code false}
	 * if you prefer a {@code null} in case of the header missing.
	 * <p>Alternatively, provide a {@link #defaultValue}, which implicitly sets
	 * this flag to {@code false}.
	 */
	boolean required() default true;



}
