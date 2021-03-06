/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DynamicTestTests {

	@Test
	void streamFromIterator() throws Throwable {
		List<String> assertedValues = new LinkedList<>();

		Stream<DynamicTest> stream = DynamicTest.stream(Arrays.asList("foo", "bar").iterator(), String::toUpperCase,
			assertedValues::add);
		List<DynamicTest> dynamicTests = stream.collect(Collectors.toList());

		assertThat(dynamicTests).hasSize(2).extracting(DynamicTest::getDisplayName).containsExactly("FOO", "BAR");

		assertThat(assertedValues).isEmpty();
		dynamicTests.get(0).getExecutable().execute();
		assertThat(assertedValues).containsExactly("foo");
		dynamicTests.get(1).getExecutable().execute();
		assertThat(assertedValues).containsExactly("foo", "bar");
	}

}
