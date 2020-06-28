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


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Objects;

public interface IDao {

    /**
     * Default <u>C</u>reate operation.
     *
     * @param sf session factory
     */
    default void create( SessionFactory sf ) {
        Objects.requireNonNull( sf );

        Transaction transaction = null;
        try ( Session session = sf.openSession() ) {

            transaction = session.beginTransaction();
            session.persist( this );
            transaction.commit();

        } catch ( final HibernateException hibE ) {

            transaction.rollback();
        }

    }
}
