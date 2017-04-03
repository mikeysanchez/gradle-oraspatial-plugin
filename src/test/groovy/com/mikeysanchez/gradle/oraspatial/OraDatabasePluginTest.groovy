/*Copyright 2017 Mikey Sanchez
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
*/

package com.mikeysanchez.gradle.oraspatial

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project

class OraDatabasePluginTest extends GroovyTestCase {
    void testAddOraDatabaseExtension() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.mikeysanchez.oraspatial'
        project.evaluate()

        assert project.oradb instanceof OraDatabaseExtension
    }
}
