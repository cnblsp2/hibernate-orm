/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008-2011, Red Hat Inc. or third-party contributors as
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
package org.hibernate.metadata;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

/**
 * Exposes entity class metadata to the application
 *
 * @see org.hibernate.SessionFactory#getClassMetadata(Class)
 * @author Gavin King
 */
@SuppressWarnings( {"JavaDoc"})
public interface ClassMetadata {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // stuff that is persister-centric and/or EntityInfo-centric ~~~~~~~~~~~~~~
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * The name of the entity
	 */
	String getEntityName();

	/**
	 * Get the name of the identifier property (or return null)
	 */
	String getIdentifierPropertyName();

	/**
	 * Get the names of the class' persistent properties
	 */
	String[] getPropertyNames();

	/**
	 * Get the identifier Hibernate type
	 */
	Type getIdentifierType();

	/**
	 * Get the Hibernate types of the class properties
	 */
	Type[] getPropertyTypes();

	/**
	 * Get the type of a particular (named) property
	 */
	Type getPropertyType(String propertyName) throws HibernateException;

	/**
	 * Does this class support dynamic proxies?
	 */
	boolean hasProxy();

	/**
	 * Are instances of this class mutable?
	 */
	boolean isMutable();

	/**
	 * Are instances of this class versioned by a timestamp or version number column?
	 */
	boolean isVersioned();

	/**
	 * Get the index of the version property
	 */
	int getVersionProperty();

	/**
	 * Get the nullability of the class' persistent properties
	 */
	boolean[] getPropertyNullability();


	/**
	 * Get the "laziness" of the properties of this class
	 */
	boolean[] getPropertyLaziness();

	/**
	 * Does this class have an identifier property?
	 */
	boolean hasIdentifierProperty();

	/**
	 * Does this entity declare a natural id?
	 */
	boolean hasNaturalIdentifier();

	/**
	 * Which properties hold the natural id?
	 */
	int[] getNaturalIdentifierProperties();
	
	/**
	 * Does this entity have mapped subclasses?
	 */
	boolean hasSubclasses();
	
	/**
	 * Does this entity extend a mapped superclass?
	 */
	boolean isInherited();
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// stuff that is tuplizer-centric, but is passed a session ~~~~~~~~~~~~~~~~
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Return the values of the mapped properties of the object
	 */
	@SuppressWarnings( {"UnusedDeclaration"})
	Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session)
	throws HibernateException;


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// stuff that is Tuplizer-centric ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * The persistent class, or null
	 */
	Class getMappedClass();

	/**
	 * Create a class instance initialized with the given identifier
	 *
	 * @param id The identifier value to use (may be null to represent no value)
	 * @param session The session from which the request originated.
	 *
	 * @return The instantiated entity.
	 */
	Object instantiate(Serializable id, SessionImplementor session);

	/**
	 * Get the value of a particular (named) property
	 */
	Object getPropertyValue(Object object, String propertyName) throws HibernateException;

	/**
	 * Extract the property values from the given entity.
	 *
	 * @param entity The entity from which to extract the property values.
	 * @return The property values.
	 * @throws HibernateException
	 */
	Object[] getPropertyValues(Object entity) throws HibernateException;

	/**
	 * Set the value of a particular (named) property
	 */
	void setPropertyValue(Object object, String propertyName, Object value) throws HibernateException;

	/**
	 * Set the given values to the mapped properties of the given object
	 */
	void setPropertyValues(Object object, Object[] values) throws HibernateException;

	/**
	 * Get the identifier of an instance (throw an exception if no identifier property)
	 *
	 * @deprecated Use {@link #getIdentifier(Object,SessionImplementor)} instead
	 */
	@Deprecated
	@SuppressWarnings( {"JavaDoc"})
	Serializable getIdentifier(Object object) throws HibernateException;

	/**
	 * Get the identifier of an instance (throw an exception if no identifier property)
	 *
	 * @param entity The entity for which to get the identifier
	 * @param session The session from which the request originated
	 *
	 * @return The identifier
	 */
	Serializable getIdentifier(Object entity, SessionImplementor session);

	/**
	 * Inject the identifier value into the given entity.
	 *
	 * @param entity The entity to inject with the identifier value.
	 * @param id The value to be injected as the identifier.
	 * @param session The session from which is requests originates
	 */
	void setIdentifier(Object entity, Serializable id, SessionImplementor session);


	/**
	 * Does the class implement the <tt>Lifecycle</tt> interface?
	 */
	@SuppressWarnings( {"UnusedDeclaration"})
	boolean implementsLifecycle();

	/**
	 * Get the version number (or timestamp) from the object's version property
	 * (or return null if not versioned)
	 */
	Object getVersion(Object object) throws HibernateException;

}
