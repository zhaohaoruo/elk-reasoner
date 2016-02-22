package org.semanticweb.elk.reasoner.saturation.inferences;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedClass;
import org.semanticweb.elk.reasoner.indexing.model.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.model.IndexedContextRoot;
import org.semanticweb.elk.reasoner.indexing.model.IndexedDefinitionAxiom;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusionComposed;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusionDecomposed;

/**
 * A {@link ClassInference} producing a {@link SubClassInclusionDecomposed} from
 * a {@link SubClassInclusionDecomposed} with
 * {@link SubClassInclusionComposed#getSubsumer()} instance of
 * {@link IndexedClass} and an {@link IndexedDefinitionAxiom}:<br>
 * 
 * <pre>
 *     (1)      (2)
 *  [C] ⊑ -A  [A = D]
 * ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
 *      [C] ⊑ -D
 * </pre>
 * 
 * The parameters can be obtained as follows:<br>
 * 
 * C = {@link #getOrigin()} = {@link #getDestination()}<br>
 * A = {@link #getDefinedClass()}<br>
 * D = {@link #getDefinition()}<br>
 * 
 * @see IndexedDefinitionAxiom
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class SubClassInclusionExpandedDefinition
		extends AbstractSubClassInclusionDecomposedInference {

	private final IndexedClass defined_;

	private final ElkAxiom reason_;

	public SubClassInclusionExpandedDefinition(IndexedContextRoot root,
			IndexedClass defined, IndexedClassExpression definition,
			ElkAxiom reason) {
		super(root, definition);
		this.defined_ = defined;
		this.reason_ = reason;
	}

	public IndexedClass getDefinedClass() {
		return defined_;
	}

	public IndexedClassExpression getDefinition() {
		return getSubsumer();
	}

	public ElkAxiom getReason() {
		return reason_;
	}

	public SubClassInclusionDecomposed getFirstPremise(
			SubClassInclusionDecomposed.Factory factory) {
		return factory.getSubClassInclusionDecomposed(getOrigin(), defined_);
	}

	public IndexedDefinitionAxiom getSecondPremise(
			IndexedDefinitionAxiom.Factory factory) {
		return factory.getIndexedDefinitionAxiom(reason_, defined_,
				getSubsumer());
	}

	@Override
	public IndexedContextRoot getOrigin() {
		return getDestination();
	}

	@Override
	public String toString() {
		return super.toString() + " (definition-)";
	}

	@Override
	public final <O> O accept(
			SubClassInclusionDecomposedInference.Visitor<O> visitor) {
		return visitor.visit(this);
	}

	/**
	 * Visitor pattern for instances
	 * 
	 * @author Yevgeny Kazakov
	 *
	 * @param <O>
	 *            the type of the output
	 */
	public static interface Visitor<O> {

		public O visit(SubClassInclusionExpandedDefinition inference);

	}

}