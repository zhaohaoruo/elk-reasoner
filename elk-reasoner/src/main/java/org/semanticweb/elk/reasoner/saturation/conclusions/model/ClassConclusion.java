package org.semanticweb.elk.reasoner.saturation.conclusions.model;

import org.semanticweb.elk.reasoner.indexing.model.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.context.ContextPremises;
import org.semanticweb.elk.reasoner.saturation.inferences.ClassInference;
import org.semanticweb.elk.reasoner.saturation.rules.Rule;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
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

/**
 * A {@link SaturationConclusion} that represents a derived class axiom produced
 * by {@link ClassInference}s. {@link ClassConclusion}s are stored in
 * {@link ContextPremises} to which {@link Rule}s are applied producing
 * {@link ClassInference}s.
 * 
 * @see Rule
 * @see ClassInference
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public interface ClassConclusion extends SaturationConclusion {

	/**
	 * @return The root of the {@link Context} in which this conclusion should
	 *         participate in inferences; it cannot be {@code null}.
	 */
	public IndexedContextRoot getConclusionRoot();

	/**
	 * 
	 * @return The {@link IndexedContextRoot} of the {@link Context} from which
	 *         this {@link ClassConclusion} originate. This value may be
	 *         different from {@link #getConclusionRoot()} but cannot be
	 *         {@code null}. Specifically, this {@link ClassConclusion} is
	 *         guaranteed to be derived from {@link ContextInitialization} with
	 *         the value {@link #getConclusionRoot()} equal to this value.
	 */
	public IndexedContextRoot getOriginRoot();

	public <O> O accept(Visitor<O> visitor);

	/**
	 * A factory for creating instances
	 * 
	 * @author Yevgeny Kazakov
	 *
	 */
	interface Factory
			extends
				ContextInitialization.Factory,
				Contradiction.Factory,
				DisjointSubsumer.Factory,
				InitializationConclusion.Factory,
				ForwardLink.Factory,
				SubClassConclusion.Factory,
				SubClassInclusion.Factory {

		// combined interface

	}

	/**
	 * The visitor pattern for instances
	 * 
	 * @author Yevgeny Kazakov
	 *
	 * @param <O>
	 *            the type of the output
	 */
	interface Visitor<O>
			extends
				ContextInitialization.Visitor<O>,
				Contradiction.Visitor<O>,
				DisjointSubsumer.Visitor<O>,
				InitializationConclusion.Visitor<O>,
				ForwardLink.Visitor<O>,
				SubClassConclusion.Visitor<O>,
				SubClassInclusion.Visitor<O> {

		// combined interface

	}

}
