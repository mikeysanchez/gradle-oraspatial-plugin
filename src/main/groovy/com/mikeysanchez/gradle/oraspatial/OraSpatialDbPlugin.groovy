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

import org.gradle.api.NamedDomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

class OraSpatialDbPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // create a NamedDomainObjectContainer to enable creation of DSL
        NamedDomainObjectCollection<OraDatabase> oradbs = project.container(OraDatabase)

        // create and install the extension object
        project.getExtensions().create("oradb", OraDatabaseExtension, oradbs)
        project.convention.plugins.oradb = new OraDatabaseConvention(oradbs)

    }
}
