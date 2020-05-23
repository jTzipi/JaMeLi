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
import earth.eu.jtzipi.modules.node.path.IPathNode;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Properties shared.
 *
 * @author jTzipi
 */
public final class FXProperties {

    /**
     * Main Frame width.
     * Fallback if ui.properties is not readable.
     */
    public static final double JMAIN_PANE_WIDTH = 1000D;
    /**
     * Main Frame height .
     * Fallback if ui.properties is not readable.
     */
    public static final double JMAIN_PANE_HEIGHT = 770D; // 750

    /**
     * Main stage.
     */
    private static Stage MAIN_STAGE;

    /**
     * Filter of directory tree.
     */
    public static ObjectProperty<Predicate<IPathNode>> FX_PATH_NODE_FILTER_PROP = new SimpleObjectProperty<>( IPathNode.ACCEPT_DIR_VISIBLE_NO_LINK );

    /**
     * Directory currently viewed.
     */
    public static final ObjectProperty<IPathNode> FX_CURRENT_DIR_PATH = new SimpleObjectProperty<>();

    /**
     * Root path.
     */
    public static final Path ROOT_PATH = IOUtils.getHomeDir();


    /**
     * Default 3px inset.
     */
    public static final DoubleProperty FX_DEF_INSET_PROP = new SimpleDoubleProperty( 3D );

    /**
     * Set main stage.
     *
     * @param stage stage
     */
    static void setMainStage( Stage stage ) {
        MAIN_STAGE = stage;
    }

    /**
     * Return primary stage.
     *
     * @return main stage
     */
    public static Stage getPrimaryStage() {
        return MAIN_STAGE;
    }

    /**
     * Access forbidden.
     */
    private FXProperties() {
        // NO Access
        throw new AssertionError( "Fault!" );
    }
}
