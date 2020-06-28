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

package earth.eu.jtzipi.jameli.media.audiovideo;

import earth.eu.jtzipi.jameli.IMedia;
import earth.eu.jtzipi.jameli.media.ISpot;
import javafx.util.Duration;

import java.util.List;

public interface IAudioVideo extends IMedia {

    String CODEC_UNKNOWN = "?Unknown?";


    /**
     * Return name of audio or video codec.
     *
     * @return
     */
    String getCodec();

    List<ISpot> getSpotList();

    long getTimeInSeconds();

    default Duration getDuration() {

        return Duration.seconds( getTimeInSeconds() );
    }

    ;


}
