
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Company;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class FoundingdateEvaluationRule extends EvaluationRule<Company, Attribute> {

	@Override
	public boolean isEqual(Company record1, Company record2, Attribute schemaElement) {
		if (record1.getFoundingDate() == null && record2.getFoundingDate() == null)
			return true;
		else if (record1.getFoundingDate() == null ^ record2.getFoundingDate() == null)
			return false;
		else
			return record1.getFoundingDate().equals(record2.getFoundingDate());
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