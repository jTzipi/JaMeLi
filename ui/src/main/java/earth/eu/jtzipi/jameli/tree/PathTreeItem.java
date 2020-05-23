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

package earth.eu.jtzipi.jameli.tree;

import earth.eu.jtzipi.jameli.FXProperties;
import earth.eu.jtzipi.modules.node.path.IPathNode;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Tree Item displaying {@linkplain IPathNode}'s.
 * <p>
 * <b>
 * Directory Tree Item of filesystem path.
 * </b>
 * <p>
 * <p>
 * Important details.
 * <p>
 * We use a 'double' cache strategy here.
 * Since we wrap {@link IPathNode} we have already a cached
 * loading of sub nodes. In that case we load always all nodes from a
 * directory. Here we filter all directories from that path nodes.
 * <p>
 * TODO: check whether wrapped node contains directories. If not we do not need to create sub nodes anyway.
 * TODO: check for fs change
 *
 * <u>Important:</u>
 *
 *
 *
 * </p>
 *
 * @author jTzipi
 */
public class PathTreeItem extends TreeItem<IPathNode> {

    private boolean created;    // sub nodes created

    /**
     * PathTreeItem.
     *
     * @param pathNode node wrapped
     */
    PathTreeItem( IPathNode pathNode ) {
        super( pathNode );
        this.created = false;

    }


    /**
     * Create a new instance.
     *
     * @param pathNode path node
     * @return created instance
     * @throws NullPointerException if {@code pathNode} is null
     */
    public static PathTreeItem of( IPathNode pathNode ) {
        return new PathTreeItem( Objects.requireNonNull( pathNode ) );
    }


    @Override
    public boolean isLeaf() {
        return getValue().isLeaf();
    }

    private static List<TreeItem<IPathNode>> createSubTree( IPathNode pathNode ) {


        // get all dirs of pathNode
        // map them to tree item

        List<TreeItem<IPathNode>> tioL = new ArrayList<>();
        Predicate<IPathNode> pp = FXProperties.FX_PATH_NODE_FILTER_PROP.getValue();
        for ( IPathNode pn : pathNode.getSubnodes() ) {

            if ( pp.test( pn ) ) {
                tioL.add( PathTreeItem.of( pn ) );
            }
        }

        return tioL;
    }

    @Override
    public ObservableList<TreeItem<IPathNode>> getChildren() {

        if ( !this.created ) {

            this.created = true;
            IPathNode pathNode = getValue();
            ObservableList<TreeItem<IPathNode>> subOL;


            // if leaf empty
            if ( isLeaf() || pathNode == null ) {

                subOL = FXCollections.emptyObservableList();

            } else {

                subOL = FXCollections.observableArrayList();
                subOL.addAll( createSubTree( pathNode ) );
                FXProperties.FX_PATH_NODE_FILTER_PROP.addListener( this::onDirFilterChanged );
            }
            super.getChildren().setAll( subOL );

        }

        return super.getChildren();
    }

    /**
     * On directory filter changed.
     *
     * @param pnObs  obs
     * @param ppnOld old predicate
     * @param ppNew  new predicate
     */
    private void onDirFilterChanged( ObservableValue<? extends Predicate<IPathNode>> pnObs, Predicate<IPathNode> ppnOld, Predicate<IPathNode> ppNew ) {

        //
        // here we do only set sub nodes if this node have loaded
        // it's subnodes.
        // TODO: restore expanded status
        //
        if ( ppnOld != ppNew && null != ppNew && created ) {

            super.getChildren().setAll( createSubTree( getValue() ) );

        }


    }
}
