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
import javafx.scene.control.TreeCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Tree Cell Renderer for {@link IPathNode}.
 */
public class PathTreeCell extends TreeCell<IPathNode> {

    private static final Logger LOGGER = LoggerFactory.getLogger( "PathTreeCell" );
    private String text;


    public PathTreeCell() {
        this.text = "";

    }


    @Override
    public void updateItem( IPathNode node, boolean empty ) {
        super.updateItem( node, empty );


        if ( null == node ) {
            LOGGER.warn( "Node is null" );
            text = "";
        } else if ( empty ) {

            LOGGER.warn( "Node is empty " + node );
            text = "";
        } else {

            text = node.getName();

        }

        setText( text );

    }
}
