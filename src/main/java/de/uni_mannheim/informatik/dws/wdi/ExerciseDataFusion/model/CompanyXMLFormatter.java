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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CompanyXMLFormatter extends XMLFormatter<Company> {

	ActorXMLFormatter actorFormatter = new ActorXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("companies");
	}

	@Override
	public Element createElementFromRecord(Company record, Document doc) {
		Element company = doc.createElement("company");

		company.appendChild(createTextElement("id", record.getIdentifier(), doc));

		company.appendChild(createTextElementWithProvenance("name",
				record.getName(),
				record.getMergedAttributeProvenance(Company.NAME), doc));
		company.appendChild(createTextElementWithProvenance("website",
				record.getWebsite(),
				record.getMergedAttributeProvenance(Company.WEBSITE), doc));
		company.appendChild(createTextElementWithProvenance("foundingdate",
				record.getFoundingDate(),
				record.getMergedAttributeProvenance(Company.FOUNDINGDATE), doc));
		company.appendChild(createTextElementWithProvenance("hqcity",
				record.getHqCity(),
				record.getMergedAttributeProvenance(Company.HQCITY), doc));
		company.appendChild(createTextElementWithProvenance("industry",
				record.getIndustry(),
				record.getMergedAttributeProvenance(Company.INDUSTRY), doc));
		company.appendChild(createTextElementWithProvenance("marketvalue",
				Double.toString(record.getMarketValue()), record
						.getMergedAttributeProvenance(Company.MARKETVALUE),
				doc));
		company.appendChild(createTextElementWithProvenance("assets",
				Double.toString(record.getAssets()), record
						.getMergedAttributeProvenance(Company.ASSETS),
				doc));
		company.appendChild(createTextElementWithProvenance("revenue",
				Double.toString(record.getRevenue()), record
						.getMergedAttributeProvenance(Company.REVENUE),
				doc));
		company.appendChild(createTextElementWithProvenance("revenuesource",
				record.getRevenueSource(),
				record.getMergedAttributeProvenance(Company.REVENUESOURCE), doc));

		return company;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

}
