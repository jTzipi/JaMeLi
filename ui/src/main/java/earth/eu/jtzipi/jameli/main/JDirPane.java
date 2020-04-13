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

package earth.eu.jtzipi.jameli.main;

import earth.eu.jtzipi.jameli.FXProperties;
import earth.eu.jtzipi.jameli.tree.PathTreeCell;
import earth.eu.jtzipi.modules.node.path.IPathNode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Directory Tree Pane.
 */
public final class JDirPane extends Pane {

    private static final Logger LOG = LoggerFactory.getLogger( "JDirPane!" );

    TreeView<IPathNode> dirTreeView;


    JDirPane() {

        createJDirPane();
    }

    private void createJDirPane() {

        dirTreeView = new TreeView<>( FXProperties.ROOT_TREE_ITEM );
        dirTreeView.setCellFactory( ( cb ) -> new PathTreeCell() );
        dirTreeView.getSelectionModel().selectedItemProperty().addListener( ( ( observableValue, oldValue, newValue ) -> onPathTreeSelectionChanged( oldValue, newValue ) ) );


        getChildren().add( dirTreeView );


    }

    private void onPathTreeSelectionChanged( TreeItem<IPathNode> oldNode, TreeItem<IPathNode> newNode ) {

        // we did not select node before or new node is null
        // return
        if ( null == oldNode || null == newNode ) {

            LOG.debug( "Old Path[" + oldNode + "] or new Path[" + newNode + "] is null onPathTreeSelectionChanged" );

            return;
        }


        IPathNode newPathNode = newNode.getValue();
        LOG.warn( "TP Changed from " + oldNode.getValue() + " > " + newPathNode );

        FXProperties.FX_CURRENT_DIR_PATH.setValue( newPathNode );
    }
}
