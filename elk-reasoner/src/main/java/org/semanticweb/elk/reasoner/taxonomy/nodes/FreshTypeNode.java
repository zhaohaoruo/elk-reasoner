/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.reasoner.taxonomy.nodes;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.semanticweb.elk.reasoner.Reasoner;
import org.semanticweb.elk.reasoner.taxonomy.GenericInstanceTaxonomy;

/**
 * A fresh {@link GenericTypeNode} containing an object that does not occur in a
 * taxonomy. Such nodes are returned to queries when
 * {@link Reasoner#getAllowFreshEntities()} is set to {@code true}.
 * 
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 * @param <K>
 *            the type of the keys for the node members
 * @param <M>
 *            the type of node members
 * @param <K>
 *            the type of the keys for the node instances
 * @param <I>
 *            the type of instances
 * @param <TN>
 *            the type of sub-nodes and super-nodes of this
 *            {@code FreshTypeNode}
 * @param <IN>
 *            the type of instance nodes of this {@code FreshTypeNode}
 * @param <T>
 *            the type of taxonomy to which this node is attached
 * 
 * @see Reasoner#getAllowFreshEntities()
 */
public class FreshTypeNode<K, M, KI, I, 
			TN extends GenericTypeNode<K, M, KI, I, TN, IN>, 
			IN extends GenericInstanceNode<K, M, KI, I, TN, IN>, 
			T extends GenericInstanceTaxonomy<K, M, KI, I, TN, IN>>
		extends FreshTaxonomyNode<K, M, TN, T> implements
		GenericNonBottomTypeNode<K, M, KI, I, TN, IN> {

	public FreshTypeNode(Map.Entry<K, M> member, T taxonomy) {
		super(member, taxonomy);
	}

	@Override
	public Set<? extends IN> getDirectInstanceNodes() {
		return Collections.emptySet();
	}

	@Override
	public Set<? extends IN> getAllInstanceNodes() {
		return Collections.emptySet();
	}

}
