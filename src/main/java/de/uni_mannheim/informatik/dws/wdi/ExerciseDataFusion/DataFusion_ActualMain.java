package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.ActorsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.AssetsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DirectorEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.FoundingdateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.HqcityEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.IndustryEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.MarketvalueEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.NameEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.RevenueEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.RevenuesourceEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.WebsiteEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.ActorsFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.AssetsFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DirectorFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.FoundingdateFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.HqcityFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.IndustryFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.MarketvalueFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.RevenueFuserFavourSources;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.RevenuesourceFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.TitleFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.WebsiteFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.FusibleCompanyFactory;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Company;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.CompanyXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.CompanyXMLReader;
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

public class DataFusion_ActualMain {
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
		// Load the Data into FusibleDataSet
		logger.info("*\tLoading datasets\t*");
		FusibleDataSet<Company, Attribute> ds1 = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/input/fortune_500_updated_translated.xml"),
				"/companies/company",
				ds1);
		ds1.printDataSetDensityReport();

		FusibleDataSet<Company, Attribute> ds2 = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/input/top_2000_translated.xml"),
				"/companies/company",
				ds2);
		ds2.printDataSetDensityReport();

		FusibleDataSet<Company, Attribute> ds3 = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/input/us_companies_translated.xml"),
				"/companies/company",
				ds3);
		ds3.printDataSetDensityReport();

		// Maintain Provenance
		// Scores (e.g. from rating)
		ds1.setScore(4.0);
		ds2.setScore(2.0);
		ds3.setScore(3.0);

		// Date (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern("yyyy-MM-dd")
				.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				.toFormatter(Locale.ENGLISH);

		ds1.setDate(LocalDateTime.parse("2012-01-01", formatter));
		ds2.setDate(LocalDateTime.parse("2010-01-01", formatter));
		ds3.setDate(LocalDateTime.parse("2008-01-01", formatter));

		// // load correspondences
		logger.info("*\tLoading correspondences\t*");
		CorrespondenceSet<Company, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(
				new File(
						"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/correspondences/levenshtein_top_2000_2_us_companies_correspondences.csv"),
				ds3,
				ds2);
		correspondences.loadCorrespondences(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/correspondences/levenshtein_standard_blocking_fortune_500_2_us_companies_correspondences.csv"),
				ds3,
				ds1);

		// // write group size distribution
		correspondences.printGroupSizeDistribution();

		// // load the gold standard
		logger.info("*\tEvaluating results\t*");
		DataSet<Company, Attribute> gs = new FusibleHashedDataSet<>();
		new CompanyXMLReader().loadFromXML(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/goldstandard/actualgold.xml"),
				"/companies/company", gs);

		for (Company m : gs.get()) {
			logger.info(String.format("gs: %s", m.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<Company, Attribute> strategy = new DataFusionStrategy<>(new CompanyXMLReader());
		// write debug results to file
		strategy.activateDebugReport(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/output/debugResultsDatafusion.csv",
				-1,
				gs);

		// add attribute fusers
		strategy.addAttributeFuser(Company.NAME, new NameFuserShortestString(), new NameEvaluationRule());
		strategy.addAttributeFuser(Company.WEBSITE, new WebsiteFuserShortestString(), new WebsiteEvaluationRule());
		strategy.addAttributeFuser(Company.FOUNDINGDATE, new FoundingdateFuserShortestString(),
				new FoundingdateEvaluationRule());
		strategy.addAttributeFuser(Company.HQCITY, new HqcityFuserFavourSources(), new HqcityEvaluationRule());
		strategy.addAttributeFuser(Company.INDUSTRY, new IndustryFuserShortestString(), new IndustryEvaluationRule());
		strategy.addAttributeFuser(Company.MARKETVALUE, new MarketvalueFuserFavourSources(),
				new MarketvalueEvaluationRule());
		strategy.addAttributeFuser(Company.ASSETS, new AssetsFuserFavourSources(), new AssetsEvaluationRule());
		strategy.addAttributeFuser(Company.REVENUE, new RevenueFuserFavourSources(), new RevenueEvaluationRule());
		strategy.addAttributeFuser(Company.REVENUESOURCE, new RevenuesourceFuserLongestString(),
				new RevenuesourceEvaluationRule());

		// // create the fusion engine
		DataFusionEngine<Company, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);

		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/output/recordGroupConsistencies.csv"),
				correspondences,
				null);

		// run the fusion
		logger.info("*\tRunning data fusion\t*");
		FusibleDataSet<Company, Attribute> fusedDataSet = engine.run(correspondences,
				null);

		// write the result
		new CompanyXMLFormatter().writeXML(new File(
				"C:/Users/Marcus Kok/OneDrive/Y3S1/Web Data Integration/04_Data_Fusion/DF_exercise/data/output/fused.xml"),
				fusedDataSet);

		// evaluate
		DataFusionEvaluator<Company, Attribute> evaluator = new DataFusionEvaluator<>(strategy,
				new RecordGroupFactory<Company, Attribute>());

		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		logger.info(String.format("*\tAccuracy: %.2f", accuracy));
	}
}
