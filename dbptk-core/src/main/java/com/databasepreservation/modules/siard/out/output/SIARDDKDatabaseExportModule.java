package com.databasepreservation.modules.siard.out.output;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.databasepreservation.CustomLogger;
import com.databasepreservation.model.exception.ModuleException;
import com.databasepreservation.modules.siard.common.path.MetadataPathStrategy;
import com.databasepreservation.modules.siard.constants.SIARDDKConstants;
import com.databasepreservation.modules.siard.out.metadata.ContextDocumentationWriter;
import com.databasepreservation.modules.siard.out.metadata.FileIndexFileStrategy;
import com.databasepreservation.modules.siard.out.metadata.SIARDMarshaller;

/**
 * @author Andreas Kring <andreas@magenta.dk>
 *
 */
public class SIARDDKDatabaseExportModule extends SIARDExportDefault {

  private SIARDDKExportModule siarddkExportModule;
  private final CustomLogger logger = CustomLogger.getLogger(SIARDDKDatabaseExportModule.class);

  public SIARDDKDatabaseExportModule(SIARDDKExportModule siarddkExportModule) {
    super(siarddkExportModule.getContentExportStrategy(), siarddkExportModule.getMainContainer(), siarddkExportModule
      .getWriteStrategy(), siarddkExportModule.getMetadataExportStrategy());

    this.siarddkExportModule = siarddkExportModule;
  }

  @Override
  public void initDatabase() throws ModuleException {
    super.initDatabase();

    // Delete output folder if it already exists

    File outputFolder = siarddkExportModule.getMainContainer().getPath().toFile();
    if (outputFolder.isDirectory()) {
      try {
        FileUtils.deleteDirectory(outputFolder);

        // TO-DO: not logging ?

        logger.info("Deleted the already existing folder: " + outputFolder);
      } catch (IOException e) {
        throw new ModuleException("Error deleting existing directory", e);
      }
    }
  }

  @Override
  public void finishDatabase() throws ModuleException {
    super.finishDatabase();

    // Write ContextDocumentation to archive

    Map<String, String> exportModuleArgs = siarddkExportModule.getExportModuleArgs();
    FileIndexFileStrategy fileIndexFileStrategy = siarddkExportModule.getFileIndexFileStrategy();
    MetadataPathStrategy metadataPathStrategy = siarddkExportModule.getMetadataPathStrategy();
    SIARDMarshaller siardMarshaller = siarddkExportModule.getSiardMarshaller();

    if (exportModuleArgs.get(SIARDDKConstants.CONTEXT_DOCUMENTATION_FOLDER) != null) {

      ContextDocumentationWriter contextDocumentationWriter = new ContextDocumentationWriter(
        siarddkExportModule.getMainContainer(), siarddkExportModule.getWriteStrategy(), fileIndexFileStrategy,
        siarddkExportModule.getExportModuleArgs());

      contextDocumentationWriter.writeContextDocumentation();
    }

    // Create fileIndex.xml

    // TO-DO: refactor the stuff below into separate class (also to be used by
    // the MetadataExportStrategy)

    try {
      fileIndexFileStrategy.generateXML(null);
    } catch (ModuleException e) {
      throw new ModuleException("Error writing fileIndex.xml", e);
    }

    try {
      String path = metadataPathStrategy.getXmlFilePath(SIARDDKConstants.FILE_INDEX);
      OutputStream writer = fileIndexFileStrategy.getWriter(siarddkExportModule.getMainContainer(), path,
        siarddkExportModule.getWriteStrategy());

      siardMarshaller.marshal(SIARDDKConstants.JAXB_CONTEXT_FILEINDEX,
        metadataPathStrategy.getXsdResourcePath(SIARDDKConstants.FILE_INDEX),
        "http://www.sa.dk/xmlns/diark/1.0 ../Schemas/standard/fileIndex.xsd", writer,
        fileIndexFileStrategy.generateXML(null));

      writer.close();
    } catch (IOException e) {
      throw new ModuleException("Error writing fileIndex to the archive.", e);
    }

  }
}
