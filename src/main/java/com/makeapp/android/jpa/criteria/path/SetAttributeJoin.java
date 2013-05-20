/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.makeapp.android.jpa.criteria.path;

import com.makeapp.android.jpa.criteria.*;

import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SetAttribute;
import java.io.Serializable;
import java.util.Set;

/**
 * TODO : javadoc
 *
 * @author Steve Ebersole
 */
public class SetAttributeJoin<O, E>
        extends PluralAttributeJoinSupport<O, Set<E>, E>
        implements SetJoinImplementor<O, E>, Serializable
{

    public SetAttributeJoin(
            CriteriaBuilderImpl criteriaBuilder,
            Class<E> javaType,
            PathSource<O> pathSource,
            SetAttribute<? super O, E> joinAttribute,
            JoinType joinType)
    {
        super(criteriaBuilder, javaType, pathSource, joinAttribute, joinType);
    }

    @Override
    public SetAttribute<? super O, E> getAttribute()
    {
        return (SetAttribute<? super O, E>) super.getAttribute();
    }

    @Override
    public SetAttribute<? super O, E> getModel()
    {
        return getAttribute();
    }

    @Override
    public final SetAttributeJoin<O, E> correlateTo(CriteriaSubqueryImpl subquery)
    {
        return (SetAttributeJoin<O, E>) super.correlateTo(subquery);
    }

    @Override
    protected FromImplementor<O, E> createCorrelationDelegate()
    {
        return new SetAttributeJoin<O, E>(
                criteriaBuilder(),
                getJavaType(),
                (PathImplementor<O>) getParentPath(),
                getAttribute(),
                getJoinType()
        );
    }
}
