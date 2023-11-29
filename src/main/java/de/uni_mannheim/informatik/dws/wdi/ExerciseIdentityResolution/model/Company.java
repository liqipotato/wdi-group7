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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
// import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} representing a company.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Company extends AbstractRecord<Attribute> implements Serializable {

	/*
	 * example entry <movie> <id>academy_awards_2</id> <title>True Grit</title>
	 * <director> <name>Joel Coen and Ethan Coen</name> </director> <actors>
	 * <actor> <name>Jeff Bridges</name> </actor> <actor> <name>Hailee
	 * Steinfeld</name> </actor> </actors> <date>2010-01-01</date> </movie>
	 */

	private static final long serialVersionUID = 1L;

	protected String id;
	// protected String provenance;
	private String name;
	private String website;
	private String foundingdate;
	private String hqcity;
	private String industry;
	private double marketvalue;
	private double assets;
	private double revenue;
	private String revenuesource;

	public Company(String identifier, String provenance) {
		// id = identifier;
		// this.provenance = provenance;
		// // actors = new LinkedList<>();
		super(identifier, provenance);
	}

	@Override
	public String getIdentifier() {
		return id;
	}

	// @Override
	// public String getProvenance() {
	// 	return provenance;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFoundingdate() {
		return foundingdate;
	}

	public void setDate(String foundingdate) {
		this.foundingdate = foundingdate;
	}

	public String getHqcity() {
		return hqcity;
	}

	public void setHqcity(String hqcity) {
		this.hqcity = hqcity;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Double getMarketvalue() {
		return marketvalue;
	}

	public void setMarketvalue(double marketvalue) {
		this.marketvalue = marketvalue;
	}

	public Double getAssets() {
		return assets;
	}

	public void setAssets(double assets) {
		this.assets = assets;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public String getRevenuesource() {
		return revenuesource;
	}

	public void setRevenuesource(String revenuesource) {
		this.revenuesource = revenuesource;
	}

	@Override
	public String toString() {
		return String.format("[Company %s: %s]", getIdentifier(), getName().toString());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Company) {
			return this.getIdentifier().equals(((Company) obj).getIdentifier());
		} else
			return false;
	}

	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}

	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute WEBSITE = new Attribute("Website");
	public static final Attribute FOUNDING_DATE = new Attribute("Founding date");
	public static final Attribute HQ_CITY = new Attribute("HQ City");
	public static final Attribute INDUSTRY = new Attribute("Industry");
	public static final Attribute MARKET_VALUE = new Attribute("Market value");
	public static final Attribute ASSETS = new Attribute("Assets");
	public static final Attribute REVENUE = new Attribute("Revenue");
	public static final Attribute REVENUE_SOURCE = new Attribute("Revenue source");
	
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==NAME)
		return getName() != null && !getName().isEmpty();
		else if(attribute==WEBSITE)
			return getWebsite() != null && !getWebsite().isEmpty();
		else if(attribute==FOUNDING_DATE)
			return getFoundingdate() != null && !getFoundingdate().isEmpty();
		else if(attribute==HQ_CITY)
			return getHqcity() != null && !getHqcity().isEmpty();
		else if(attribute==INDUSTRY)
			return getIndustry() != null && !getIndustry().isEmpty();
		else if(attribute==MARKET_VALUE)
			return getMarketvalue() != null;
		else if(attribute==ASSETS)
			return getAssets() != null;
		else if(attribute==REVENUE)
			return getRevenue() != null;
		else if(attribute==REVENUE_SOURCE)
			return getRevenuesource() != null && !getRevenuesource().isEmpty();
		else
			return false;
	}


}
