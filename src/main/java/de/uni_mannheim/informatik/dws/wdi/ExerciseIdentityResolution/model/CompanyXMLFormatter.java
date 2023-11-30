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
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.eg.Actor;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class CompanyXMLFormatter extends XMLFormatter<Company> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("companies");
	}

	@Override
	public Element createElementFromRecord(Company record, Document doc) {
		Element company = doc.createElement("company");

		company.appendChild(createTextElement("id", record.getIdentifier(), doc));

		company.appendChild(createTextElement("name",
				record.getName(),
				doc));
		company.appendChild(createTextElement("website",
				record.getWebsite(),
				doc));
		company.appendChild(createTextElement("founding_date", record
				.getFoundingdate(),
				doc));
		company.appendChild(createTextElement("hq_city", record
				.getHqcity(),
				doc));
		company.appendChild(createTextElement("industry", record
				.getIndustry(),
				doc));
		company.appendChild(createTextElement("market_value", record
				.getMarketvalue().toString(),
				doc));
		company.appendChild(createTextElement("assets", record
				.getAssets().toString(),
				doc));
		company.appendChild(createTextElement("revenue", record
				.getRevenue().toString(),
				doc));
		company.appendChild(createTextElement("revenue_source", record
				.getRevenuesource(),
				doc));

		return company;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

}
