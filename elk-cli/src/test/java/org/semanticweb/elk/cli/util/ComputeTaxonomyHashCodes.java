/*
 * #%L
 * ELK Command Line Interface
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
/**
 * 
 */
package org.semanticweb.elk.cli.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;

import org.semanticweb.elk.cli.IOReasoner;
import org.semanticweb.elk.io.FileUtils;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkNamedIndividual;
import org.semanticweb.elk.owl.parsing.Owl2ParseException;
import org.semanticweb.elk.reasoner.InconsistentOntologyException;
import org.semanticweb.elk.reasoner.stages.TestStageExecutor;
import org.semanticweb.elk.reasoner.taxonomy.InstanceTaxonomy;
import org.semanticweb.elk.reasoner.taxonomy.Taxonomy;
import org.semanticweb.elk.reasoner.taxonomy.hashing.InstanceTaxonomyHasher;
import org.semanticweb.elk.reasoner.taxonomy.hashing.TaxonomyHasher;
/**
 * Computes correct class taxonomy hash codes for a set of ontologies
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * @author "Yevgeny Kazakov"
 */
public class ComputeTaxonomyHashCodes {

	static final String CLASSIFICATION_PATH = "../elk-reasoner/src/test/resources/classification_test_input";
	static final String REALIZATION_PATH = "../elk-reasoner/src/test/resources/realization_test_input";

	/**
	 * args[0]: path to the dir with source ontologies
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		generateHashCodes(CLASSIFICATION_PATH, new TestHasher() {

			@Override
			public int hash(IOReasoner reasoner) {
				Taxonomy<ElkClass> taxonomy = null;
				
				try {
					taxonomy = reasoner.getTaxonomy();
				} catch (InconsistentOntologyException e) {
					return 0;
				}
				
				return TaxonomyHasher.hash(taxonomy);
			}});
		
		generateHashCodes(REALIZATION_PATH, new TestHasher() {

			@Override
			public int hash(IOReasoner reasoner) {
				InstanceTaxonomy<ElkClass, ElkNamedIndividual> taxonomy = null;
				
				try {
					taxonomy = reasoner.getInstanceTaxonomy();
				} catch (InconsistentOntologyException e) {
					return 0;
				}
				
				return InstanceTaxonomyHasher.hash(taxonomy);
			}});		
	}
	
	static void generateHashCodes(String path, TestHasher hasher) throws IOException, Owl2ParseException {
		File srcDir = new File(path);
		// use just one worker to minimize the risk of errors:
		IOReasoner reasoner = new IOReasoner(new TestStageExecutor(),
				Executors.newCachedThreadPool(), 1);

		for (File ontFile : srcDir.listFiles(FileUtils
				.getExtBasedFilenameFilter("owl"))) {

			System.err.println(ontFile.getName());

			reasoner.loadOntologyFromFile(ontFile);

			int hash = hasher.hash(reasoner);
			// create the expected result file
			File out = new File(srcDir.getAbsolutePath() + "/"
					+ FileUtils.dropExtension(ontFile.getName())
					+ ".expected.hash");
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(out));

			writer.write(Integer.valueOf(hash).toString());
			writer.flush();
			writer.close();
		}

		reasoner.shutdown();		
	}
	
	interface TestHasher {
		int hash(IOReasoner reasoner);
	}
}