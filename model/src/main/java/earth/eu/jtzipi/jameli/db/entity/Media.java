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


import earth.eu.jtzipi.jameli.db.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class for data base media data.
 *
 * @author jTzipi
 */
@Entity
@Table(name = "media")
@Inheritance(strategy = InheritanceType.JOINED)
public class Media {

    @ManyToOne
    Cat cat;

    @Id
    String id;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "media_tag",
            joinColumns = {@JoinColumn(name = "media_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    Set<Tag> tagS = new HashSet<>();

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime archiveLDT;


    String name;
    String desc;

    @ManyToOne(fetch = FetchType.LAZY)
    Rating rating;

}
