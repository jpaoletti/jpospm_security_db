/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.security;

import java.util.Date;

import org.hibernate.Transaction;
import org.jpos.ee.DB;
import org.jpos.ee.pm.security.*;

import junit.framework.TestCase;
import org.hibernate.HibernateException;

public class UserTest extends TestCase {

    public void testInsert() throws Exception {
        try {
            final DB db = new DB();
            db.open();
            assertNotNull(db.session());
            final Transaction tx = db.open().beginTransaction();

            final SECPermission p = new SECPermission();
            p.setName(SECPermission.LOGIN);
            db.save(p);

            final SECPermission p2 = new SECPermission();
            p2.setName(SECPermission.USER_ADMIN);
            db.save(p2);

            final SECUserGroup group = new SECUserGroup();
            group.setActive(true);
            group.setCreation(new Date());
            group.setDescription("Super Administration");
            group.setName("Administrators");
            group.grant(p);
            group.grant(p2);
            db.save(group);

            final SECUser user = new SECUser();
            user.setNick("admin");
            user.setPassword("$2a$12$I3T7QvrtClBjGA9mhVIDhe2XNj5GvaeaHv7Zx1v6P6Be3PXfA4f9W"); //test
            user.setName("Administrator");
            user.setActive(true);
            user.getGroups().add(group);

            db.save(user);
            tx.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
}
