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

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Rating")
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue
    long id;

    String type;

    @Min(value = 0)
    int rating;

    @OneToMany(
            mappedBy = "rating"
            , cascade = CascadeType.ALL
            , orphanRemoval = true
    )
    private List<Media> mediaL = new ArrayList<>();
}