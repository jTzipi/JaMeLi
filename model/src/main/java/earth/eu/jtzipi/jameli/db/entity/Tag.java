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
 * A Tag for Media.
 */
@Entity(name = "Tag")
public final class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column
    private String text;

    @Column
    private String desc;

    @ManyToMany(mappedBy = "tag")
    private List<? extends Media> mediaList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "decoration_id", referencedColumnName = "id")
    private Decoration decoration;


}
