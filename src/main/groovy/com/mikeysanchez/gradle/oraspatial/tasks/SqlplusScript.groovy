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
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.Nested
import org.gradle.util.CollectionUtils


class SqlplusScript extends Exec {
    @Input @Nested
    OraDatabase oradb
    @Input @Optional
    Boolean runAsSys = false
    @InputFile
    File scriptFile
    @Optional @OutputFile
    File logFile
    @Nested
    private List<Object> scriptArgs = []

    SqlplusScript() {
        executable = 'sqlplus'
    }

    void scriptFile(File file) {
        this.scriptFile = file
    }

    void scriptFile(String file) {
        scriptFile(new File(workingDir, file))
    }

    @Input @Optional
    List<String> getScriptArgs() {
        CollectionUtils.stringize(
                this.scriptArgs.collect { it ->
                    it instanceof Closure ? (it as Closure).call() : it
                } .flatten()
        )
    }

    void setScriptArgs(Object... args) {
        this.scriptArgs.clear()
        this.scriptArgs.addAll(args as List)
    }

    void scriptArgs(Object... args) {
        this.scriptArgs.addAll(args as List)
    }

    void logFile(File file) {
        if (file) {
            doFirst {
                standardOutput = new FileOutputStream(file.getPath())
                errorOutput = new FileOutputStream(file.getPath() + '.err')
            }
        }
    }

    void logFile(String file) {
        if (file) {
            logFile(new File(workingDir, file))
        }
    }

    private void constructExecArgs() {
        def execArgs = ["-S", (runAsSys ? oradb.sysConnectUrl : oradb.connectUrl)]
        execArgs.add('@' + scriptFile.getPath())
        if (scriptArgs) {
            execArgs.addAll(scriptArgs)
        }
        args = execArgs
        logger.debug("executable = $executable ${args.join(" ")}")
    }

    @TaskAction
    void exec() {
        constructExecArgs()
        super.exec()
    }
}
