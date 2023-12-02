package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Company;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class AssetsFuserFavourSources extends AttributeValueFuser<String, Company, Attribute> {
	public AssetsFuserFavourSources() {
		super(new FavourSources<String, Company, Attribute>());
	}

	@Override
	public void fuse(RecordGroup<Company, Attribute> group, Company fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

		// get the fused value
		FusedValue<String, Company, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

		// set the value for the fused record
		fusedRecord.setAssets(Double.parseDouble(fused.getValue()));

		// add provenance info
		fusedRecord.setAttributeProvenance(Company.ASSETS, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Company record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Company.ASSETS);
	}

	@Override
	public String getValue(Company record, Correspondence<Attribute, Matchable> correspondence) {
		return Double.toString(record.getAssets());
	}

}
