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
import earth.eu.jtzipi.jameli.tree.PathTreeItem;
import earth.eu.jtzipi.modules.node.path.IPathNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

/**
 * Directory Tree Pane.
 * Display directory tree of file system (fs).
 * <p>
 * <p>
 * We sync the <code>dirTreeView</code> with the {@link org.controlsfx.control.BreadCrumbBar} of
 * <code>{@link JBreadCrumbPathPanel}</code> via <code>fxTreeItemProp</code>.
 *
 * @author jTzipi
 */
public final class JDirTreePane extends Pane {

    private static final Logger LOG = LoggerFactory.getLogger( "JDirPane!" );

    private TreeView<IPathNode> dirTreeView;

    private ToggleButton pathHiddenTogB;
    private ToggleButton pathLinkedTogB;
    private ObjectProperty<TreeItem<IPathNode>> fxTreeItemProp = new SimpleObjectProperty<>( this, "FX_TREE_ITEM_PROP" );


    JDirTreePane() {


        createJDirPane();
        initListener();
    }



    private void initListener() {

        // Add Change Listener to global Directory Path Prop
        FXProperties.FX_CURRENT_DIR_PATH.addListener( this::onDirPathChanged );


        // Add Change Listener to own directory tree item prop
        this.fxTreeItemProp.addListener( this::onFxDirItemPropChange );
    }


    private void createJDirPane() {

        BorderPane borderPane = new BorderPane();
        // root node
        // IPathNode rootNode = RegularPathNode.of( FXProperties.ROOT_PATH, null );
        // root tree item
        TreeItem<IPathNode> rootTreeItem = PathTreeItem.of( FXProperties.FX_CURRENT_DIR_PATH.getValue() );
        // set this to initial tree item prop
        fxTreeItemProp.setValue( rootTreeItem );


        dirTreeView = new TreeView<>( rootTreeItem );
        dirTreeView.setCellFactory( ( cb ) -> new PathTreeCell() );
        dirTreeView.setShowRoot( false );
        dirTreeView.getSelectionModel().selectedItemProperty().addListener( this::onDirItemChange );


        pathHiddenTogB = new ToggleButton();
        pathHiddenTogB.setOnAction( ae -> updatePathFilter() );

        pathLinkedTogB = new ToggleButton();
        pathLinkedTogB.setOnAction( ae -> updatePathFilter() );

        HBox hb = new HBox( pathHiddenTogB, pathLinkedTogB );
        borderPane.setTop( hb );
        borderPane.setCenter( dirTreeView );
        getChildren().add( borderPane );


    }

    /**
     * TreeItem<{@link IPathNode}> property to sync with other views.
     *
     * @return object property
     */
    ObjectProperty<TreeItem<IPathNode>> getTreeItemProp() {
        return this.fxTreeItemProp;
    }

    /**
     * {@code dirTreeView selection Changed handler.
     *
     * <code>Pre:</code>
     * 1. First check that new node is not null -> no info
     * 2. Check that old node is not new node -> nothing todo.
     * <br>
     * <code>Do:</code>
     * 1. Set {@code newNode} to {@code fxTreeItemProp}. Setting the breadcrumb bar of
     * {@linkplain JBreadCrumbPathPanel}.
     * 2. Update @linkplain FXProperties
     * @param oldNode old path node
     * @param newNode new path node
     */

    private void onDirItemChange( ObservableValue<? extends TreeItem<IPathNode>> observableValue, TreeItem<IPathNode> oldNode, TreeItem<IPathNode> newNode ) {

        if ( null == newNode || oldNode == newNode ) {
            LOG.debug( "newNode=" + newNode + " oldNode=" + oldNode );
            return;
        }

        fxTreeItemProp.setValue( newNode );
    }

    private void onFxDirItemPropChange( ObservableValue<? extends TreeItem<IPathNode>> observableValue, TreeItem<IPathNode> oldNode, TreeItem<IPathNode> newNode ) {

        if ( null != newNode && newNode != oldNode ) {


            int row = dirTreeView.getRow( newNode );
            // only if event was not originated here
            if ( !isFocused() ) {
                this.dirTreeView.scrollTo( row );
            }
            //LOG.warn( "row for " + newNode.getValue().getName() + ">> " +row );


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
            // Scroll to new dir if not focused only
            // this is the case when we receive this event not from this tree
            if ( !isFocused() ) {
                TreeItem<IPathNode> item = dirTreeView.getTreeItem( row );
                item.setExpanded( true );
                dirTreeView.scrollTo( row );
            }
        }
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

        Predicate<IPathNode> predicate = IPathNode::isDir;

        boolean ph = pathHiddenTogB.isSelected();
        boolean pl = pathLinkedTogB.isSelected();

        Predicate<IPathNode> filter;
        if ( !ph && pl ) {
            filter = predicate.and( pathNode -> !pathNode.isHidden() );
        } else if ( ph && !pl ) {
            filter = predicate.and( pathNode -> !pathNode.isLink() );
        } else {
            filter = predicate.and( pathNode -> !pathNode.isHidden() && !pathNode.isLink() );
        }

        FXProperties.FX_PATH_NODE_FILTER_PROP.setValue( filter);
    }
}
