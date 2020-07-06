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

package earth.eu.jtzipi.jameli.db.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Default Category.
 * <p>
 * This DB table contain information about a media category.
 * <br/>
 * <br/>
 * Each Cat have a
 * <ul>
 *     <li>Parent</li>
 *     <li>Zero or more sub categories</li>
 *     <li>Short text</li>
 *     <li>Detailed text</li>
 *     <li>Optional Decoration</li>
 * </ul>
 */
@Entity
@Table(name = "category")
public final class Cat {

    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    private Cat parent;

    @OneToMany
    private List<Cat> subCatL = new ArrayList<>();

    @NaturalId
    @Column(nullable = false)
    private String text;

    @Column
    private String desc;


    @ManyToOne
    @JoinColumn(name = "decoration_id", referencedColumnName = "id")
    private Decoration decoration;

    public Cat( Cat parent, List<Cat> subCatL, String id, String text, String desc, Decoration decoration ) {

    }

    /**
     * Return parent category or null if root.
     *
     * @return parent category
     */
    public Cat getParent() {
        return parent;
    }

    public List<Cat> getSubCatList() {
        return subCatL;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public Decoration getDecoration() {
        return decoration;
    }
}
