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

import java.io.Serializable;
//import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} which represents an company
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Company extends AbstractRecord<Attribute> implements Serializable {

	/*
	 * example entry <actor> <name>Janet Gaynor</name>
	 * <birthday>1906-01-01</birthday> <birthplace>Pennsylvania</birthplace>
	 * </actor>
	 */

	private static final long serialVersionUID = 1L;
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
		super(identifier, provenance);
	}

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

	public String getFoundingDate() {
		return foundingdate;
	}

	public void setFoundingDate(String foundingdate) {
		this.foundingdate = foundingdate;
	}

	public String getHqCity() {
		return hqcity;
	}

	public void setHqCity(String hqcity) {
		this.hqcity = hqcity;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public double getMarketValue() {
		return marketvalue;
	}

	public void setMarketValue(double marketvalue) {
		this.marketvalue = marketvalue;
	}

	public double getAssets() {
		return assets;
	}

	public void setAssets(double assets) {
		this.assets = assets;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public String getRevenueSource() {
		return revenuesource;
	}

	public void setRevenueSource(String revenuesource) {
		this.revenuesource = revenuesource;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute WEBSITE = new Attribute("Website");
	public static final Attribute FOUNDINGDATE = new Attribute("Foundingdate");
	public static final Attribute HQCITY = new Attribute("Hqcity");
	public static final Attribute INDUSTRY = new Attribute("Industry");
	public static final Attribute MARKETVALUE = new Attribute("Marketvalue");
	public static final Attribute ASSETS = new Attribute("Assets");
	public static final Attribute REVENUE = new Attribute("Revenue");
	public static final Attribute REVENUESOURCE = new Attribute("Revenuesource");

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if (attribute == NAME)
			return name != null;
		else if (attribute == WEBSITE)
			return website != null;
		else if (attribute == FOUNDINGDATE)
			return foundingdate != null;
		else if (attribute == HQCITY)
			return hqcity != null;
		else if (attribute == INDUSTRY)
			return industry != null;
		else if (attribute == MARKETVALUE)
			return Double.toString(marketvalue) != null;
		else if (attribute == ASSETS)
			return Double.toString(assets) != null;
		else if (attribute == REVENUE)
			return Double.toString(revenue) != null;
		else if (attribute == REVENUESOURCE)
			return revenuesource != null;
		return false;
	}

	@Override
	public String toString() {
		return String.format("[Company: %s]", getName());
	}
}
