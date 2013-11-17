package org.semanticweb.elk.util.collections.intervals;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Interval interface. Represents a value space with lower and upper bounds.
 * 
 * @author Pospishnyi Oleksandr
 * @author Pavel Klinov
 */
public interface Interval<T> extends Comparable<Interval<T>> {

	public T getLow();
	
	public boolean isLowerInclusive();

	public T getHigh();
	
	public boolean isUpperInclusive();

	public boolean contains(Interval<T> interval);
}
