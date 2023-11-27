package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import java.util.Arrays;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Movie;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.BlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class CompanyBlockingKeyByHqCityGenerator extends RecordBlockingKeyGenerator <Company, Attribute> {
    private static final long serialVersionUID = 1L;

    @Override
	public void generateBlockingKeys(Company company, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Company>> resultCollector) {
        
        String hqCity = company.getHqcity();
        String[] tokens = new String[0]; 
        
        if (hqCity != null) {
            tokens = hqCity.split(" ");
        }
        
        String blockingKeyValue = "";
        for (int i = 0; i <= 2 && i < tokens.length; i++) {
            if (tokens[i] != null) {
                blockingKeyValue += tokens[i].substring(0, Math.min(2, tokens[i].length())).toUpperCase();
            }
        }
        
        resultCollector.next(new Pair<>(blockingKeyValue, company));
	}

}