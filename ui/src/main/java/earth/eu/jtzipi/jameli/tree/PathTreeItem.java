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

import earth.eu.jtzipi.modules.node.path.IPathNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Tree Item displaying {@linkplain IPathNode}'s.
 *
 * @author jTzipi
 */
public class PathTreeItem extends TreeItem<IPathNode> {

    private boolean created;    // sub nodes created


    PathTreeItem( IPathNode pathNode ) {
        super( pathNode );
        this.created = false;
    }

    /**
     * Create a new instance.
     *
     * @param pathNode path node
     * @return created instance
     */
    public static PathTreeItem of( IPathNode pathNode ) {
        return Objects.requireNonNull( new PathTreeItem( pathNode ) );
    }

    @Override
    public ObservableList<TreeItem<IPathNode>> getChildren() {
        IPathNode pathNode = getValue();

        if ( null == pathNode || !pathNode.isDir() ) {

            return FXCollections.emptyObservableList();
        }

        if ( !created ) {

            // get all dirs of pathNode
            // map them to tree item
            List<TreeItem<IPathNode>> subDirL = pathNode
                    .getSubnodes( Files::isDirectory )
                    .stream()
                    .map( PathTreeItem::of )
                    .collect( Collectors.toList() );
            super.getChildren().setAll( subDirL );
            created = true;
        }

        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        return getValue().isLeaf();
    }
}
