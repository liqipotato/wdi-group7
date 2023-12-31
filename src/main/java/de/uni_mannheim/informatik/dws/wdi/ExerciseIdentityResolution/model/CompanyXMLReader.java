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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.time.format.DateTimeFormatterBuilder;
// import java.time.temporal.ChronoField;
// import java.util.List;
// import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Movie}s.
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

	}

	@Override
	public Company createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Company company = new Company(id, provenanceInfo);

		// fill the attributes
		company.setName(getValueFromChildElement(node, "name"));
		company.setWebsite(getValueFromChildElement(node, "website"));
		company.setDate(getValueFromChildElement(node, "foundingdate"));
		company.setHqcity(getValueFromChildElement(node, "hqcity"));
		company.setIndustry(getValueFromChildElement(node, "industry"));

		try {
			String marketValue = getValueFromChildElement(node, "marketvalue");
			if (marketValue != null && !marketValue.isEmpty()) {
				company.setMarketvalue(Double.parseDouble(marketValue));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String assets = getValueFromChildElement(node, "assets");
			if (assets != null && !assets.isEmpty()) {
				company.setAssets(Double.parseDouble(assets));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String revenue = getValueFromChildElement(node, "revenue");
			if (revenue != null && !revenue.isEmpty()) {
				company.setRevenue(Double.parseDouble(revenue));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		company.setRevenuesource(getValueFromChildElement(node, "revenuesource"));
		
		// convert the date string into a DateTime object
		// try {
		// String date = getValueFromChildElement(node, "date");
		// if (date != null && !date.isEmpty()) {
		// DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		// .appendPattern("yyyy-MM-dd")
		// .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		// .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		// .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		// .toFormatter(Locale.ENGLISH);
		// LocalDateTime dt = LocalDateTime.parse(date, formatter);
		// movie.setDate(dt);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// load the list of actors
		// List<Actor> actors = getObjectListFromChildElement(node, "actors",
		// "actor", new ActorXMLReader(), provenanceInfo);
		// movie.setActors(actors);

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
