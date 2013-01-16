/**
 * 
 */
package org.semanticweb.elk.benchmark;
/*
 * #%L
 * ELK Benchmarking Package
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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.semanticweb.elk.util.logging.ElkTimer;

/**
 * The only reason this class exists is because sometimes it's convenient to
 * finish w/ one task before preparing the next one.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class TaskCollectionRunner {

	private static final Logger LOGGER_ = Logger.getLogger(TaskCollectionRunner.class);	
	
	private final TaskRunner runner_;
	
	protected TaskCollectionRunner(int warmups, int runs) {
		runner_ = new TaskRunner(warmups, runs);
	}

	public void run(TaskCollection collection) throws TaskException {
		for (Task nextTask : collection.getTasks()) {
			runner_.run(nextTask);
		}
		
		for (Task nextTask : collection.getTasks()) {
			ElkTimer.getNamedTimer(nextTask.getName()).log(LOGGER_, Level.INFO);
		}
		
		collection.dispose();
	}
}
