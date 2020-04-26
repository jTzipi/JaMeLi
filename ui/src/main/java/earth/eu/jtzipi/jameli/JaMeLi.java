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

import earth.eu.jtzipi.jameli.main.JMainFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main class.
 *
 * @author jTzipi
 */
public final class JaMeLi extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger( "JaMeLi" );

    /**
     * Main function.
     *
     * @param args arguments [Not used]
     */
    public static void main( String[] args ) {
        launch( args );
    }

    /**
     * Start method.
     * Flow:
     * <ol>
     * <li>First we store the stage.
     * <li>Then we init resource properties.
     * <li>Then we load all resources.
     * <li>Then we start GUI.
     * </ol>
     * In case of error during resource init we shut down.
     *
     * @param stage main stage
     */
    public void start( final Stage stage ) {
        FXProperties.setMainStage( stage );

        try {
            Resources.init();
            Resources.loadResources().thenRun( this::initStage );
        } catch ( IOException e ) {
            LOGGER.error( "[?] could not init", e );
            System.exit( 2 );
        }

    }

    /**
     * Init main .
     * TODO: read size from Properties
     * read misc from Prop
     */
    private void initStage() {
        Stage stage = FXProperties.getPrimaryStage();
        Scene scene = new Scene( JMainFrame.main(), FXProperties.JMAIN_PANE_WIDTH, FXProperties.JMAIN_PANE_HEIGHT );

        stage.setTitle( "JaMLib" );
        stage.setScene( scene );
        stage.show();
    }
}
