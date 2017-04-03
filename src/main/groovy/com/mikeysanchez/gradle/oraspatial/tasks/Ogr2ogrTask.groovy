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

package com.mikeysanchez.gradle.oraspatial.tasks

import com.mikeysanchez.gradle.oraspatial.OraDatabase
import org.gradle.api.tasks.*

abstract class Ogr2ogrTask extends Exec {
    @Input @Nested
    OraDatabase oradb
    @Input @Optional
    String layerName
    @Input @Optional
    String layerOwner
    @Input @Optional
    Boolean showProgress = true
    @Input @Optional
    Boolean overWrite = false
    @Input @Optional
    Boolean skipFailures = false

    Ogr2ogrTask() {
        executable = 'ogr2ogr'
    }

    abstract void constructExecArgs()

    @TaskAction
    void exec() {
        constructExecArgs()
        super.exec()
    }
}
