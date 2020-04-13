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

/**
 * Main class.
 *
 * @author jTzipi
 */
public final class JaMeLi extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger( "JaMeLi" );


    public static void main( String args[] ) {
        launch( args );
    }

    /**
     * Start method.
     *
     * @param stage main stage
     */
    public void start( final Stage stage ) {


        Scene scene = new Scene( JMainFrame.main(), FXProperties.JMAIN_PANE_WIDTH, FXProperties.JMAIN_PANE_HEIGHT );

        stage.setTitle( "JaMLib" );
        stage.setScene( scene );
        stage.show();
    }
}
