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
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.List;
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
 * <br/>
 * The currently displayed folder is sync with {@linkplain JBreadCrumbPathPanel} and
 * {@linkplain JDirTreePane}.
 * Sync dir via {@link FXProperties#FX_CURRENT_DIR_PATH}.
 *
 * </p>
 *
 * @author jTzipi
 */
public class JDirectoryViewPane extends FlowPane {


    private final DoubleProperty fxGapHoriProp = new SimpleDoubleProperty( this, "FX_DIR_VIEW_GAP_H_PROP", 9D );
    private final DoubleProperty fxGapVertProp = new SimpleDoubleProperty( this, "FX_DIR_VIEW_GAP_V_PROP", 9D );

    JDirectoryViewPane() {

        createDirectoryViewPane();
        initListener();
    }

    private void initListener() {
        FXProperties.FX_CURRENT_DIR_PATH.addListener( this::onPathChanged );
    }

    private static List<Tile> nodeChanged( IPathNode pn ) {
// populate all nodes based
        List<Tile> tl = pn.getSubnodes().stream().filter( FXProperties.FX_PATH_NODE_FILTER_PROP.getValue() ).map( Tile::new ).collect( Collectors.toList() );
        // add node if  parent is not  root
        if ( !pn.getValue().equals( FXProperties.ROOT_PATH ) ) {

            IPathNode parentNode = ( IPathNode ) pn.getParent();
            tl.add( 0, new Tile( parentNode ) );
        }


        return tl;
    }

    private void createDirectoryViewPane() {

        // get root node
        IPathNode root = FXProperties.FX_CURRENT_DIR_PATH.getValue();

        List<Tile> tileL = nodeChanged( root );

        this.hgapProperty().bind( fxGapHoriProp );
        this.vgapProperty().bind( fxGapVertProp );
        this.getChildren().setAll( tileL );
    }

    private void onPathChanged( ObservableValue<? extends IPathNode> obp, IPathNode oldNode, IPathNode newNode ) {

        if ( null == newNode || oldNode == newNode ) {

            return;
        }

        // collect and set all nodes

        List<Tile> dirL = nodeChanged( newNode );
        getChildren().setAll( dirL );

    }

    private static class Tile extends Label {

        private Background bgHover = new Background( new BackgroundFill( Color.rgb( 10, 10, 246, 0.5D ), CornerRadii.EMPTY, Insets.EMPTY ) );
        private final IPathNode node;
        //
        private ScaleTransition growST;
        private boolean up; // dir up

        /**
         * @param pathNode
         */
        private Tile( IPathNode pathNode ) {
            this( pathNode, false );
        }

        private Tile( IPathNode pathNode, boolean dirUpProp ) {
            this.node = pathNode;
            this.up = dirUpProp;

            this.setText( pathNode.getName() );
            this.setMinHeight( 25D );

            growST = new ScaleTransition();
            growST.setNode( this );
            growST.setFromX( 1.0D );
            growST.setToX( 1.2D );
            //growST.setByX( 1.2D );

// ouseEvent -> setBackground( bgHover )-> setBackground( null )
            setOnMouseEntered( this::onMouseEnter );
            setOnMouseExited( this::onMouseExit );
            setOnMouseClicked( this::onMouseClicked );
        }

        private boolean isUp() {
            return up;
        }

        private void onMouseEnter( MouseEvent me ) {

            growST.setRate( 1D );
            growST.play();
        }

        private void onMouseExit( MouseEvent me ) {


            growST.setRate( -1D );
            growST.play();
        }

        private void onMouseClicked( MouseEvent me ) {

            if ( me.getClickCount() == 2 ) {

                // show new dir
                if ( node.isDir() ) {

                    // what to show
                    IPathNode dir = isUp() ? ( IPathNode ) node.getParent() : this.node;

                    FXProperties.FX_CURRENT_DIR_PATH.setValue( dir );


                }

            }

        }
    }
}
