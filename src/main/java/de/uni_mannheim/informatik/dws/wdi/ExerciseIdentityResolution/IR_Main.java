package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.MovieBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.CompanyNameComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.CompanyNameComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.CompanyNameComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.MovieTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.CompanyXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.MaximumBipartiteMatchingAlgorithm;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_Main {
	/*
	 * Logging Options:
	 * default: level INFO - console
	 * trace: level TRACE - console
	 * infoFile: level INFO - console/file
	 * traceFile: level TRACE - console/file
	 * 
	 * To set the log level to trace and write the log to winter.log and console,
	 * activate the "traceFile" logger as follows:
	 * private static final Logger logger =
	 * WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("trace");

	public static void main(String[] args) throws Exception {
		// loading data
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Company, Attribute> usCompanies = new HashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("data/input/us_companies_translated.xml"), "/companies/company",
				usCompanies);
		// HashedDataSet<Company, Attribute> dataFortune500 = new HashedDataSet<>();
		// new CompanyXMLReader().loadFromXML(new File("data/input/fortune_500_translated.xml"), "/companies/company",
		// 		dataFortune500);
		HashedDataSet<Company, Attribute> dataTop2000 = new HashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("data/input/top_2000_translated.xml"), "/companies/company",
				dataTop2000);

		// // load the gold standard
		logger.info("*\tLoading gold standard\t*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		// gsTest.loadFromCSVFile(new File(
		// "data/goldstandard/fortune_500_2_us_companies.csv")); // fortune 500 against us companies
		gsTest.loadFromCSVFile(new File(
		"data/goldstandard/top_2000_2_us_companies.csv")); // top 2000 against us companies

		// create a matching rule
		LinearCombinationMatchingRule<Company, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.7);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTest);

		// add comparators
		// matchingRule.addComparator(new CompanyNameComparatorEqual(), 1.0);
		// matchingRule.addComparator(new CompanyNameComparatorJaccard(), 1.0);
		matchingRule.addComparator(new CompanyNameComparatorLevenshtein(), 1.0);

		// create a blocker (blocking strategy)
		// StandardRecordBlocker<Movie, Attribute> blocker = new
		// StandardRecordBlocker<Movie, Attribute>(
		// new MovieBlockingKeyByTitleGenerator());
		NoBlocker<Company, Attribute> blocker = new NoBlocker<>();
		// SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new
		// SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByTitleGenerator(), 1);
		// blocker.setMeasureBlockSizes(true);
		// Write debug results to file:
		// blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Company, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		logger.info("*\tRunning identity resolution\t*");
		// Processable<Correspondence<Company, Attribute>> correspondences = engine.runIdentityResolution(
		// 		usCompanies, dataFortune500, null, matchingRule,
		// 		blocker); // fortune 500 against us companies
		Processable<Correspondence<Company, Attribute>> correspondences = engine.runIdentityResolution(
				usCompanies, dataTop2000, null, matchingRule,
				blocker); // fortune 500 against us companies

		// Create a top-1 global matching
		correspondences = engine.getTopKInstanceCorrespondences(correspondences, 1, 0.0);

		// Alternative: Create a maximum-weight, bipartite matching
		// MaximumBipartiteMatchingAlgorithm<Movie,Attribute> maxWeight = new
		// MaximumBipartiteMatchingAlgorithm<>(correspondences);
		// maxWeight.run();
		// correspondences = maxWeight.getResult();

		// write the correspondences to the output file
		// new CSVCorrespondenceFormatter().writeCSV(new File("data/output/fortune_500_2_us_companies_correspondences.csv"), correspondences); // fortune 500 against us companies
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/top_2000_2_us_companies_correspondences.csv"), correspondences); // top 2000 against us companies
	
		// logger.info("*\tEvaluating result\t*");
		// // evaluate your result
		MatchingEvaluator<Company, Attribute> evaluator = new MatchingEvaluator<Company,
		Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// // print the evaluation result
		logger.info("Forbes Top 2000 Companies <-> US Companies");
		logger.info(String.format(
		"Precision: %.4f", perfTest.getPrecision()));
		logger.info(String.format(
		"Recall: %.4f", perfTest.getRecall()));
		logger.info(String.format(
		"F1: %.4f", perfTest.getF1()));
	}
}
