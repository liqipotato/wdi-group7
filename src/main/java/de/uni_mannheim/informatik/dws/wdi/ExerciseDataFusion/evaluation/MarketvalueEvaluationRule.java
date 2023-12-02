package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Company;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class MarketvalueEvaluationRule extends EvaluationRule<Company, Attribute> {
	@Override
	public boolean isEqual(Company record1, Company record2, Attribute schemaElement) {
		if (Double.toString(record1.getMarketValue()) == null && Double.toString(record2.getMarketValue()) == null)
			return true;
		else if (Double.toString(record1.getMarketValue()) == null ^ Double.toString(record2.getMarketValue()) == null)
			return false;
		else
			return Double.toString(record1.getMarketValue()).equals(Double.toString(record2.getMarketValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.
	 * Object, java.lang.Object,
	 * de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Company record1, Company record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute) null);
	}

}