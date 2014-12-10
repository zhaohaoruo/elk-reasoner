/**
 * 
 */
package org.semanticweb.elk.proofs.inferences.properties;
/*
 * #%L
 * ELK Proofs Package
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import java.util.Arrays;
import java.util.Collection;

import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyOfAxiom;
import org.semanticweb.elk.proofs.expressions.derived.DerivedExpression;
import org.semanticweb.elk.proofs.expressions.derived.DerivedExpressionFactory;
import org.semanticweb.elk.proofs.inferences.AbstractInference;
import org.semanticweb.elk.proofs.inferences.InferenceRule;
import org.semanticweb.elk.proofs.inferences.InferenceVisitor;
import org.semanticweb.elk.proofs.utils.InferencePrinter;

/**
 * TODO
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class ChainSubsumption extends AbstractInference {

	private final DerivedExpression firstPremise_;

	private final DerivedExpression secondPremise_;

	private final DerivedExpression conclusion_;

	public ChainSubsumption(
			ElkSubObjectPropertyOfAxiom conclusion,
			DerivedExpression first, 
			ElkSubObjectPropertyOfAxiom second,
			DerivedExpressionFactory exprFactory) {
		firstPremise_ = first;
		secondPremise_ = exprFactory.create(second);
		conclusion_ = exprFactory.create(conclusion);
	}

	@Override
	public Collection<DerivedExpression> getRawPremises() {
		return Arrays.asList(firstPremise_, secondPremise_);
	}

	@Override
	public DerivedExpression getConclusion() {
		return conclusion_;
	}

	@Override
	public <I, O> O accept(InferenceVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public String toString() {
		return InferencePrinter.print(this);
	}
	
	@Override
	public InferenceRule getRule() {
		return InferenceRule.R_CHAIN_SUBSUMPTION;
	}
}
