package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class FusibleCompanyFactory implements FusibleFactory<Company, Attribute> {

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
