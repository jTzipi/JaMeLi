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

import java.util.List;

/**
 * Category IF.
 * <p>
 * A category for all kind of media.
 * <br/>
 * This is a tree like structure.
 * So each cat have one parent. In case of root category this is null.
 * Each cat may have one or more sub categories.
 */
public interface ICat extends IModel {


    /**
     * Return  parent category or null if root.
     *
     * @return parent cat
     */
    ICat getParent();

    /**
     * Return list of sub categories.
     *
     * @return
     */
    List<ICat> getSubCatList();

    /**
     * Return text of category.
     *
     * @return text
     */
    String getText();

    /**
     * Description of category.
     *
     * @return description
     */
    String getDescription();

    /**
     * Return decoration.
     *
     * @return decoration
     */
    IDecoratable getDecor();
}
