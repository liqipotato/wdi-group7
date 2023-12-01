package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.NameEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.WebsiteEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.FoundingdateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.HqcityEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.IndustryEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.MarketvalueEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.AssetsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.RevenueEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.evaluation.RevenuesourceEvaluationRule;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.NameFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.NameFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.WebsiteFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.FoundingdateFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.HqcityFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.IndustryFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.IndustryFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.MarketvalueFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.AssetsFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.RevenueFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.fusers.RevenuesourceFuserLongestString;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.FusibleCompanyFactory;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Company;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.CompanyXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.CompanyXMLReader;

import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;

public class DataFusion_Main 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");
	
	public static void main( String[] args ) throws Exception
    {
		// Load the Data into FusibleDataSet
		logger.info("*\tLoading datasets\t*");

		FusibleDataSet<Company, Attribute> ds1 = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("data/input/us_companies_translated.xml"), "/companies/company", ds1);
		ds1.printDataSetDensityReport();
		
        FusibleDataSet<Company, Attribute> ds2 = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("data/input/fortune_500_updated_translated.xml"), "/companies/company", ds2);
		ds2.printDataSetDensityReport();

		FusibleDataSet<Company, Attribute> ds3 = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("data/input/top_2000_translated.xml"), "/companies/company", ds3);
		ds3.printDataSetDensityReport();

		// Maintain Provenance
		// Scores (e.g. from rating)
		ds1.setScore(1.0);
		ds2.setScore(2.0);
		ds3.setScore(3.0);

		// Date (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		        .appendPattern("yyyy-MM-dd")
		        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		        .toFormatter(Locale.ENGLISH);
		
        ds1.setDate(LocalDateTime.parse("2016-09-16", formatter));
		ds2.setDate(LocalDateTime.parse("2023-06-15", formatter));
		ds3.setDate(LocalDateTime.parse("2019-01-18", formatter));

		// load correspondences
		logger.info("*\tLoading correspondences\t*");
		CorrespondenceSet<Company, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/correspondences/levenshtein_standard_blocking_fortune_500_2_us_companies_correspondences.csv"),ds1, ds2);
		correspondences.loadCorrespondences(new File("data/correspondences/levenshtein_top_2000_2_us_companies_correspondences.csv"),ds1, ds3);

		// write group size distribution -- can go fix IR
		correspondences.printGroupSizeDistribution();

		// load the gold standard
		logger.info("*\tEvaluating results\t*");
		DataSet<Company, Attribute> gs = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File("data/goldstandard/actualgold.xml"), "/movies/movie", gs);

		for(Company c : gs.get()) {
			logger.info(String.format("gs: %s", c.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<Company, Attribute> strategy = new DataFusionStrategy<>(new CompanyXMLReader());
		// write debug results to file
		strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);
		
		// add attribute s
		// strategy.addAttributeFuser(Movie.TITLE, new TitleFuserShortestString(),new TitleEvaluationRule());
		// strategy.addAttributeFuser(Movie.DIRECTOR,new DirectorFuserLongestString(), new DirectorEvaluationRule());
		// strategy.addAttributeFuser(Movie.DATE, new DateFuserFavourSource(),new DateEvaluationRule());
		// strategy.addAttributeFuser(Movie.ACTORS,new ActorsFuserUnion(),new ActorsEvaluationRule());
		strategy.addAttributeFuser(Company.NAME, new NameFuserLongestString(), new NameEvaluationRule());
		strategy.addAttributeFuser(Company.WEBSITE, new WebsiteFuserShortestString(), new WebsiteEvaluationRule());
		strategy.addAttributeFuser(Company.FOUNDING_DATE, new FoundingdateFuserShortestString(), new FoundingdateEvaluationRule());
		strategy.addAttributeFuser(Company.HQ_CITY, new HqcityFuserFavourSources(), new HqcityEvaluationRule());
		strategy.addAttributeFuser(Company.INDUSTRY, new IndustryFuserLongestString(), new IndustryEvaluationRule());
		strategy.addAttributeFuser(Company.MARKET_VALUE, new MarketvalueFuserFavourSources(), new MarketvalueEvaluationRule());
		strategy.addAttributeFuser(Company.ASSETS, new AssetsFuserFavourSources(), new AssetsEvaluationRule());
		strategy.addAttributeFuser(Company.REVENUE, new RevenueFuserFavourSources(), new RevenueEvaluationRule());
		strategy.addAttributeFuser(Company.REVENUE_SOURCE, new RevenueFuserFavourSources(), new RevenuesourceEvaluationRule());

		// create the fusion engine
		DataFusionEngine<Company, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);
		
		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

		// run the fusion
		logger.info("*\tRunning data fusion\t*");
		FusibleDataSet<Company, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		new CompanyXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

		// evaluate
		DataFusionEvaluator<Company, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Company, Attribute>());
		
		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		logger.info(String.format("*\tAccuracy: %.2f", accuracy));
    }
}
