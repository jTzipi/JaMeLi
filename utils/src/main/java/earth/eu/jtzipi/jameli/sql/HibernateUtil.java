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

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

/**
 * Hibernate Utils.
 */
public enum HibernateUtil {
    /**
     * Singleton.
     */
    SINGLETON;


    private static SessionFactory sf;

    public static void getSeFa( Properties sql, char[] pw ) {
        Objects.requireNonNull( sql );
        if ( null == sf ) {

            sql.setProperty( Environment.PASS, String.copyValueOf( pw ) );
            sf = createSeFa( sql );


            Arrays.fill( pw, '!' );
        }
    }

    private static SessionFactory createSeFa( final Properties prop ) {

        Configuration cf = new Configuration();


        cf.setProperties( prop );


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings( cf.getProperties() ).build();

        return cf.buildSessionFactory( serviceRegistry );

    }
}
