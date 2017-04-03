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

class Tab2Ora extends Ogr2ogrTask {
    @InputFile
    File tabFile
    @Input @Optional
    def srid = 'WGS84'
    @Input @Optional
    String geomColumn = 'geom'
    @Input @Optional
    Boolean validate = true

    Tab2Ora() {
        super
    }

    void tabFile(File file) {
        tabFile = file
    }

    void tabFile(String file) {
        tabFile(new File(file))
    }

    void constructExecArgs() {
        def allArgs = ['-f', 'OCI']
        // specifying the layer in the connection string should speed up the processing time
        // because the query agains ALL_SDO_GEOM_METADATA will filter for just this table
        // NOTE: this will throw an ignorable exception "ORA-04043: object <layer_name> does not exist" if it doesn't yet
        allArgs.add(oradb.ociUrl + ':' + (layerOwner ? layerOwner + '.' + layerName : layerName))
        allArgs.add(tabFile.getPath())
        allArgs.add('-nln')
        allArgs.add((layerOwner ? layerOwner + '.' + layerName : layerName))
        allArgs += ['-t_srs',srid]
        allArgs += ['-geomfield',geomColumn]
        if (showProgress) {
            allArgs.add('-progress')
        }
        if (overWrite) {
            allArgs.add('-overwrite')
        }
        if (skipFailures) {
            allArgs.add('-skipfailures')
        }

        args = allArgs
        logger.info(executable + args.join(" "))
    }
}
