/*
 * Copyright 2012-2016 the original author or authors.
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

package org.springframework.boot.test.context.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTestContextBootstrapper} (in its own package so we can test
 * detection).
 *
 * @author Phillip Webb
 */
@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class SpringBootTestContextBootstrapperTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private SpringBootTestContextBootstrapperExampleConfig config;

	public boolean defaultTestExecutionListenersPostProcessorCalled;

	@Test
	public void findConfigAutomatically() throws Exception {
		assertThat(this.config).isNotNull();
	}

	@Test
	public void contextWasCreatedViaSpringApplication() throws Exception {
		assertThat(this.context.getId()).startsWith("application:");
	}

	@Test
	public void testConfigurationWasApplied() throws Exception {
		assertThat(this.context.getBean(ExampleBean.class)).isNotNull();
	}

	@Test
	public void defaultTestExecutionListenersPostProcessorShouldBeCalled()
			throws Exception {
		assertThat(this.defaultTestExecutionListenersPostProcessorCalled).isTrue();
	}

	@TestConfiguration
	static class TestConfig {

		@Bean
		public ExampleBean exampleBean() {
			return new ExampleBean();
		}

	}

	static class ExampleBean {

	}

	@Component
	static class ExampleTestComponent {

	}

}
