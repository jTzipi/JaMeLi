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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity(name = "MediaAudioVideo")
@Table(name = "media_audio_video")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MediaAudioVideo extends Media {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "media_audio_video",
            orphanRemoval = true)
    @JoinColumn(name = "spot_id")
    private List<Spot> spotList = new ArrayList<>();


    private String codec;
    @Column
    private long timeInSecond;


    public void addSpot( Spot spot ) {
        Objects.requireNonNull( spot );
        spotList.add( spot );
        spot.setMediaAudioVideo( this );
    }


    public void removeSpot( Spot spot ) {

        if ( !spotList.contains( spot ) ) {
            throw new IllegalStateException( "Spot '" + spot + "' not found!" );
        }
        spot.setMediaAudioVideo( null );
    }
}
