/**
 * 
 */
package org.semanticweb.elk.proofs.inferences.mapping;
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

import java.util.Collection;

import org.semanticweb.elk.owl.implementation.ElkObjectFactoryImpl;
import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.owl.interfaces.ElkClassExpression;
import org.semanticweb.elk.owl.interfaces.ElkDisjointClassesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectFactory;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyChain;
import org.semanticweb.elk.owl.interfaces.ElkObjectSomeValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkObjectUnionOf;
import org.semanticweb.elk.owl.interfaces.ElkReflexiveObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSubClassOfAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyExpression;
import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyOfAxiom;
import org.semanticweb.elk.owl.predefined.PredefinedElkClass;
import org.semanticweb.elk.proofs.expressions.derived.DerivedExpression;
import org.semanticweb.elk.proofs.expressions.derived.DerivedExpressionFactory;
import org.semanticweb.elk.proofs.expressions.lemmas.ElkLemmaObjectFactory;
import org.semanticweb.elk.proofs.expressions.lemmas.ElkReflexivePropertyChainLemma;
import org.semanticweb.elk.proofs.expressions.lemmas.ElkSubClassOfLemma;
import org.semanticweb.elk.proofs.expressions.lemmas.ElkSubPropertyChainOfLemma;
import org.semanticweb.elk.proofs.expressions.lemmas.impl.ElkLemmaObjectFactoryImpl;
import org.semanticweb.elk.proofs.inferences.Inference;
import org.semanticweb.elk.proofs.inferences.InferenceRule;
import org.semanticweb.elk.proofs.inferences.InferenceVisitor;
import org.semanticweb.elk.proofs.inferences.classes.ClassSubsumption;
import org.semanticweb.elk.proofs.inferences.classes.ConjunctionComposition;
import org.semanticweb.elk.proofs.inferences.classes.ConjunctionDecomposition;
import org.semanticweb.elk.proofs.inferences.classes.DisjointnessContradiction;
import org.semanticweb.elk.proofs.inferences.classes.DisjunctionComposition;
import org.semanticweb.elk.proofs.inferences.classes.ExistentialComposition;
import org.semanticweb.elk.proofs.inferences.classes.ExistentialCompositionViaChain;
import org.semanticweb.elk.proofs.inferences.classes.InconsistentDisjointness;
import org.semanticweb.elk.proofs.inferences.classes.NegationContradiction;
import org.semanticweb.elk.proofs.inferences.classes.ReflexiveExistentialComposition;
import org.semanticweb.elk.proofs.inferences.properties.ChainSubsumption;
import org.semanticweb.elk.proofs.inferences.properties.ReflexiveComposition;
import org.semanticweb.elk.proofs.inferences.properties.ReflexivityViaSubsumption;
import org.semanticweb.elk.proofs.inferences.properties.SubsumptionViaReflexivity;
import org.semanticweb.elk.proofs.inferences.properties.ToldReflexivity;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedBinaryPropertyChain;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectIntersectionOf;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedPropertyChainVisitor;
import org.semanticweb.elk.reasoner.saturation.tracing.SideConditionLookup;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ClassInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedBackwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedConjunction;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromDisjointSubsumers;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromInconsistentDisjointnessAxiom;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromNegation;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromOwlNothing;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedConjunction;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedExistentialBackwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedExistentialForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DisjointSubsumerFromSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.InitializationSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.PropagatedContradiction;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.PropagatedSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ReflexiveSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ReversedForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.SubClassOfSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.TracedPropagation;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.BottomUpPropertySubsumptionInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.LeftReflexiveSubPropertyChainInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ObjectPropertyInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.PropertyChainInitialization;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ReflexivePropertyChainInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ReflexiveToldSubObjectProperty;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.RightReflexiveSubPropertyChainInference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.SubObjectProperty;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.SubPropertyChain;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.ToldReflexiveProperty;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.properties.TopDownPropertySubsumptionInference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps one or more lower-level inferences to a single {@link Inference}. If the given low-level
 * inference cannot be mapped, one the following is returned:
 * 
 * i) {@link #STOP} meaning that the low-level inference is not a part of any higher-level inference (or a part of the inference which already
 * was produced).
 * ii) {@link #CONTINUE} meaning that the low-level inference is a part of some higher-level inference and this mapper needs to see
 * more low-level inferences to produce it.  
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class SingleInferenceMapper {
	
	private static final Logger LOGGER_ = LoggerFactory.getLogger(SingleInferenceMapper.class);
	
	static final Inference STOP = new Unmappable();
	
	static final Inference CONTINUE = new Unmappable();
	
	private final ElkObjectFactory factory_;
	
	private final ElkLemmaObjectFactory lemmaObjectFactory_;

	private final SideConditionLookup sideConditionLookup_;
	
	private final DerivedExpressionFactory exprFactory_;

	public SingleInferenceMapper(DerivedExpressionFactory exprFactory) {
		factory_ = new ElkObjectFactoryImpl();
		lemmaObjectFactory_ = new ElkLemmaObjectFactoryImpl();
		sideConditionLookup_ = new SideConditionLookup();
		exprFactory_ = exprFactory;
	}
	
	public Inference map(ClassInference inference,
			IndexedClassExpression whereStored) {
		Inference userInf = inference.acceptTraced(new MappingVisitor(), whereStored);
		
		LOGGER_.trace("Mapped {} in {} => {}", inference, whereStored, userInf);
		
		return userInf;
	}

	public Inference map(ObjectPropertyInference inference) {
		Inference userInf = inference.acceptTraced(new MappingVisitor(), null);
		
		LOGGER_.trace("Mapped {} => {}", inference, userInf);
		
		return userInf;
	}
	
	/**
	 * Special kinds of {@link Inference} to return as instructions to the outer code.
	 * 
	 * @author Pavel Klinov
	 *
	 * pavel.klinov@uni-ulm.de
	 */
	static class Unmappable implements Inference {

		@Override
		public Collection<? extends DerivedExpression> getPremises() {
			return null;
		}

		@Override
		public DerivedExpression getConclusion() {
			return null;
		}

		@Override
		public InferenceRule getRule() {
			return null;
		}

		@Override
		public <I, O> O accept(InferenceVisitor<I, O> visitor, I input) {
			return null;
		}
		
	}
	
	/**
	 * The visitor which converts inferences. Returns null if the low-level
	 * inference should not be represented on the user level.
	 * 
	 * @author Pavel Klinov
	 * 
	 *         pavel.klinov@uni-ulm.de
	 */
	private class MappingVisitor
			implements
			org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.ClassInferenceVisitor<IndexedClassExpression, Inference>,
			org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.ObjectPropertyInferenceVisitor<Void, Inference> {

		@Override
		public Inference visit(InitializationSubsumer<?> inference,
				IndexedClassExpression parameter) {
			// don't map init inferences
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(SubClassOfSubsumer<?> inference,
				IndexedClassExpression parameter) {
			ElkAxiom sideCondition = sideConditionLookup_.lookup(inference);
			
			return new ClassSubsumption(sideCondition, Deindexer.deindex(parameter),
					Deindexer.deindex(inference.getExpression()),
					Deindexer.deindex(inference.getPremise().getExpression()),
					factory_, exprFactory_);
		}

		@Override
		public Inference visit(ComposedConjunction inference,
				IndexedClassExpression input) {
			return new ConjunctionComposition(Deindexer.deindex(input),
					Deindexer.deindex(inference.getFirstConjunct()
							.getExpression()), Deindexer.deindex(inference
							.getSecondConjunct().getExpression()), factory_, exprFactory_);
		}

		@Override
		public Inference visit(DecomposedConjunction inference,
				IndexedClassExpression input) {
			IndexedClassExpression conclusion = inference.getExpression();
			IndexedObjectIntersectionOf conjunction = inference
					.getConjunction().getExpression();
			ElkClassExpression sub = Deindexer.deindex(input);
			ElkClassExpression other = Deindexer
					.deindex(conclusion == conjunction.getFirstConjunct() ? conjunction
							.getSecondConjunct() : conjunction
							.getFirstConjunct());

			return new ConjunctionDecomposition(sub, Deindexer.deindex(conclusion),
					other, factory_, exprFactory_);
		}

		@Override
		public Inference visit(PropagatedSubsumer inference,
				IndexedClassExpression whereStored) {
			// the left premise is an axiom with a simple existential on the right
			ElkClassExpression c = Deindexer.deindex(whereStored);
			ElkObjectProperty r = Deindexer.deindex(inference.getBackwardLink().getRelation());
			ElkClassExpression d = Deindexer.deindex(inference.getInferenceContextRoot(whereStored));
			ElkObjectProperty s = Deindexer.deindex(inference.getExpression().getRelation());
			ElkClassExpression e = Deindexer.deindex(inference.getExpression().getFiller());
			ElkObjectSomeValuesFrom rSomeD = factory_.getObjectSomeValuesFrom(r, d);
			ElkObjectSomeValuesFrom sSomeE = factory_.getObjectSomeValuesFrom(s, e);
			
			return new ExistentialComposition(c, sSomeE,
					factory_.getSubClassOfAxiom(c, rSomeD),
					factory_.getSubClassOfAxiom(d, e),
					factory_.getSubObjectPropertyOfAxiom(r, s), factory_, exprFactory_);
		}

		@Override
		public Inference visit(ReflexiveSubsumer<?> inference,
				IndexedClassExpression input) {
			ElkClassExpression sub = Deindexer.deindex(input);
			ElkClassExpression sup = Deindexer.deindex(inference.getExpression());
			ElkSubClassOfAxiom conclusion = factory_.getSubClassOfAxiom(sub, sup);
			ElkObjectProperty property = inference.getReflexivityPremise()
					.getPropertyChain().getElkObjectProperty();

			return new ReflexiveExistentialComposition(conclusion,
					factory_.getReflexiveObjectPropertyAxiom(property),
					exprFactory_);
		}
		
		// Creates existential composition inferences by analyzing the chain premise. Depending on whether
		// its LHS and RHS are object properties or chains, it creates the corresponding premise expressions (out of lemmas or axioms).
		private ExistentialCompositionViaChain createExistentialCompositionInference(
				DerivedExpression conclusion,
				ElkClassExpression d,
				ElkClassExpression e,
				SubPropertyChain<?,?> chainPremise,
				ElkSubClassOfAxiom leftExistentialPremise,
				ElkSubObjectPropertyOfAxiom leftChainPremise,
				ElkObjectPropertyAxiom chainAxiom
				) {
			DerivedExpression rightExistentialPremise = null;
			DerivedExpression rightChainSubsumptionPremise = null;
			ElkSubObjectPropertyExpression ss = null;
			// create the right property chain premise
			if (chainPremise.getSubPropertyChain() instanceof IndexedBinaryPropertyChain) {
				ss = Deindexer.deindex(chainPremise.getSubPropertyChain());
				// a lemma
				rightExistentialPremise = exprFactory_.create(lemmaObjectFactory_.getSubClassOfLemma(d, lemmaObjectFactory_.getComplexObjectSomeValuesFrom(ss, e)));
			}
			else {
				ss = Deindexer.deindex(chainPremise.getSubPropertyChain());
				// an axiom
				rightExistentialPremise = exprFactory_.create(factory_.getSubClassOfAxiom(d, factory_.getObjectSomeValuesFrom((ElkObjectProperty) ss, e)));
			}
			// now create the right existential premise
			if (chainPremise.getSuperPropertyChain() instanceof IndexedBinaryPropertyChain) {
				// a lemma
				ElkSubObjectPropertyExpression ssPrime = Deindexer.deindex(chainPremise.getSuperPropertyChain());				
				
				rightChainSubsumptionPremise = exprFactory_.create(lemmaObjectFactory_.getSubPropertyChainOfLemma(ss, ssPrime));
			}
			else {
				// an axiom
				ElkObjectProperty ssPrime = (ElkObjectProperty) Deindexer.deindex(chainPremise.getSuperPropertyChain());
				
				rightChainSubsumptionPremise = exprFactory_.create(factory_.getSubObjectPropertyOfAxiom(ss, ssPrime));
			}
			
			return new ExistentialCompositionViaChain(
					conclusion, 
					exprFactory_.create(leftExistentialPremise), 
					rightExistentialPremise, 
					exprFactory_.create(leftChainPremise), 
					rightChainSubsumptionPremise, 
					chainAxiom == null ? null : exprFactory_.createAsserted(chainAxiom));
		}

		@Override
		public Inference visit(ComposedBackwardLink inference, 	IndexedClassExpression whereStored) {
			SubObjectProperty leftPropertyPremise = inference.getLeftSubObjectProperty();
			SubPropertyChain<?,?> rightChainPremise = inference.getRightSubObjectPropertyChain();			
			ElkObjectProperty r = Deindexer.deindex(leftPropertyPremise.getSubPropertyChain());
			ElkClassExpression c = Deindexer.deindex(inference.getBackwardLink().getSource());
			ElkClassExpression d = Deindexer.deindex(inference.getInferenceContextRoot(whereStored));
			ElkClassExpression rSomeD = factory_.getObjectSomeValuesFrom(r, d);
			ElkClassExpression e = Deindexer.deindex(inference.getForwardLink().getTarget());
			ElkObjectProperty h = Deindexer.deindex(inference.getRelation());
			ElkClassExpression hSomeE = factory_.getObjectSomeValuesFrom(h, e);
			ElkSubClassOfAxiom firstExPremise = factory_.getSubClassOfAxiom(c, rSomeD);
			ElkObjectProperty rPrime = Deindexer.deindex(leftPropertyPremise.getSuperPropertyChain());
			ElkSubObjectPropertyOfAxiom propSubsumption = factory_.getSubObjectPropertyOfAxiom(r, rPrime);
			ElkSubClassOfAxiom conclusion = factory_.getSubClassOfAxiom(c, hSomeE);
			// TODO will get rid of this cast later
			ElkObjectPropertyAxiom chainAxiom = (ElkObjectPropertyAxiom) sideConditionLookup_.lookup(inference);		
			
			return createExistentialCompositionInference(exprFactory_.create(conclusion), d, e, rightChainPremise, firstExPremise, propSubsumption, chainAxiom);
		}

		@Override
		public Inference visit(ComposedForwardLink inference, IndexedClassExpression whereStored) {
			SubObjectProperty leftPropertyPremise = inference.getLeftSubObjectProperty();
			SubPropertyChain<?,?> rightChainPremise = inference.getRightSubObjectPropertyChain();			
			ElkObjectProperty r = Deindexer.deindex(leftPropertyPremise.getSubPropertyChain());
			ElkClassExpression c = Deindexer.deindex(whereStored);
			ElkClassExpression d = Deindexer.deindex(inference.getInferenceContextRoot(whereStored));
			ElkClassExpression rSomeD = factory_.getObjectSomeValuesFrom(r, d);
			ElkClassExpression e = Deindexer.deindex(inference.getTarget());
			ElkSubClassOfAxiom firstExPremise = factory_.getSubClassOfAxiom(c, rSomeD);
			ElkObjectProperty rPrime = Deindexer.deindex(leftPropertyPremise.getSuperPropertyChain());
			ElkSubObjectPropertyOfAxiom propSubsumption = factory_.getSubObjectPropertyOfAxiom(r, rPrime);
			ElkSubObjectPropertyExpression conclusionChain = Deindexer.deindex(inference.getRelation()); 
			ElkSubClassOfLemma conclusion = lemmaObjectFactory_.getSubClassOfLemma(c, lemmaObjectFactory_.getComplexObjectSomeValuesFrom(conclusionChain, e));
			
			return createExistentialCompositionInference(exprFactory_.create(conclusion), d, e, rightChainPremise, firstExPremise, propSubsumption, null);
		}

		@Override
		public Inference visit(ReversedForwardLink inference,
				IndexedClassExpression input) {
			// not a self-contained user inference
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(DecomposedExistentialBackwardLink inference,
				IndexedClassExpression input) {
			// not a self-contained user inference
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(DecomposedExistentialForwardLink inference,
				IndexedClassExpression input) {
			// not a self-contained user inference
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(TracedPropagation inference,
				IndexedClassExpression input) {
			// not a self-contained user inference
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(
				ContradictionFromInconsistentDisjointnessAxiom inference,
				IndexedClassExpression input) {
			ElkDisjointClassesAxiom sideCondition = (ElkDisjointClassesAxiom) sideConditionLookup_.lookup(inference);
			ElkClassExpression c = Deindexer.deindex(input);
			ElkClassExpression d = Deindexer.deindex(inference.getPremise().getExpression());
			
			return new InconsistentDisjointness(c, d, sideCondition, factory_, exprFactory_);
		}

		@Override
		public Inference visit(ContradictionFromDisjointSubsumers inference,
				IndexedClassExpression input) {
			ElkDisjointClassesAxiom sideCondition = (ElkDisjointClassesAxiom) sideConditionLookup_.lookup(inference);
			ElkClassExpression c = Deindexer.deindex(input);
			ElkClassExpression d = Deindexer.deindex(inference.getPremises()[0].getMember());
			ElkClassExpression e = Deindexer.deindex(inference.getPremises()[1].getMember());
			// TODO check this
			return new DisjointnessContradiction(c, d, e, sideCondition, factory_, exprFactory_);
		}

		@Override
		public Inference visit(ContradictionFromNegation inference,
				IndexedClassExpression input) {
			ElkClassExpression c = Deindexer.deindex(input);
			ElkClassExpression d = Deindexer.deindex(inference.getPositivePremise().getExpression());
			
			return new NegationContradiction(c, d, factory_, exprFactory_);
		}

		@Override
		public Inference visit(ContradictionFromOwlNothing inference,
				IndexedClassExpression input) {
			// not a self-contained user inference but we need to see inferences for owl:Nothing and map those.
			return SingleInferenceMapper.CONTINUE;
		}

		@Override
		public Inference visit(PropagatedContradiction inference,
				IndexedClassExpression whereStored) {
			// the left premise is an axiom with a simple existential on the right
			ElkClassExpression c = Deindexer.deindex(whereStored);
			ElkObjectProperty r = Deindexer.deindex(inference.getLinkPremise().getRelation());
			ElkClassExpression d = Deindexer.deindex(inference.getInferenceContextRoot(whereStored));
			ElkObjectSomeValuesFrom rSomeD = factory_.getObjectSomeValuesFrom(r, d);

			return new ExistentialComposition(c, PredefinedElkClass.OWL_NOTHING,
					factory_.getSubClassOfAxiom(c, rSomeD),
					factory_.getSubClassOfAxiom(d, PredefinedElkClass.OWL_NOTHING),
					factory_.getSubObjectPropertyOfAxiom(r, r), factory_, exprFactory_);
		}

		@Override
		public Inference visit(DisjointSubsumerFromSubsumer inference,
				IndexedClassExpression input) {
			// not a self-contained user inference
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(
				org.semanticweb.elk.reasoner.saturation.tracing.inferences.DisjunctionComposition inference,
				IndexedClassExpression input) {
			ElkClassExpression c = Deindexer.deindex(input);
			ElkClassExpression d = Deindexer.deindex(inference.getPremise().getExpression());
			ElkObjectUnionOf dOrE = (ElkObjectUnionOf) Deindexer.deindex(inference.getExpression());
			
			return new DisjunctionComposition(c, dOrE, d, factory_, exprFactory_);
		}

		@Override
		public Inference visit(TopDownPropertySubsumptionInference inference,
				Void input) {
			final ElkObjectProperty sup = inference.getSuperPropertyChain().getElkObjectProperty();
			ElkSubObjectPropertyOfAxiom sideCondition = (ElkSubObjectPropertyOfAxiom) sideConditionLookup_.lookup(inference);
			ElkObjectProperty h = inference.getPremise().getSubPropertyChain().getElkObjectProperty();
			ElkSubObjectPropertyOfAxiom conclusion = inference.getSubPropertyChain().accept(new IndexedPropertyChainVisitor<ElkSubObjectPropertyOfAxiom>() {

				@Override
				public ElkSubObjectPropertyOfAxiom visit(IndexedObjectProperty property) {
					ElkObjectProperty sub = Deindexer.deindex(property);
					
					return factory_.getSubObjectPropertyOfAxiom(sub, sup);
				}

				@Override
				public ElkSubObjectPropertyOfAxiom visit(IndexedBinaryPropertyChain chain) {
					ElkObjectPropertyChain sub = Deindexer.deindex(chain);
					
					return factory_.getSubObjectPropertyOfAxiom(sub, sup);
				}
			});
			
			return new ChainSubsumption(conclusion, exprFactory_.createAsserted(sideCondition), factory_.getSubObjectPropertyOfAxiom(h, sup), exprFactory_);
		}

		@Override
		public Inference visit(final BottomUpPropertySubsumptionInference inference,
				Void input) {
			final ElkObjectProperty s = inference.getSuperPropertyChain().getElkObjectProperty();
			final ElkSubObjectPropertyExpression sub = inference.getSubPropertyChain().accept(new IndexedPropertyChainVisitor<ElkSubObjectPropertyExpression>() {

				@Override
				public ElkSubObjectPropertyExpression visit(IndexedObjectProperty property) {
					return Deindexer.deindex(property);
				}

				@Override
				public ElkSubObjectPropertyExpression visit(IndexedBinaryPropertyChain chain) {
					return Deindexer.deindex(chain);
				}
			});
			
			return inference.getFirstPremise().getSuperPropertyChain().accept(new IndexedPropertyChainVisitor<ChainSubsumption>() {

				@Override
				public ChainSubsumption visit(IndexedObjectProperty hh) {
					// first premise is an axiom
					DerivedExpression firstPremise = exprFactory_.create(factory_.getSubObjectPropertyOfAxiom(sub, hh.getElkObjectProperty()));
					ElkSubObjectPropertyOfAxiom secondPremise = factory_.getSubObjectPropertyOfAxiom(hh.getElkObjectProperty(), s);
					
					return new ChainSubsumption(factory_.getSubObjectPropertyOfAxiom(sub, s), firstPremise, secondPremise, exprFactory_);
				}

				@Override
				public ChainSubsumption visit(IndexedBinaryPropertyChain hh) {
					// first premise is not an axiom
					ElkObjectPropertyChain chain = Deindexer.deindex(hh);
					DerivedExpression firstPremise = exprFactory_.create(lemmaObjectFactory_.getSubPropertyChainOfLemma(sub, chain));
					ElkSubObjectPropertyOfAxiom secondPremise = factory_.getSubObjectPropertyOfAxiom(chain, s);
					
					return new ChainSubsumption(factory_.getSubObjectPropertyOfAxiom(sub, s), firstPremise, secondPremise, exprFactory_);
				}
			});
		}

		@Override
		public Inference visit(PropertyChainInitialization inference, Void input) {
			return SingleInferenceMapper.STOP;
		}

		@Override
		public Inference visit(ToldReflexiveProperty inference, Void input) {
			return new ToldReflexivity((ElkReflexiveObjectPropertyAxiom) sideConditionLookup_.lookup(inference), factory_, exprFactory_);
		}

		@Override
		public Inference visit(final ReflexiveToldSubObjectProperty inference,
				Void input) {
			ElkSubObjectPropertyOfAxiom sideCondition = (ElkSubObjectPropertyOfAxiom) sideConditionLookup_.lookup(inference);
			DerivedExpression premise = inference.getSubProperty().getPropertyChain().accept(reflexiveChainExpressionCreator());
			
			return new ReflexivityViaSubsumption(sideCondition, premise, factory_, exprFactory_);
		}

		@Override
		public Inference visit(final ReflexivePropertyChainInference inference,
				Void input) {
			ElkObjectProperty r = inference.getLeftReflexiveProperty().getPropertyChain().getElkObjectProperty();
			DerivedExpression second = inference.getRightReflexivePropertyChain().getPropertyChain().accept(reflexiveChainExpressionCreator());
			ElkReflexivePropertyChainLemma conclusion = lemmaObjectFactory_.getReflexivePropertyChainLemma(Deindexer.deindex(inference.getPropertyChain()));
			
			return new ReflexiveComposition(conclusion, factory_.getReflexiveObjectPropertyAxiom(r), second, exprFactory_);
		}

		@Override
		public Inference visit(
				LeftReflexiveSubPropertyChainInference inference, Void input) {
			ElkObjectProperty r = inference.getReflexivePremise().getPropertyChain().getElkObjectProperty();
			ElkReflexiveObjectPropertyAxiom premise = factory_.getReflexiveObjectPropertyAxiom(r);
			ElkSubPropertyChainOfLemma conclusion = lemmaObjectFactory_
					.getSubPropertyChainOfLemma(
							Deindexer.deindex(inference.getSubPropertyChain()),
							Deindexer.deindex(inference.getSuperPropertyChain()));
			
			return new SubsumptionViaReflexivity(conclusion, premise, exprFactory_);
		}

		@Override
		public Inference visit(
				RightReflexiveSubPropertyChainInference inference, Void input) {
			DerivedExpression premise = inference.getReflexivePremise().getPropertyChain().accept(reflexiveChainExpressionCreator());
			ElkSubPropertyChainOfLemma conclusion = lemmaObjectFactory_
					.getSubPropertyChainOfLemma(
							Deindexer.deindex(inference.getSubPropertyChain()),
							Deindexer.deindex(inference.getSuperPropertyChain()));
			
			return new SubsumptionViaReflexivity(conclusion, premise, exprFactory_);
		}
		
		// creates a visitor which creates an axiom or a lemma out of a reflexive chain
		private IndexedPropertyChainVisitor<DerivedExpression> reflexiveChainExpressionCreator() {
			return new IndexedPropertyChainVisitor<DerivedExpression>() {

				@Override
				public DerivedExpression visit(IndexedObjectProperty property) {
					return exprFactory_.create(factory_.getReflexiveObjectPropertyAxiom(property.getElkObjectProperty()));
				}

				@Override
				public DerivedExpression visit(IndexedBinaryPropertyChain chain) {
					// can't represent reflexive chains as axioms
					return exprFactory_.create(lemmaObjectFactory_.getReflexivePropertyChainLemma(Deindexer.deindex(chain)));
				}
				
			};
		}
	}
}
