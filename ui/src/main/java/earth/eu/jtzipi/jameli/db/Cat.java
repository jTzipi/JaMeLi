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

package earth.eu.jtzipi.jameli.db;

import earth.eu.jtzipi.jameli.ICat;
import earth.eu.jtzipi.jameli.IDecoratable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public class Cat implements ICat {

    private ICat parent;
    private List<ICat> subCatL;

    private String _name;
    private String _description;

    private ObjectProperty<ICat> fxParentCatProp;
    private ObservableList<ICat> fxSubCatOL;


    private StringProperty fxNameProp;
    private StringProperty fxDescProp;


    Cat( earth.eu.jtzipi.jameli.db.entity.Cat cat ) {

    }


    @Override
    public ICat getParent() {
        return this.parent;
    }

    public final void setParent( ICat cat ) {

    }

    @Override
    public List<ICat> getSubCatList() {
        return this.subCatL;
    }

    @Override
    public String getText() {
        return _name;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public Optional<IDecoratable> getDecor() {
        return Optional.empty();
    }

    @Override
    public String getId() {
        return null;
    }
}
