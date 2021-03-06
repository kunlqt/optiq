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
package org.eigenbase.sarg;

/**
 * SargSetOperator defines the supported set operators which can be used to
 * combine instances of {@link SargExpr}.
 *
 * @author John V. Sichi
 * @version $Id$
 */
public enum SargSetOperator
{
    /**
     * Set intersection over any number of children (no children => universal
     * set).
     */
    INTERSECTION,

    /**
     * Set union over any number of children (no children => empty set).
     */
    UNION,

    /**
     * Set complement over exactly one child.
     */
    COMPLEMENT
}

// End SargSetOperator.java
