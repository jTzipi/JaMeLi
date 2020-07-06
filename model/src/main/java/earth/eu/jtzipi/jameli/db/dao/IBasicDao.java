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

package earth.eu.jtzipi.jameli.db.dao;


import javax.persistence.PersistenceException;


/**
 * @param <T>
 */
public interface IBasicDao<T, TI> {

    /**
     * Default <u>C</u>reate operation.
     *
     * @param db datatable
     * @throws PersistenceException
     * @throws NullPointerException
     */
    TI create( T db );

    T read( Long id ) throws PersistenceException;
}
