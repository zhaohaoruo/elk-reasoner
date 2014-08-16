/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedPropertyChain;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.BackwardLinkImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.SubPropertyChain;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.ClassInferenceVisitor;

/**
 * A {@link BackwardLink} that is obtained by reversing a given
 * {@link ForwardLink}.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class ReversedForwardLink extends BackwardLinkImpl implements ClassInference {

	private final ForwardLink sourceLink_;

	private final IndexedClassExpression inferenceContext_;

	/**
	 * 
	 */
	public ReversedForwardLink(IndexedClassExpression source,
			IndexedObjectProperty relation, ForwardLink forwardLink) {
		super(source, relation);
		this.sourceLink_ = forwardLink;
		this.inferenceContext_ = source;
	}

	@Override
	public <I, O> O acceptTraced(ClassInferenceVisitor<I, O> visitor, I parameter) {
		return visitor.visit(this, parameter);
	}

	public ForwardLink getSourceLink() {
		return sourceLink_;
	}
	
	// this is a side condition
	public SubPropertyChain<IndexedPropertyChain, IndexedObjectProperty> getSubPropertyChain() {
		return new SubPropertyChain<IndexedPropertyChain, IndexedObjectProperty>(sourceLink_.getRelation(), getRelation());
	}

	@Override
	public IndexedClassExpression getInferenceContextRoot(
			IndexedClassExpression rootWhereStored) {
		return inferenceContext_;
	}
}
