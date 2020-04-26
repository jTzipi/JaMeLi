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

import earth.eu.jtzipi.jameli.tree.PathTreeItem;
import earth.eu.jtzipi.modules.io.IOUtils;
import earth.eu.jtzipi.modules.node.path.IPathNode;
import earth.eu.jtzipi.modules.node.path.RegularPathNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.nio.file.Path;

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
     * Directory currently viewed.
     */
    public static final ObjectProperty<IPathNode> FX_CURRENT_DIR_PATH = new SimpleObjectProperty<>();

    /**
     * Root path.
     */
    public static final Path ROOT_PATH = IOUtils.getHomeDir();
    /**
     * Root node.
     */
    public static final IPathNode ROOT_PATH_NODE = RegularPathNode.of( ROOT_PATH, null );
    /**
     * Root Tree Item.
     */
    public static final TreeItem<IPathNode> ROOT_TREE_ITEM = PathTreeItem.of( ROOT_PATH_NODE );

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
