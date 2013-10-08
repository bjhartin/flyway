/**
 * Copyright 2010-2013 Axel Fontaine and the many contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.flyway.maven;

import com.googlecode.flyway.core.util.logging.Log;
import com.googlecode.flyway.core.util.logging.LogCreator;
import org.apache.maven.plugin.AbstractMojo;

/**
 * Log Creator for Maven Logging.
 */
public class MavenLogCreator implements LogCreator {
    /**
     * The Maven Mojo to log for.
     */
    private final AbstractMojo mojo;

    /**
     * Creates a new Maven Log Creator for this Mojo.
     *
     * @param mojo The Maven Mojo to log for.
     */
    public MavenLogCreator(AbstractMojo mojo) {
        this.mojo = mojo;
    }

    public Log createLogger(Class<?> clazz) {
        return new MavenLog(mojo.getLog());
    }
}
