/*
 * #%L
 * ELK OWL Object Interfaces
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.owl.printers;

import java.io.IOException;

import org.semanticweb.elk.owl.interfaces.ElkAnnotationAxiom;
import org.semanticweb.elk.owl.interfaces.ElkAnnotationProperty;
import org.semanticweb.elk.owl.interfaces.ElkAnonymousIndividual;
import org.semanticweb.elk.owl.interfaces.ElkAsymmetricObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkCardinalityRestriction;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkClassAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkClassExpression;
import org.semanticweb.elk.owl.interfaces.ElkDataAllValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkDataComplementOf;
import org.semanticweb.elk.owl.interfaces.ElkDataExactCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataHasValue;
import org.semanticweb.elk.owl.interfaces.ElkDataIntersectionOf;
import org.semanticweb.elk.owl.interfaces.ElkDataMaxCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataMinCardinality;
import org.semanticweb.elk.owl.interfaces.ElkDataOneOf;
import org.semanticweb.elk.owl.interfaces.ElkDataProperty;
import org.semanticweb.elk.owl.interfaces.ElkDataPropertyAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDataPropertyDomainAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDataPropertyExpression;
import org.semanticweb.elk.owl.interfaces.ElkDataPropertyRangeAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDataRange;
import org.semanticweb.elk.owl.interfaces.ElkDataSomeValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkDataUnionOf;
import org.semanticweb.elk.owl.interfaces.ElkDatatype;
import org.semanticweb.elk.owl.interfaces.ElkDatatypeRestriction;
import org.semanticweb.elk.owl.interfaces.ElkDeclarationAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDifferentIndividualsAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDisjointClassesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDisjointDataPropertiesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDisjointObjectPropertiesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkDisjointUnionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkEntity;
import org.semanticweb.elk.owl.interfaces.ElkEquivalentClassesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkEquivalentDataPropertiesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkEquivalentObjectPropertiesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkFacetRestriction;
import org.semanticweb.elk.owl.interfaces.ElkFunctionalDataPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkFunctionalObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkIndividual;
import org.semanticweb.elk.owl.interfaces.ElkInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkInverseObjectPropertiesAxiom;
import org.semanticweb.elk.owl.interfaces.ElkIrreflexiveObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkLiteral;
import org.semanticweb.elk.owl.interfaces.ElkNamedIndividual;
import org.semanticweb.elk.owl.interfaces.ElkNegativeDataPropertyAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObject;
import org.semanticweb.elk.owl.interfaces.ElkObjectAllValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkObjectComplementOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectExactCardinality;
import org.semanticweb.elk.owl.interfaces.ElkObjectHasSelf;
import org.semanticweb.elk.owl.interfaces.ElkObjectHasValue;
import org.semanticweb.elk.owl.interfaces.ElkObjectIntersectionOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectInverseOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectMaxCardinality;
import org.semanticweb.elk.owl.interfaces.ElkObjectMinCardinality;
import org.semanticweb.elk.owl.interfaces.ElkObjectOneOf;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyChain;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyDomainAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyExpression;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyRangeAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectSomeValuesFrom;
import org.semanticweb.elk.owl.interfaces.ElkObjectUnionOf;
import org.semanticweb.elk.owl.interfaces.ElkPropertyRestriction;
import org.semanticweb.elk.owl.interfaces.ElkCardinalityRestrictionQualified;
import org.semanticweb.elk.owl.interfaces.ElkDataExactCardinalityQualified;
import org.semanticweb.elk.owl.interfaces.ElkDataMaxCardinalityQualified;
import org.semanticweb.elk.owl.interfaces.ElkDataMinCardinalityQualified;
import org.semanticweb.elk.owl.interfaces.ElkObjectExactCardinalityQualified;
import org.semanticweb.elk.owl.interfaces.ElkObjectMaxCardinalityQualified;
import org.semanticweb.elk.owl.interfaces.ElkObjectMinCardinalityQualified;
import org.semanticweb.elk.owl.interfaces.ElkPropertyRestrictionQualified;
import org.semanticweb.elk.owl.interfaces.ElkReflexiveObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSameIndividualAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSubClassOfAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSubDataPropertyOfAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSubObjectPropertyOfAxiom;
import org.semanticweb.elk.owl.interfaces.ElkSymmetricObjectPropertyAxiom;
import org.semanticweb.elk.owl.interfaces.ElkTransitiveObjectPropertyAxiom;
import org.semanticweb.elk.owl.visitors.ElkEntityVisitor;
import org.semanticweb.elk.owl.visitors.ElkObjectVisitor;

/**
 * A ELK Object visitor pattern implementation for printing ELK Objects in OWL 2
 * functional-style syntax.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
class OwlFunctionalStylePrinterVisitor implements ElkObjectVisitor<Void> {

	/**
	 * An auxiliary visitor class to print declarations.
	 * 
	 * @author "Yevgeny Kazakov"
	 * 
	 */
	class EntityPrinter implements ElkEntityVisitor<Void> {

		public Void visit(ElkAnnotationProperty elkAnnotationProperty) {
			write("AnnotationProperty(");
			write(elkAnnotationProperty);
			write(')');
			return null;
		}

		public Void visit(ElkClass elkClass) {
			write("Class(");
			write(elkClass);
			write(')');
			return null;
		}

		public Void visit(ElkDataProperty elkDataProperty) {
			write("DataProperty(");
			write(elkDataProperty);
			write(')');
			return null;
		}

		public Void visit(ElkDatatype elkDatatype) {
			write("Datatype(");
			write(elkDatatype);
			write(')');
			return null;
		}

		public Void visit(ElkNamedIndividual elkNamedIndividual) {
			write("NamedIndividual(");
			write(elkNamedIndividual);
			write(')');
			return null;
		}

		public Void visit(ElkObjectProperty elkObjectProperty) {
			write("ObjectProperty(");
			write(elkObjectProperty);
			write(')');
			return null;
		}

	}

	protected final Appendable writer;

	protected final EntityPrinter entityPrinter;

	/**
	 * Create a printer using a writer which can append strings.
	 * 
	 * @param writer
	 *            the string appender used for printing
	 */
	OwlFunctionalStylePrinterVisitor(Appendable writer) {
		this.writer = writer;
		this.entityPrinter = new EntityPrinter();
	}

	public Void visit(ElkAnnotationAxiom elkAnnotationAxiom) {
		return null;
	}

	public Void visit(ElkAnnotationProperty elkAnnotationProperty) {
		write(elkAnnotationProperty);
		return null;
	}

	public Void visit(ElkAnonymousIndividual elkAnonymousIndividual) {
		write(elkAnonymousIndividual.getNodeId());
		return null;
	}

	public Void visit(
			ElkAsymmetricObjectPropertyAxiom elkAsymmetricObjectPropertyAxiom) {
		write("AsymmetricObjectProperty(");
		write(elkAsymmetricObjectPropertyAxiom.getObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(ElkClass elkClass) {
		write(elkClass);
		return null;
	}

	public Void visit(ElkClassAssertionAxiom elkClassAssertionAxiom) {
		write("ClassAssertion(");
		write(elkClassAssertionAxiom.getClassExpression());
		write(' ');
		write(elkClassAssertionAxiom.getIndividual());
		write(')');
		return null;
	}

	public Void visit(ElkDataAllValuesFrom elkDataAllValuesFrom) {
		write("DataAllValuesFrom(");
		write((ElkPropertyRestrictionQualified<ElkDataPropertyExpression, ElkDataRange>) elkDataAllValuesFrom);
		write(')');
		return null;
	}

	public Void visit(ElkDataComplementOf elkDataComplementOf) {
		write("DataComplementOf(");
		write(elkDataComplementOf.getDataRange());
		write(')');
		return null;
	}

	public Void visit(ElkDataExactCardinality elkDataExactCardinality) {
		write("DataExactCardinality(");
		write((ElkCardinalityRestriction<ElkDataPropertyExpression>) elkDataExactCardinality);
		write(')');
		return null;
	}

	public Void visit(
			ElkDataExactCardinalityQualified elkDataExactCardinalityQualified) {
		write("DataExactCardinality(");
		write((ElkCardinalityRestrictionQualified<ElkDataPropertyExpression, ElkDataRange>) elkDataExactCardinalityQualified);
		write(')');
		return null;
	}

	public Void visit(ElkDataHasValue elkDataHasValue) {
		write("DataHasValue(");
		write((ElkPropertyRestrictionQualified<ElkDataPropertyExpression, ElkLiteral>) elkDataHasValue);
		write(')');
		return null;
	}

	public Void visit(ElkDataIntersectionOf elkDataIntersectionOf) {
		write("DataIntersectionOf(");
		write(elkDataIntersectionOf.getDataRanges());
		write(')');
		return null;
	}

	public Void visit(ElkDataMaxCardinality elkDataMaxCardinality) {
		write("DataMaxCardinality(");
		write((ElkCardinalityRestriction<ElkDataPropertyExpression>) elkDataMaxCardinality);
		write(')');
		return null;
	}

	public Void visit(
			ElkDataMaxCardinalityQualified elkDataMaxCardinalityQualified) {
		write("DataMaxCardinality(");
		write((ElkCardinalityRestrictionQualified<ElkDataPropertyExpression, ElkDataRange>) elkDataMaxCardinalityQualified);
		write(')');
		return null;
	}

	public Void visit(ElkDataMinCardinality elkDataMinCardinality) {
		write("DataMinCardinality(");
		write((ElkCardinalityRestriction<ElkDataPropertyExpression>) elkDataMinCardinality);
		write(')');
		return null;
	}

	public Void visit(
			ElkDataMinCardinalityQualified elkDataMinCardinalityQualified) {
		write("DataMinCardinality(");
		write((ElkCardinalityRestrictionQualified<ElkDataPropertyExpression, ElkDataRange>) elkDataMinCardinalityQualified);
		write(')');
		return null;
	}

	public Void visit(ElkDataOneOf elkDataOneOf) {
		write("DataOneOf(");
		write(elkDataOneOf.getLiterals());
		write(')');
		return null;
	}

	public Void visit(ElkDataProperty elkDataProperty) {
		write(elkDataProperty);
		return null;
	}

	public Void visit(
			ElkDataPropertyAssertionAxiom elkDataPropertyAssertionAxiom) {
		write("DataPropertyAssertion(");
		write(elkDataPropertyAssertionAxiom.getDataPropertyExpression());
		write(' ');
		write(elkDataPropertyAssertionAxiom.getIndividual());
		write(' ');
		write(elkDataPropertyAssertionAxiom.getLiteral());
		write(')');
		return null;
	}

	public Void visit(ElkDataPropertyDomainAxiom elkDataPropertyDomainAxiom) {
		write("DataPropertyDomain(");
		write(elkDataPropertyDomainAxiom.getDataPropertyExpression());
		write(' ');
		write(elkDataPropertyDomainAxiom.getClassExpression());
		write(')');
		return null;
	}

	public Void visit(ElkDataPropertyRangeAxiom elkDataPropertyRangeAxiom) {
		write("DataPropertyRange(");
		write(elkDataPropertyRangeAxiom.getDataPropertyExpression());
		write(' ');
		write(elkDataPropertyRangeAxiom.getDataRange());
		write(')');
		return null;
	}

	public Void visit(ElkDataSomeValuesFrom elkDataSomeValuesFrom) {
		write("DataSomeValuesFrom(");
		write((ElkPropertyRestrictionQualified<ElkDataPropertyExpression, ElkDataRange>) elkDataSomeValuesFrom);
		write(')');
		return null;
	}

	public Void visit(ElkDatatype elkDatatype) {
		write(elkDatatype);
		return null;
	}

	public Void visit(ElkDatatypeRestriction elkDatatypeRestriction) {
		write("DatatypeRestriction(");
		write(elkDatatypeRestriction.getDatatype());
		write(' ');
		write(elkDatatypeRestriction.getFacetRestrictions());
		write(')');
		return null;
	}

	public Void visit(ElkDataUnionOf elkDataUnionOf) {
		write("DataUnionOf(");
		write(elkDataUnionOf.getDataRanges());
		write(')');
		return null;
	}

	public Void visit(ElkDeclarationAxiom elkDeclarationAxiom) {
		write("Declaration(");
		elkDeclarationAxiom.getEntity().accept(this.entityPrinter);
		write(')');
		return null;
	}

	public Void visit(ElkDifferentIndividualsAxiom elkDifferentIndividualsAxiom) {
		write("DifferentIndividuals(");
		write(elkDifferentIndividualsAxiom.getIndividuals());
		write(')');
		return null;
	}

	public Void visit(ElkDisjointClassesAxiom elkDisjointClasses) {
		write("DisjointClasses(");
		write(elkDisjointClasses.getClassExpressions());
		write(')');
		return null;
	}

	public Void visit(
			ElkDisjointDataPropertiesAxiom elkDisjointDataPropertiesAxiom) {
		write("DisjointDataProperties(");
		write(elkDisjointDataPropertiesAxiom.getDataPropertyExpressions());
		write(')');
		return null;
	}

	public Void visit(
			ElkDisjointObjectPropertiesAxiom elkDisjointObjectPropertiesAxiom) {
		write("DisjointObjectProperties(");
		write(elkDisjointObjectPropertiesAxiom.getObjectPropertyExpressions());
		write(')');
		return null;
	}

	public Void visit(ElkDisjointUnionAxiom elkDisjointUnionAxiom) {
		write("DisjointUnion(");
		write(elkDisjointUnionAxiom.getDefinedClass());
		write(' ');
		write(elkDisjointUnionAxiom.getClassExpressions());
		write(')');
		return null;
	}

	public Void visit(ElkEquivalentClassesAxiom elkEquivalentClassesAxiom) {
		write("EquivalentClasses(");
		write(elkEquivalentClassesAxiom.getClassExpressions());
		write(')');
		return null;
	}

	public Void visit(
			ElkEquivalentDataPropertiesAxiom elkEquivalentDataProperties) {
		write("EquivalentDataProperties(");
		write(elkEquivalentDataProperties.getDataPropertyExpressions());
		write(')');
		return null;
	}

	public Void visit(
			ElkEquivalentObjectPropertiesAxiom elkEquivalentObjectProperties) {
		write("DisjointObjectProperties(");
		write(elkEquivalentObjectProperties.getObjectPropertyExpressions());
		write(')');
		return null;
	}

	public Void visit(ElkFacetRestriction elkFacetRestriction) {
		write(elkFacetRestriction.getConstrainingFacet());
		write(' ');
		write(elkFacetRestriction.getRestrictionValue());
		return null;
	}

	public Void visit(
			ElkFunctionalDataPropertyAxiom elkFunctionalDataPropertyAxiom) {
		write("FunctionalDataProperty(");
		write(elkFunctionalDataPropertyAxiom.getDataPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(
			ElkFunctionalObjectPropertyAxiom elkFunctionalObjectPropertyAxiom) {
		write("FunctionalObjectProperty(");
		write(elkFunctionalObjectPropertyAxiom.getObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(
			ElkInverseFunctionalObjectPropertyAxiom elkInverseFunctionalObjectPropertyAxiom) {
		write("InverseFunctionalObjectProperty(");
		write(elkInverseFunctionalObjectPropertyAxiom
				.getObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(
			ElkInverseObjectPropertiesAxiom elkInverseObjectPropertiesAxiom) {
		write("InverseObjectProperties(");
		write(elkInverseObjectPropertiesAxiom
				.getFirstObjectPropertyExpression());
		write(' ');
		write(elkInverseObjectPropertiesAxiom
				.getSecondObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(
			ElkIrreflexiveObjectPropertyAxiom elkIrreflexiveObjectPropertyAxiom) {
		write("IrreflexiveObjectProperty(");
		write(elkIrreflexiveObjectPropertyAxiom.getObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(ElkLiteral elkLiteral) {
		write(elkLiteral.getLexicalForm());
		write("^^");
		write(elkLiteral.getDatatype());
		return null;
	}

	public Void visit(ElkNamedIndividual elkNamedIndividual) {
		write(elkNamedIndividual);
		return null;
	}

	public Void visit(
			ElkNegativeDataPropertyAssertionAxiom elkNegativeDataPropertyAssertion) {
		write("NegativeDataPropertyAssertion(");
		write(elkNegativeDataPropertyAssertion.getDataPropertyExpression());
		write(' ');
		write(elkNegativeDataPropertyAssertion.getIndividual());
		write(' ');
		write(elkNegativeDataPropertyAssertion.getLiteral());
		write(')');
		return null;
	}

	public Void visit(
			ElkNegativeObjectPropertyAssertionAxiom elkNegativeObjectPropertyAssertion) {
		write("NegativeObjectPropertyAssertion(");
		write(elkNegativeObjectPropertyAssertion.getObjectPropertyExpression());
		write(' ');
		write(elkNegativeObjectPropertyAssertion.getFirstIndividual());
		write(' ');
		write(elkNegativeObjectPropertyAssertion.getSecondIndividual());
		write(')');
		return null;
	}

	public Void visit(ElkObjectAllValuesFrom elkObjectAllValuesFrom) {
		write("ObjectAllValuesFrom(");
		write((ElkPropertyRestrictionQualified<ElkObjectPropertyExpression, ElkClassExpression>) elkObjectAllValuesFrom);
		write(')');
		return null;
	}

	public Void visit(ElkObjectComplementOf elkObjectComplementOf) {
		write("ObjectComplementOf(");
		write(elkObjectComplementOf.getClassExpression());
		write(')');
		return null;
	}

	public Void visit(ElkObjectExactCardinality elkObjectExactCardinality) {
		write("ObjectExactCardinality(");
		write((ElkCardinalityRestriction<ElkObjectPropertyExpression>) elkObjectExactCardinality);
		write(')');
		return null;
	}

	public Void visit(
			ElkObjectExactCardinalityQualified elkObjectExactCardinalityQualified) {
		write("ObjectExactCardinality(");
		write((ElkCardinalityRestrictionQualified<ElkObjectPropertyExpression, ElkClassExpression>) elkObjectExactCardinalityQualified);
		write(')');
		return null;
	}

	public Void visit(ElkObjectHasSelf elkObjectHasSelf) {
		write("ObjectHasSelf(");
		write((ElkPropertyRestriction<ElkObjectPropertyExpression>) elkObjectHasSelf);
		write(')');
		return null;
	}

	public Void visit(ElkObjectHasValue elkObjectHasValue) {
		write("ObjectHasValue(");
		write((ElkPropertyRestrictionQualified<ElkObjectPropertyExpression, ElkIndividual>) elkObjectHasValue);
		write(')');
		return null;
	}

	public Void visit(ElkObjectIntersectionOf elkObjectIntersectionOf) {
		write("ObjectIntersectionOf(");
		write(elkObjectIntersectionOf.getClassExpressions());
		write(')');
		return null;
	}

	public Void visit(ElkObjectInverseOf elkObjectInverseOf) {
		write("ObjectInverseOf(");
		write(elkObjectInverseOf.getObjectProperty());
		write(')');
		return null;
	}

	public Void visit(ElkObjectMaxCardinality elkObjectMaxCardinality) {
		write("ObjectMaxCardinality(");
		write((ElkCardinalityRestriction<ElkObjectPropertyExpression>) elkObjectMaxCardinality);
		write(')');
		return null;
	}

	public Void visit(
			ElkObjectMaxCardinalityQualified elkObjectMaxCardinalityQualified) {
		write("ObjectMaxCardinality(");
		write((ElkCardinalityRestrictionQualified<ElkObjectPropertyExpression, ElkClassExpression>) elkObjectMaxCardinalityQualified);
		write(')');
		return null;
	}

	public Void visit(ElkObjectMinCardinality elkObjectMinCardinality) {
		write("ObjectMinCardinality(");
		write((ElkCardinalityRestriction<ElkObjectPropertyExpression>) elkObjectMinCardinality);
		write(')');
		return null;
	}

	public Void visit(
			ElkObjectMinCardinalityQualified elkObjectMinCardinalityQualified) {
		write("ObjectMinCardinality(");
		write((ElkCardinalityRestrictionQualified<ElkObjectPropertyExpression, ElkClassExpression>) elkObjectMinCardinalityQualified);
		write(')');
		return null;
	}

	public Void visit(ElkObjectOneOf elkObjectOneOf) {
		write("ObjectOneOf(");
		write(elkObjectOneOf.getIndividuals());
		write(')');
		return null;
	}

	public Void visit(ElkObjectProperty elkObjectProperty) {
		write(elkObjectProperty);
		return null;
	}

	public Void visit(
			ElkObjectPropertyAssertionAxiom elkObjectPropertyAssertionAxiom) {
		write("ObjectPropertyAssertion(");
		write(elkObjectPropertyAssertionAxiom.getObjectPropertyExpression());
		write(' ');
		write(elkObjectPropertyAssertionAxiom.getFirstIndividual());
		write(' ');
		write(elkObjectPropertyAssertionAxiom.getSecondIndividual());
		write(')');
		return null;
	}

	public Void visit(ElkObjectPropertyChain elkObjectPropertyChain) {
		write("ObjectPropertyChain(");
		write(elkObjectPropertyChain.getObjectPropertyExpressions());
		write(')');
		return null;
	}

	public Void visit(ElkObjectPropertyDomainAxiom elkObjectPropertyDomainAxiom) {
		write("ObjectPropertyDomain(");
		write(elkObjectPropertyDomainAxiom.getObjectPropertyExpression());
		write(' ');
		write(elkObjectPropertyDomainAxiom.getClassExpression());
		write(')');
		return null;
	}

	public Void visit(ElkObjectPropertyRangeAxiom elkObjectPropertyRangeAxiom) {
		write("ObjectPropertyRange(");
		write(elkObjectPropertyRangeAxiom.getObjectPropertyExpression());
		write(' ');
		write(elkObjectPropertyRangeAxiom.getClassExpression());
		write(')');
		return null;
	}

	public Void visit(ElkObjectSomeValuesFrom elkObjectSomeValuesFrom) {
		write("ObjectSomeValuesFrom(");
		write((ElkPropertyRestrictionQualified<ElkObjectPropertyExpression, ElkClassExpression>) elkObjectSomeValuesFrom);
		write(')');
		return null;
	}

	public Void visit(ElkObjectUnionOf elkObjectUnionOf) {
		write("ObjectUnionOf(");
		write(elkObjectUnionOf.getClassExpressions());
		write(')');
		return null;
	}

	public Void visit(
			ElkReflexiveObjectPropertyAxiom elkReflexiveObjectPropertyAxiom) {
		write("ReflexiveObjectProperty(");
		write(elkReflexiveObjectPropertyAxiom.getObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(ElkSameIndividualAxiom elkSameIndividualAxiom) {
		write("SameIndividual(");
		write(elkSameIndividualAxiom.getIndividuals());
		write(')');
		return null;
	}

	public Void visit(ElkSubClassOfAxiom elkSubClassOfAxiom) {
		write("SubClassOf(");
		write(elkSubClassOfAxiom.getSubClassExpression());
		write(' ');
		write(elkSubClassOfAxiom.getSuperClassExpression());
		write(')');
		return null;
	}

	public Void visit(ElkSubDataPropertyOfAxiom elkSubDataPropertyOfAxiom) {
		write("SubDataPropertyOf(");
		write(elkSubDataPropertyOfAxiom.getSubDataPropertyExpression());
		write(' ');
		write(elkSubDataPropertyOfAxiom.getSuperDataPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(ElkSubObjectPropertyOfAxiom elkSubObjectPropertyOfAxiom) {
		write("SubObjectPropertyOf(");
		write(elkSubObjectPropertyOfAxiom.getSubObjectPropertyExpression());
		write(' ');
		write(elkSubObjectPropertyOfAxiom.getSuperObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(
			ElkSymmetricObjectPropertyAxiom elkSymmetricObjectPropertyAxiom) {
		write("SymmetricObjectProperty(");
		write(elkSymmetricObjectPropertyAxiom.getObjectPropertyExpression());
		write(')');
		return null;
	}

	public Void visit(
			ElkTransitiveObjectPropertyAxiom elkTransitiveObjectPropertyAxiom) {
		write("SymmetricObjectProperty(");
		write(elkTransitiveObjectPropertyAxiom.getObjectPropertyExpression());
		write(')');
		return null;
	}

	protected final void write(char ch) {
		try {
			writer.append(ch);
		} catch (IOException e) {
			throw new PrintingException(e.getMessage(), e.getCause());
		}
	}

	protected final void write(ElkEntity elkEntity) {
		write('<');
		write(elkEntity.getIri().asString());
		write('>');
	}

	protected final void write(ElkObject elkObject) {
		elkObject.accept(this);
	}

	protected final void write(Integer n) {
		try {
			writer.append(n.toString());
		} catch (IOException e) {
			throw new PrintingException(e.getMessage(), e.getCause());
		}
	}

	protected final void write(Iterable<? extends ElkObject> elkObjects) {
		boolean first = true;
		for (ElkObject elkObject : elkObjects) {
			if (!first)
				write(' ');
			else
				first = false;
			write(elkObject);
		}
	}

	protected final void write(String string) {
		try {
			writer.append(string);
		} catch (IOException e) {
			throw new PrintingException(e.getMessage(), e.getCause());
		}
	}

	protected final <P extends ElkObject> void write(
			ElkPropertyRestriction<P> elkPropertyRestriction) {
		write(elkPropertyRestriction.getProperty());
	}

	protected final <P extends ElkObject, F extends ElkObject> void write(
			ElkPropertyRestrictionQualified<P, F> elkQualifiedPropertyRestriction) {
		write((ElkPropertyRestriction<P>) elkQualifiedPropertyRestriction);
		write(' ');
		write(elkQualifiedPropertyRestriction.getFiller());
	}

	protected final <P extends ElkObject> void write(
			ElkCardinalityRestriction<P> elkCardinalityRestriction) {
		write(elkCardinalityRestriction.getCardinality());
		write(' ');
		write((ElkPropertyRestriction<P>) elkCardinalityRestriction);
	}

	protected final <P extends ElkObject, F extends ElkObject> void write(
			ElkCardinalityRestrictionQualified<P, F> elkQualifiedCardinalityRestriction) {
		write((ElkCardinalityRestriction<P>) elkQualifiedCardinalityRestriction);
		write(' ');
		write(elkQualifiedCardinalityRestriction.getFiller());
	}

}