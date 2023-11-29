package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
// import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.ShortestString;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;

public class RevenuesourceFuserLongestString extends
		AttributeValueFuser<String, Company, Attribute> {

	public RevenuesourceFuserLongestString() {
		super(new LongestString<Company, Attribute>());
	}

	@Override
	public void fuse(RecordGroup<Company, Attribute> group, Company fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

		// get the fused value
		FusedValue<String, Company, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

		// set the value for the fused record
		fusedRecord.setRevenuesource(fused.getValue());

		// add provenance info
		fusedRecord.setAttributeProvenance(Company.REVENUE_SOURCE, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Company record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Company.REVENUE_SOURCE);
	}

	@Override
	public String getValue(Company record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getRevenuesource();
	}

}
