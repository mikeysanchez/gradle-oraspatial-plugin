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
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.com.esotericsoftware.kryo.io.Output

class ShpToOra extends JavaExec {
    @Input @Nested
    OraDatabase oradb
    @InputFile
    File shpFile
    @Input @Optional
    String tableName
    @Input @Optional
    String srid = '8307'
    @Input @Optional
    String geomColumn = 'geom'
    @Input @Optional
    String tolerance = '0.5'
    @Input @Optional
    String verbosity = '100000'

    ShpToOra() {
        main = 'oracle.spatial.util.SampleShapefileToJGeomFeature'
        group = 'Loading'
    }

    void shpFile(File file) {
        shpFile = file
    }

    void shpFile(String file) {
        shpFile(new File(file))
    }

    void shpFile(Object... files) {
        println('-------------------')
        println('Setting the shpFile using a FileCollection')
        println('-------------------')
        shpFile(files.first())
    }

    private void constructClassArgs() {
        def allArgs = ['-h', oradb.host]
        allArgs += ['-p', oradb.port]
        allArgs += ['-sn', oradb.service]
        allArgs += ['-u', oradb.username]
        allArgs += ['-d', oradb.password]
        allArgs += ['-f', shpFile.getPath().replace('.shp', '')]
        allArgs += ['-t', (tableName ?: shpFile.getName().replace('.shp', ''))]
        allArgs += ['-r', srid]
        allArgs += ['-g', geomColumn]
        allArgs += ['-o', tolerance]
        allArgs += ['-v', verbosity]
        args = allArgs
        logger.info("$main ${args.join(" ")}")
    }

    @TaskAction
    void exec() {
        constructClassArgs()
        super.exec()
    }
}
