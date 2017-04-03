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

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional

class Tab2Shp extends Ogr2ogrTask {
    @InputFile
    File tabFile
    @Input @Optional
    File shpFile


    Tab2Shp() {
        group = 'Converting'
        super
    }

    void tabFile(File file) {
        tabFile = file
    }

    void tabFile(String file) {
        tabFile(new File(file))
    }

    void shpFile(File file) {
        shpFile = file
    }

    void shpFile(String file) {
        shpFile(new File(file))
    }

    void constructExecArgs() {
        def allArgs = ['-f', '"ESRI Shapefile"']
        def shpFile = this.shpFile ?: tabFile.getPath().replace('.tab','.shp')
        allArgs.add(shpFile)
        allArgs.add(tabFile.getPath())
        if (showProgress) {
            allArgs.add('-progress')
        }
        if (overWrite) {
            allArgs.add('-overwrite')
        }

        args = allArgs

        this.shpFile(shpFile)
    }
}
