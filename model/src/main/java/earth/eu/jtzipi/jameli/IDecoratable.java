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

package earth.eu.jtzipi.jameli;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.nio.file.Path;

/**
 * For UI controls which can be styled.
 */
public interface IDecoratable {

    /**
     * Cat with no Icon
     */
    String NO_ICON = "$";

    /**
     * Return relative path to icon.
     *
     * @return path to icon
     */
    Path getIconRelPath();

    /**
     * Return background.
     *
     * @return background color
     */
    Color getColor();

    /**
     * Return custom shape.
     *
     * @return shape
     */
    SVGPath getSVGPath();
}
