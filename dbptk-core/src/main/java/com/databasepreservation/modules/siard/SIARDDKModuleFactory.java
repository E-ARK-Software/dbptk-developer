package com.databasepreservation.modules.siard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.databasepreservation.model.modules.DatabaseExportModule;
import com.databasepreservation.model.modules.DatabaseImportModule;
import com.databasepreservation.model.modules.DatabaseModuleFactory;
import com.databasepreservation.model.parameters.Parameter;
import com.databasepreservation.model.parameters.Parameters;
import com.databasepreservation.modules.siard.constants.SIARDDKConstants;
import com.databasepreservation.modules.siard.in.input.SIARDDKImportModule;
import com.databasepreservation.modules.siard.out.output.SIARDDKExportModule;

/**
 * @author Andreas Kring <andreas@magenta.dk>
 * @author Thomas Kristensen <tk@bithuset.dk>
 *
 */
public class SIARDDKModuleFactory implements DatabaseModuleFactory {

  private static final Parameter folder = new Parameter().shortName("f").longName("folder")
    .description(
      "Path to SIARDDK archive folder. Archive folder name must match the expression AVID.[A-ZÆØÅ]{2,4}.[1-9][0-9]*.[1-9][0-9]")
    .hasArgument(true).setOptionalArgument(false).required(true);

  private static final Parameter archiveIndex = new Parameter().shortName("ai").longName("archiveIndex")
    .description("Path to archiveIndex.xml input file").hasArgument(true).setOptionalArgument(false).required(false);

  private static final Parameter contextDocumentationIndex = new Parameter().shortName("ci")
    .longName("contextDocumentationIndex").description("Path to contextDocumentationIndex.xml input file")
    .hasArgument(true).setOptionalArgument(false).required(false);

  private static final Parameter contextDocmentationFolder = new Parameter().shortName("cf")
    .longName(SIARDDKConstants.CONTEXT_DOCUMENTATION_FOLDER)
    .description("Path to contextDocumentation folder which should contain the context documentation for the archive")
    .hasArgument(true).setOptionalArgument(false).required(false);

  public static final Parameter PARAM_IMPORT_AS_SCHEMA = new Parameter().shortName("ias").longName("as-schema")
    .description("Name of the database schema to use when importing the SIARDDK archive").required(true);

  public static final Parameter PARAM_IMPORT_FOLDER = new Parameter().shortName("f").longName("folder")
    .description(
      "Path to (the first) SIARDDK archive folder. Archive folder name must match the expression AVID.[A-ZÆØÅ]{2,4}.[1-9][0-9]*.1 .Any subsequent folders in the identified serie will also be processed (eg. with suffixes .2 .3 etc)")
    .hasArgument(true).setOptionalArgument(false).required(true);

  // This is not used now, but will be used later
  // private static final Parameter clobType = new
  // Parameter().shortName("ct").longName("clobtype")
  // .description("Specify the type for
  // CLOBs").hasArgument(true).setOptionalArgument(false).required(false)
  // .valueIfNotSet(SIARDDKConstants.DEFAULT_CLOB_TYPE);

  // This is not used now, but will be used later
  // private static final Parameter clobLength = new
  // Parameter().shortName("cl").longName("cloblength")
  // .description("The threshold length of CLOBs before converting to
  // tiff").hasArgument(true)
  // .setOptionalArgument(false).required(false).valueIfNotSet(SIARDDKConstants.DEFAULT_MAX_CLOB_LENGTH);

  @Override
  public boolean producesImportModules() {
    return true;
  }

  @Override
  public boolean producesExportModules() {
    return true;
  }

  @Override
  public String getModuleName() {
    return "siard-dk";
  }

  @Override
  public Map<String, Parameter> getAllParameters() {
    HashMap<String, Parameter> parameterMap = new HashMap<String, Parameter>();

    parameterMap.put(folder.longName(), folder);
    parameterMap.put(archiveIndex.longName(), archiveIndex);
    parameterMap.put(contextDocumentationIndex.longName(), contextDocumentationIndex);
    parameterMap.put(contextDocmentationFolder.longName(), contextDocmentationFolder);
    parameterMap.put(PARAM_IMPORT_AS_SCHEMA.longName(), PARAM_IMPORT_AS_SCHEMA);
    parameterMap.put(PARAM_IMPORT_FOLDER.longName(), PARAM_IMPORT_FOLDER);
    // to be used later...
    // parameterMap.put(clobType.longName(), clobType);
    // parameterMap.put(clobLength.longName(), clobLength);

    return parameterMap;
  }

  @Override
  public Parameters getImportModuleParameters() throws OperationNotSupportedException {
    return new Parameters(Arrays.asList(PARAM_IMPORT_FOLDER, PARAM_IMPORT_AS_SCHEMA), null);
  }

  @Override
  public Parameters getExportModuleParameters() throws OperationNotSupportedException {
    // return new Parameters(Arrays.asList(folder, archiveIndex,
    // contextDocumentationIndex, contextDocmentationFolder,
    // clobType, clobLength), null);

    return new Parameters(Arrays.asList(folder, archiveIndex, contextDocumentationIndex, contextDocmentationFolder),
      null);

  }

  @Override
  public DatabaseImportModule buildImportModule(Map<Parameter, String> parameters) {
    return new SIARDDKImportModule(parameters).getDatabaseImportModule();
  }

  @Override
  public DatabaseExportModule buildExportModule(Map<Parameter, String> parameters)
    throws OperationNotSupportedException {

    // Get the values passed to the parameter flags from the command line

    String pFolder = parameters.get(folder);
    String pArchiveIndex = parameters.get(archiveIndex);
    String pContextDocumentationIndex = parameters.get(contextDocumentationIndex);
    String pContextDocumentationFolder = parameters.get(contextDocmentationFolder);

    // to be used later...
    // String pClobType = parameters.get(clobType);
    // String pClobLength = parameters.get(clobLength);

    Map<String, String> exportModuleArgs = new HashMap<String, String>();
    exportModuleArgs.put(folder.longName(), pFolder);
    exportModuleArgs.put(archiveIndex.longName(), pArchiveIndex);
    exportModuleArgs.put(contextDocumentationIndex.longName(), pContextDocumentationIndex);
    exportModuleArgs.put(contextDocmentationFolder.longName(), pContextDocumentationFolder);

    // to be used later...
    // exportModuleArgs.put(clobType.longName(), pClobType);
    // exportModuleArgs.put(clobLength.longName(), pClobLength);

    return new SIARDDKExportModule(exportModuleArgs).getDatabaseExportModule();
  }
}
