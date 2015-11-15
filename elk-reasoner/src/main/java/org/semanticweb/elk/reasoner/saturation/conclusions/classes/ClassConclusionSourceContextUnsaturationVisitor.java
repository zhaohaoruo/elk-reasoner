package org.semanticweb.elk.reasoner.saturation.conclusions.classes;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.SaturationStateWriter;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.ClassConclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusionComposed;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusionDecomposed;
import org.semanticweb.elk.reasoner.saturation.conclusions.model.SubClassInclusion;
import org.semanticweb.elk.reasoner.saturation.context.Context;

/**
 * A {@link ClassConclusion.Visitor} that marks the source {@link Context} of the
 * {@link ClassConclusion} as not saturated if the {@link ClassConclusion} can potentially
 * be re-derived. The visit method returns always {@link true}.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class ClassConclusionSourceContextUnsaturationVisitor extends
		AbstractClassConclusionVisitor<Context, Boolean> {

	private final SaturationStateWriter<?> writer_;

	public ClassConclusionSourceContextUnsaturationVisitor(
			SaturationStateWriter<?> writer) {
		this.writer_ = writer;
	}

	@Override
	protected Boolean defaultVisit(ClassConclusion conclusion, Context context) {
		IndexedContextRoot root = conclusion.getOriginRoot();
		writer_.markAsNotSaturated(root);
		return true;
	}

	Boolean defaultVisit(SubClassInclusion conclusion, Context context) {
		// if the super-class does not occur in the ontology anymore, it cannot be
		// re-derived, and thus, the context should not be modified
		// TODO: extend this check to other types of conclusions
		if (conclusion.getSuperExpression().occurs()) {
			return defaultVisit((ClassConclusion) conclusion, context);
		}	
		return true;
	}

	@Override
	public Boolean visit(SubClassInclusionComposed conclusion, Context context) {
		return defaultVisit(conclusion, context);
	}

	@Override
	public Boolean visit(SubClassInclusionDecomposed conclusion, Context context) {
		return defaultVisit(conclusion, context);
	}
}