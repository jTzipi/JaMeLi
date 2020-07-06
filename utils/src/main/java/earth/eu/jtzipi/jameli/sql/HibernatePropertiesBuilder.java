/*
 * Copyright (c) 2020 Tim Langhammer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package earth.eu.jtzipi.jameli.sql;

import earth.eu.jtzipi.modules.utils.IBuilder;

import java.util.Objects;
import java.util.Properties;

public final class HibernatePropertiesBuilder implements IBuilder<Properties> {


    private final Sql sql;
    private String url;
    private String user;

    private HibernatePropertiesBuilder( Sql sql ) {
        this.sql = sql;
    }

    public static HibernatePropertiesBuilder ofMySQL() {
        return new HibernatePropertiesBuilder( Sql.MYSQL );
    }

    public HibernatePropertiesBuilder user( final String user ) {
        this.user = Objects.requireNonNull( user );
        return this;
    }

    @Override
    public Properties build() {
        return null;
    }

    /**
     * SQL static properties.
     */
    enum Sql {
        H2SQL( "org.hibernate.dialect.H2Dialect", "org.h2.driver" ),

        MYSQL( "org.hibernate.dialect.MySQL5Dialect", "com.mysql.cj.jdbc.drver" ),

        POSTGRESQL( "", "" );
        private final String dt;
        private final String dr;

        Sql( String sqlDtc, String sqlDrv ) {
            this.dt = sqlDtc;
            this.dr = sqlDrv;
        }
    }


}
