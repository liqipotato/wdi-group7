package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class MarketvalueEvaluationRule extends EvaluationRule<Company, Attribute> {
	@Override
	public boolean isEqual(Company record1, Company record2, Attribute schemaElement) {
		if(record1.getMarketvalue()== null && record2.getMarketvalue()==null)
			return true;
		else if(record1.getMarketvalue()== null ^ record2.getMarketvalue()==null)
			return false;
		else 
			return record1.getMarketvalue().equals(record2.getMarketvalue());
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Company record1, Company record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}