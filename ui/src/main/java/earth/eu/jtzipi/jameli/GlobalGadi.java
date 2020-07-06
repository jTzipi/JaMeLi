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

package earth.eu.jtzipi.jameli;


import earth.eu.jtzipi.modules.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Global JaMeLi Property Enum.
 */
enum GlobalGadi {

    /**
     * Date and Time user started JaMeLi last.
     */
    INIT_LAST_START( "jameli.init.laststart", "null" ),
    /**
     * Database init.
     */
    INIT_DB_SETUP( "jameli.init.db", "false" ),
    ;


    private static final Path path = IOUtils.getProgramDir().resolve( "gadi.properties" );
    /**
     * Global Properties.
     */
    private static final Properties GADI_PROP = new Properties();
    private static final DateTimeFormatter GADI_FORMATTER = DateTimeFormatter.ofPattern( "dd.MM.yyyy HHmm" );
    private static final String JAMELI_USER = "JaMeLi";
    private static final String JAMELI_PATH = IOUtils.getProgramDir().resolve( "res/db/jamelidb" ).toString();
    private static final Logger LG = LoggerFactory.getLogger( GlobalGadi.class );
    final String gkey;
    final String gval;

    /**
     * View of 'gadi.properties' file.
     *
     * @param keyStr key
     * @param value  default value
     */
    GlobalGadi( final String keyStr, final String value ) {
        this.gkey = keyStr;
        this.gval = value;
    }

    /**
     * Database init script.
     *
     * @return init
     */
    static boolean initDatabase() {
        LG.info( "... Init DB" );
        boolean ret = false;
// TODO: read from properties
        String dbURL = "jdbc:h2:" + JAMELI_PATH;

        try ( Connection c = DriverManager.getConnection( dbURL, "sa", "" ) ) {

            Statement stmt = c.createStatement();
            LG.info( "Connected!" );

            boolean gadi = stmt.execute( "CREATE USER IF NOT EXISTS " + JAMELI_USER + " PASSWORD '$Gadi!Super' ADMIN" );
            LG.info( ">> Root User created " + gadi );
            boolean created = stmt.execute( "CREATE SCHEMA IF NOT EXISTS JAMELI AUTHORIZATION JaMeLi" );
            LG.info( ">> DB created " + created );

            ret = true;
        } catch ( final SQLException sqlE ) {


            LG.error( "Error loading db", sqlE.getCause() );

        }

        return ret;
    }

    /**
     * Create a new "gadi.properties".
     *
     * @throws IOException failed
     */
    private static void createProperties() throws IOException {
        LG.warn( "CREATE NEW GADI.PROPERIES FILE" );
        Files.createFile( path );
        List<CharSequence> gadiL = new ArrayList<>();

        for ( GlobalGadi globalGadi : GlobalGadi.values() ) {
            String entry = globalGadi.gkey + "=" + globalGadi.gval + "\n";
            gadiL.add( entry );
        }

        Files.write( path, gadiL, StandardOpenOption.WRITE );

    }

    private static void storeProperties() {

        try ( BufferedWriter buff = Files.newBufferedWriter( path, StandardOpenOption.WRITE ) ) {
            GADI_PROP.store( buff, ">> GADI JAMELI " );
        } catch ( final IOException ioE ) {

            LG.error( "Store failed", ioE );
        }
    }

    static void loadProperties() throws IOException {


        // properties not ?
        // if so create new one
        // TODO: check whether DB is found
        if ( !Files.isReadable( path ) ) {
            LG.warn( "No gadi properties? " + Files.exists( path ) );
            createProperties();
            LG.info( ">> gadi.properties created" );
        }
        IOUtils.loadProperties( path, GADI_PROP );

    }

    static void check() throws IOException {

        boolean dbInit = Boolean.parseBoolean( GADI_PROP.getProperty( INIT_DB_SETUP.getGkey() ) );


        // if no db init create new db
        if ( !dbInit ) {


            if ( initDatabase() ) {

                GADI_PROP.setProperty( INIT_DB_SETUP.getGkey(), "true" );
            } else {
                throw new IOException( "Failed to create database" );
            }
        }
        // create hibernate session


        LocalDateTime now = LocalDateTime.now();
        GADI_PROP.setProperty( INIT_LAST_START.getGkey(), GADI_FORMATTER.format( now ) );
    }

    public static void init() throws IOException {


        loadProperties();
        LG.info( "Properties loaded" );
        LG.info( "Check global properties" );
        check();
        LG.info( "Store" );
        storeProperties();


    }

    public String getGkey() {
        return gkey;
    }
}
