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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

/**
 * Main Frame.
 *
 * @author jTzipi
 */
public final class JMainFrame extends BorderPane {


    private JMainFrame() {
        setPrefWidth( FXProperties.JMAIN_PANE_WIDTH );
        setPrefHeight( FXProperties.JMAIN_PANE_HEIGHT );
        createJMainFrame();

    }

    /**
     * Return single instance.
     *
     * @return main Frame.
     */
    public static JMainFrame main() {


        return new JMainFrame();
    }


    private void createJMainFrame() {

        JDirTreePane dirPane = new JDirTreePane();
        JDirectoryViewPane dirViewPane = new JDirectoryViewPane();
        JBreadCrumbPathPanel breadCrumbPathPanel = new JBreadCrumbPathPanel();
        breadCrumbPathPanel.bindDirTreeProperty( dirPane.getTreeItemProp() );

        ScrollPane dirViewScrPane = new ScrollPane( dirViewPane );
        dirViewScrPane.setFitToHeight( true );
        dirViewScrPane.setFitToWidth( true );

        setLeft( dirPane );
        setTop( breadCrumbPathPanel );
        setCenter( dirViewScrPane );
    }
}
