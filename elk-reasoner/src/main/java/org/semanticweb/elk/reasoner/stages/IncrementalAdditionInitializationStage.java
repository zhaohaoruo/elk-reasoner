package org.semanticweb.elk.reasoner.stages;

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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.semanticweb.elk.owl.interfaces.ElkAnnotationProperty;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkDataProperty;
import org.semanticweb.elk.owl.interfaces.ElkDatatype;
import org.semanticweb.elk.owl.interfaces.ElkEntity;
import org.semanticweb.elk.owl.interfaces.ElkNamedIndividual;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.semanticweb.elk.owl.visitors.ElkEntityVisitor;
import org.semanticweb.elk.reasoner.incremental.IncrementalChangesInitialization;
import org.semanticweb.elk.reasoner.incremental.IncrementalStages;
import org.semanticweb.elk.reasoner.indexing.hierarchy.DifferentialIndex;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexObjectConverter;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.saturation.SaturationState.ExtendedWriter;
import org.semanticweb.elk.reasoner.saturation.conclusions.ConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.ChainableRule;
import org.semanticweb.elk.reasoner.saturation.rules.RuleApplicationVisitor;
import org.semanticweb.elk.util.collections.Operations;

/**
 * 
 * 
 */
class IncrementalAdditionInitializationStage extends
		AbstractIncrementalChangesInitializationStage {

	public IncrementalAdditionInitializationStage(
			AbstractReasonerState reasoner, AbstractReasonerStage... preStages) {
		super(reasoner, preStages);
	}

	@Override
	protected IncrementalStages stage() {
		return IncrementalStages.ADDITIONS_INIT;
	}

	@Override
	boolean preExecute() {
		if (!super.preExecute())
			return false;

		DifferentialIndex diffIndex = reasoner.ontologyIndex;
		ChainableRule<Context> changedInitRules = null;
		Map<IndexedClassExpression, ChainableRule<Context>> changedRulesByCE = null;
		Collection<Collection<Context>> inputs = Collections.emptyList();
		RuleApplicationVisitor ruleAppVisitor = getRuleApplicationVisitor(stageStatistics_
				.getRuleStatistics());
		ConclusionVisitor<?> conclusionVisitor = getConclusionVisitor(stageStatistics_
				.getConclusionStatistics());
		// first, create and init contexts for new classes
		final IndexObjectConverter converter = reasoner.objectCache_
				.getIndexObjectConverter();
		final ExtendedWriter writer = reasoner.saturationState
				.getExtendedWriter(conclusionVisitor);

		for (ElkEntity newEntity : Operations.concat(reasoner.ontologyIndex.getAddedClasses(), reasoner.ontologyIndex.getAddedIndividuals())) {
			IndexedClassExpression ice = newEntity.accept(new ElkEntityVisitor<IndexedClassExpression>(){

				@Override
				public IndexedClassExpression visit(ElkAnnotationProperty elkAnnotationProperty) {
					return null;
				}

				@Override
				public IndexedClassExpression visit(ElkClass elkClass) {
					return converter.visit(elkClass);
				}

				@Override
				public IndexedClassExpression visit(ElkDataProperty elkDataProperty) {
					return null;
				}

				@Override
				public IndexedClassExpression visit(ElkDatatype elkDatatype) {
					return null;
				}

				@Override
				public IndexedClassExpression visit(ElkNamedIndividual elkNamedIndividual) {
					return converter.visit(elkNamedIndividual);
				}

				@Override
				public IndexedClassExpression visit(ElkObjectProperty elkObjectProperty) {
					return null;
				}});
			
			if (ice.getContext() == null) {
				writer.getCreateContext(ice);
			}
		}
		
		/*for (ElkClass newClass : reasoner.ontologyIndex.getAddedClasses()) {
			IndexedClass ic = (IndexedClass) converter.visit(newClass);

			if (ic.getContext() == null) {
				writer.getCreateContext(ic);
			} else {
				// TODO Figure out why some added classes have contexts
				// This happens when class is removed and then re-added
				// throw new RuntimeException(ic + ": " +
				// ic.getContext().getSubsumers());
			}
		}*/

		changedInitRules = diffIndex.getAddedContextInitRules();
		changedRulesByCE = diffIndex.getAddedContextRulesByClassExpressions();

		if (changedInitRules != null || !changedRulesByCE.isEmpty()) {
			inputs = Operations.split(reasoner.saturationState.getContexts(),
					128);
		}

		this.initialization_ = new IncrementalChangesInitialization(inputs,
				changedInitRules, changedRulesByCE, reasoner.saturationState,
				reasoner.getProcessExecutor(), ruleAppVisitor,
				conclusionVisitor, workerNo, reasoner.getProgressMonitor());
		return true;
	}

	@Override
	boolean postExecute() {
		if (!super.postExecute())
			return false;
		this.initialization_ = null;
		reasoner.ontologyIndex.commitAddedRules();
		reasoner.ontologyIndex.clearClassSignatureChanges();
		reasoner.ontologyIndex.clearIndividualSignatureChanges();
		
		return true;
	}

}