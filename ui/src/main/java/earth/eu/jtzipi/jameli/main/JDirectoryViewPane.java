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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Show files and folders of current directory.
 * <p>
 * Idea is to show a flow of directories and files with
 * an optional icon. Dynamically setting position of each
 * file.
 * <br/>
 * We should consider place a static link to parent folder.
 * If the folder is empty display some info.
 * The currently displayed folder is sync with {@linkplain JBreadCrumbPathPanel} and
 *
 *
 * </p>
 *
 * @author jTzipi
 */
public class JDirectoryViewPane extends FlowPane {

    private final ObservableList<Label> fileLabelL = FXCollections.observableArrayList();
    private ObjectProperty<Predicate<IPathNode>> fxFileFilterProp;

    private DoubleProperty fxGapHoriProp = new SimpleDoubleProperty( this, "", 9D );
    private DoubleProperty fxGapVertProp = new SimpleDoubleProperty( this, "", 9D );

    JDirectoryViewPane() {

        createDirectoryViewPane();
        initListener();
    }

    private void initListener() {
        FXProperties.FX_CURRENT_DIR_PATH.addListener( this::onPathChanged );
    }

    private void createDirectoryViewPane() {

        // get root node
        IPathNode root = FXProperties.FX_CURRENT_DIR_PATH.getValue();
        // populate all nodes based
        List<Tile> tileList = root.getSubnodes().stream().map( Tile::ofNode ).collect( Collectors.toList() );
        // add root node
        tileList.add( 0, Tile.ofNode( root ) );

        this.hgapProperty().bind( fxGapHoriProp );
        this.vgapProperty().bind( fxGapVertProp );
        this.getChildren().setAll( tileList );

    }

    private void onPathChanged( ObservableValue<? extends IPathNode> obp, IPathNode oldNode, IPathNode newNode ) {

        if ( null == newNode || oldNode == newNode ) {

            return;
        }


    }

    private static class Tile extends Label {

        private Background bgHover = new Background( new BackgroundFill( Color.rgb( 10, 10, 246, 0.5D ), CornerRadii.EMPTY, Insets.EMPTY ) );

        private Tile() {


            setOnMouseEntered( mouseEvent -> setBackground( bgHover ) );
            setOnMouseExited( mouseEvent -> setBackground( null ) );
        }

        private static Tile ofNode( IPathNode pathNode ) {
            Tile tile = new Tile();
            tile.setText( pathNode.getName() );
            tile.setMinHeight( 25D );
            tile.setMinWidth( 99D );
            return tile;
        }
    }
}
