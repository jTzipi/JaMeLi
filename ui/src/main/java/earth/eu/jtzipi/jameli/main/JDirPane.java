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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

/**
 * Directory Tree Pane.
 *
 * @author jTzipi
 */
public final class JDirPane extends Pane {

    private static final Logger LOG = LoggerFactory.getLogger( "JDirPane!" );

    private TreeView<IPathNode> dirTreeView;
    private final ObjectProperty<TreeItem<IPathNode>> fxDirTreeItemProp = new SimpleObjectProperty<>( this, "FX_DIR_TREE_ITEM_PROP", FXProperties.DIR_TREE_ITEM );


    JDirPane() {


        createJDirPane();
        initListener();
    }

    ObjectProperty<TreeItem<IPathNode>> getFxDirTreeItemProp() {
        return fxDirTreeItemProp;
    }

    private void initListener() {

        // Add Change Listener to global Directory Path Prop
        FXProperties.FX_CURRENT_DIR_PATH.addListener( this::onDirPathChanged );
        // Add Change Listener to own directory tree item prop
        this.fxDirTreeItemProp.addListener( this::onFxDirItemPropChange );
    }


    private void createJDirPane() {

        dirTreeView = new TreeView<>( FXProperties.DIR_TREE_ITEM );
        dirTreeView.setCellFactory( ( cb ) -> new PathTreeCell() );
        dirTreeView.getSelectionModel().selectedItemProperty().addListener( this::onPathTreeSelectionChanged );

        ToggleButton pathHiddenTB = new ToggleButton();
        pathHiddenTB.setOnAction( ae -> updatePathFilter() );

        ToggleButton pathLinkedTB = new ToggleButton();
        pathLinkedTB.setOnAction( ae -> updatePathFilter() );
        getChildren().add( dirTreeView );

        // dirTreeView.get
    }


    /**
     * Tree Selection Changed.
     *
     * @param oldNode old path node
     * @param newNode new path node
     */
    private void onPathTreeSelectionChanged( ObservableValue<? extends TreeItem<IPathNode>> observableValue, TreeItem<IPathNode> oldNode, TreeItem<IPathNode> newNode ) {

        // we did not select node before or new node is null
        // return
        if ( null != newNode && newNode != oldNode ) {


            this.fxDirTreeItemProp.setValue( newNode );
            IPathNode newPathNode = newNode.getValue();

            FXProperties.FX_CURRENT_DIR_PATH.setValue( newPathNode );
        }


    }

    private void onFxDirItemPropChange( ObservableValue<? extends TreeItem<IPathNode>> observableValue, TreeItem<IPathNode> oldNode, TreeItem<IPathNode> newNode ) {
        LOG.warn( "Bei FX Dir Changed " + newNode + "'  '" + oldNode + "'" );
        if ( null != newNode && newNode != oldNode ) {
            dirTreeView.getSelectionModel().select( newNode );
            int row = dirTreeView.getRow( newNode );
            dirTreeView.scrollTo( row );
            newNode.setExpanded( true );

        }
    }

    private void onDirPathChanged( ObservableValue<? extends IPathNode> obs, IPathNode oldPath, IPathNode newPath ) {


        if ( null == newPath || oldPath == newPath ) {

            LOG.warn( "new path is null or old path == new path" );
            return;
        }
        LOG.warn( "Dir Path changed from '" + oldPath + "' to '" + newPath.getName() + "'" );
        // TODO change fxTreeItemProp too
        int row = search( newPath );

        if ( row < 0 ) {
            LOG.warn( "node not '" + newPath + "'" );
        } else {
            TreeItem<IPathNode> item = dirTreeView.getTreeItem( row );
            item.setExpanded( true );
            dirTreeView.scrollTo( row );
        }
    }

    private void setDirFilter( Predicate<IPathNode> ppn ) {
        FXProperties.FX_PATH_NODE_FILTER_PROP.setValue( ppn );
        this.dirTreeView.refresh();
    }

    private int search( IPathNode node ) {

        for ( int i = 0; i < dirTreeView.getExpandedItemCount(); i++ ) {
            TreeItem<IPathNode> pti = dirTreeView.getTreeItem( i );
            if ( pti.getValue().getValue().equals( node.getValue() ) ) {
                return i;
            }
        }
        return -9;
    }

    private void updatePathFilter() {

    }
}
