/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Company}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CompanyXMLReader extends XMLMatchableReader<Company, Attribute> implements
		FusibleFactory<Company, Attribute> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(
	 * de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	protected void initialiseDataset(DataSet<Company, Attribute> dataset) {
		super.initialiseDataset(dataset);

		// the schema is defined in the Movie class and not interpreted from the file,
		// so we have to set the attributes manually
		dataset.addAttribute(Company.NAME);
		dataset.addAttribute(Company.WEBSITE);
		dataset.addAttribute(Company.FOUNDINGDATE);
		dataset.addAttribute(Company.HQCITY);
		dataset.addAttribute(Company.INDUSTRY);
		dataset.addAttribute(Company.MARKETVALUE);
		dataset.addAttribute(Company.ASSETS);
		dataset.addAttribute(Company.REVENUE);
		dataset.addAttribute(Company.REVENUESOURCE);

	}

	@Override
	public Company createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Company company = new Company(id, provenanceInfo);

		// fill the attributes
		company.setName(getValueFromChildElement(node, "name"));
		company.setWebsite(getValueFromChildElement(node, "website"));
		company.setFoundingDate(getValueFromChildElement(node, "foundingdate"));
		company.setHqCity(getValueFromChildElement(node, "hqcity"));
		company.setIndustry(getValueFromChildElement(node, "industry"));

		String marketvalue = getValueFromChildElement(node, "marketvalue");
		if (marketvalue != null) {
			double mv = Double.parseDouble(marketvalue);
			company.setMarketValue(mv);
		}

		String assets = getValueFromChildElement(node, "assets");
		if (assets != null) {
			double a = Double.parseDouble(assets);
			company.setMarketValue(a);
		}

		String revenue = getValueFromChildElement(node, "revenue");
		if (revenue != null) {
			double r = Double.parseDouble(revenue);
			company.setMarketValue(r);
		}

		company.setRevenueSource(getValueFromChildElement(node, "revenuesource"));

		return company;
	}

	@Override
	public Company createInstanceForFusion(RecordGroup<Company, Attribute> cluster) {

		List<String> ids = new LinkedList<>();

		for (Company c : cluster.getRecords()) {
			ids.add(c.getIdentifier());
		}

		Collections.sort(ids);

		String mergedId = StringUtils.join(ids, '+');

		return new Company(mergedId, "fused");
	}

}
