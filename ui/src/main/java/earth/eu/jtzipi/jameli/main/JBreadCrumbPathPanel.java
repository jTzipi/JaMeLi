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
import earth.eu.jtzipi.modules.node.path.IPathNode;
import impl.org.controlsfx.skin.BreadCrumbBarSkin;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import org.controlsfx.control.BreadCrumbBar;

import java.util.Objects;


/**
 * Path BreadCrumbBar Panel.
 * This is an alternative way to browse the local file system.
 *
 * @author jTzipi
 */
public class JBreadCrumbPathPanel extends HBox {

    private BreadCrumbBar<IPathNode> pathBCBar;


    JBreadCrumbPathPanel() {
        createJPathPane();

    }

    /**
     * Bind breadcrumb item on directory tree item property.
     *
     * @param treeItemProp tree item prop from directory tree
     * @throws NullPointerException if
     */
    void bindDirTreeProperty( ObjectProperty<TreeItem<IPathNode>> treeItemProp ) {
        Objects.requireNonNull( treeItemProp );
        this.pathBCBar.setOnCrumbAction( ae -> treeItemProp.setValue( ae.getSelectedCrumb() ) );
        this.pathBCBar.selectedCrumbProperty().bind( treeItemProp );


    }

    private void createJPathPane() {

        this.pathBCBar = new BreadCrumbBar<>( FXProperties.DIR_TREE_ITEM );
        this.pathBCBar.setAutoNavigationEnabled( false );
        this.pathBCBar.setCrumbFactory( cback -> new BreadCrumbBarSkin.BreadCrumbButton( cback.getValue().getName() ) );

        getChildren().add( pathBCBar );

        setPadding( new Insets( 3D ) );
    }

}


