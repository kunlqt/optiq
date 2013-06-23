/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.optiq.impl.mongodb;

import net.hydromatic.linq4j.expressions.Expression;

import net.hydromatic.optiq.*;
import net.hydromatic.optiq.impl.TableInSchemaImpl;
import net.hydromatic.optiq.impl.java.MapSchema;

import org.eigenbase.reltype.RelDataType;
import org.eigenbase.reltype.RelDataTypeFactory;
import org.eigenbase.sql.type.SqlTypeName;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.util.*;

/**
 * Schema mapped onto a directory of MONGO files. Each table in the schema
 * is a MONGO file in that directory.
 */
public class MongoSchema extends MapSchema {
  final DB mongoDb;

  /**
   * Creates a MONGO schema.
   *
   * @param parentSchema Parent schema
   * @param host Mongo host, e.g. "localhost"
   * @param database Mongo database name, e.g. "foodmart"
   */
  public MongoSchema(
      Schema parentSchema,
      String host,
      String database,
      Expression expression) {
    super(parentSchema, expression);
    try {
      MongoClient mongo = new MongoClient(host);
      this.mongoDb = mongo.getDB(database);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected Collection<TableInSchema> initialTables() {
    final List<TableInSchema> list = new ArrayList<TableInSchema>();
    final RelDataType mapType =
        typeFactory.createMapType(
            typeFactory.createSqlType(SqlTypeName.VARCHAR),
            typeFactory.createSqlType(SqlTypeName.ANY));
    final RelDataType rowType =
        typeFactory.createStructType(
            new RelDataTypeFactory.FieldInfoBuilder().add("_MAP", mapType));
    for (String collection : mongoDb.getCollectionNames()) {
      final MongoTable table = new MongoTable(this, collection, rowType);
      list.add(
          new TableInSchemaImpl(this, collection, TableType.TABLE, table));
    }
    return list;
  }
}

// End MongoSchema.java
