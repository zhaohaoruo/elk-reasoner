/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2016 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.reasoner.taxonomy;

import org.semanticweb.elk.owl.interfaces.ElkEntity;
import org.semanticweb.elk.reasoner.taxonomy.model.ComparatorKeyProvider;
import org.semanticweb.elk.reasoner.taxonomy.model.Node;
import org.semanticweb.elk.reasoner.taxonomy.model.NodeFactory;
import org.semanticweb.elk.reasoner.taxonomy.model.TaxonomyNodeFactory;

public abstract class InternalNodeFactory<
				T extends ElkEntity,
				N extends Node<T>,
				Tax
		> implements NodeFactory<T, N>, TaxonomyNodeFactory<T, N, Tax> {

	private final Tax taxonomy_;
	
	public InternalNodeFactory(final Tax taxonomy) {
		this.taxonomy_ = taxonomy;
	}
	
	@Override
	public final N createNode(final Iterable<? extends T> members, final int size,
			final ComparatorKeyProvider<? super T> keyProvider) {
		return createNode(members, size, taxonomy_);
	}// TODO: simplify all the node factories !!!
	
}