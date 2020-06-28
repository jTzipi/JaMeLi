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
import java.util.Objects;

/**
 * A labeled point on a media track.
 *
 * @author jTzipi
 */
@Entity(name = "Spot")
@Table(name = "spot")
public class Spot implements Comparable<Spot> {

    @Id
    @GeneratedValue
    private long id;

    @Min(value = 0L)
    @Column(name = "milli_sec")
    private long spotMillisecond;

    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    private MediaAudioVideo mav;

    public Spot( long spotMillisecond, String label, MediaAudioVideo mav ) {
        this.spotMillisecond = spotMillisecond;
        this.label = label;
        this.mav = mav;
    }

    /**
     * Set corresponding media.
     * Caution: this maybe null
     *
     * @param mediaAudioVideo media
     */
    public void setMediaAudioVideo( MediaAudioVideo mediaAudioVideo ) {
        this.mav = mediaAudioVideo;
    }

    public final MediaAudioVideo getMav() {
        return this.mav;
    }

    /**
     * Return position.
     *
     * @return position
     */
    public long getSpotMillisecond() {
        return this.spotMillisecond;
    }

    public void setSpotMillisecond( long spotMillisecond ) {
        if ( spotMillisecond < 0L ) {
            throw new IllegalStateException( "negative milli '" + spotMillisecond + "'" );
        }
        this.spotMillisecond = spotMillisecond;
    }

    public String getLabel() {
        return this.label;
    }

    /**
     * Set label.
     *
     * @param label label
     * @throws NullPointerException if {@code label} is null
     */
    public void setLabel( String label ) {
        this.label = Objects.requireNonNull( label );
    }

    public long getId() {
        return this.id;
    }

    @Override
    public int compareTo( Spot spot ) {
        return Long.compare( this.spotMillisecond, spot.spotMillisecond );
    }

    @Override
    public int hashCode() {
        int result = ( int ) ( id ^ ( id >>> 32 ) );
        result = 31 * result + ( int ) ( spotMillisecond ^ ( spotMillisecond >>> 32 ) );

        return result;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Spot spot = ( Spot ) o;

        if ( id != spot.id ) return false;

        return this.spotMillisecond == spot.spotMillisecond;
    }
}
