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

import groovy.transform.EqualsAndHashCode
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.Nested


@EqualsAndHashCode
class OraDatabase implements Serializable {
    
    @Input
    final String name
    @Input @Optional
    String role
    @Input
    String host
    @Input @Optional
    int port = 1521
    @Input
    String service
    @Input
    String username
    @Input
    String password
    @Input @Optional
    String syspassword

    @Input @Nested @Optional
    transient private Iterable<File> classpath

    /**
     * Serialization version
     */
    private static final long serialVersionUID = 645169464594416268L

    OraDatabase(String name) {
        this.name = name
    }

    void role(String role) {
        this.role = role
    }

    void host(String host) {
        this.host = host
    }

    void port(int port) {
        this.port = port
    }

    void service(String service) {
        this.service = service
    }

    void username(String username) {
        this.username = username
    }

    void password(String password) {
        this.password = password
    }

    void syspassword(String syspassword) {
        this.syspassword = syspassword
    }

    void classpath(Iterable<File> c) {
        this.classpath = c
    }

    def getConnectUrl() {
        "$username/$password@$host:%$port/$service"
    }

    def getSysConnectUrl() {
        "sys/$syspassword@$host:%$port/$service as sysdba"
    }

    def getJdbcUrl() {
        "jdbc:oracle:thin:@$host:$port/$service"
    }

    def getOciUrl() {
        "OCI:$username/$password@$host:$port/$service"
    }

    def getSqlldrHost() {
        "$host:$port/$service"
    }

}