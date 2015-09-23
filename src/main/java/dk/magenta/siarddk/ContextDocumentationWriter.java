package dk.magenta.siarddk;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import com.databasepreservation.model.exception.ModuleException;
import com.databasepreservation.modules.siard.common.SIARDArchiveContainer;
import com.databasepreservation.modules.siard.out.write.WriteStrategy;

public class ContextDocumentationWriter {

  private Map<String, String> exportModuleArgs;
  private SIARDArchiveContainer mainContainer;
  private FileIndexFileStrategy fileIndexFileStrategy;
  private WriteStrategy writeStrategy;

  private Path mainContainerPath;

  public ContextDocumentationWriter(SIARDArchiveContainer mainContainer, WriteStrategy writeStrategy,
    FileIndexFileStrategy fileIndexFileStrategy, Map<String, String> exportModuleArgs) {

    this.mainContainer = mainContainer;
    this.writeStrategy = writeStrategy;
    this.fileIndexFileStrategy = fileIndexFileStrategy;
    this.exportModuleArgs = exportModuleArgs;
  }

  public void writeContextDocumentation() throws ModuleException {
    // getWriter
    // Write stuff
    // addFile

    String pathStr = exportModuleArgs.get(Constants.CONTEXT_DOCUMENTATION_FOLDER);
    File[] files = new File(pathStr).listFiles();

    mainContainerPath = mainContainer.getPath();
    System.out.println(mainContainer.getPath());

    writeFile(files);

    // try {
    //
    // // Loop over files...
    //
    // // Path + noget mere
    // OutputStream writer = fileIndexFileStrategy.getWriter(mainContainer,
    // Constants.CONTEXT_DOCUMENTATION_PATH,
    // writeStrategy);
    //
    // writer.close();
    // } catch (IOException e) {
    // throw new
    // ModuleException("Error writing the context documentation to the archive",
    // e);
    // }

  }

  /**
   * 
   * @param files
   *          List context documentation files to write to the archive
   * @precondition files must only contain files or folder - not symbolic links
   *               etc.
   */
  private void writeFile(File[] files) throws ModuleException {
    for (File file : files) {
      // System.out.println(file.getAbsoluteFile());
      // System.out.println(file.getName());
      String name = file.getName();
      Path path = mainContainerPath.resolve(Constants.CONTEXT_DOCUMENTATION_RELATIVE_PATH);
      path = path.resolve(name);
      System.out.println(path);
      System.out.println("---------");
      if (file.isFile()) {

        // Get a hold of the path tree

        // try {
        //
        // InputStream fis = new FileInputStream(file);
        // OutputStream fos = fileIndexFileStrategy.getWriter(mainContainer,
        // path, writeStrategy);
        // fis.close();
        //
        // } catch (FileNotFoundException e) {
        // throw new ModuleException("File not found: " + file.toString(), e);
        // } catch (IOException e) {
        // throw new ModuleException("There was a problem closing the file " +
        // file.toString(), e);
        // }
      } else {
        // Create appropriate folder

        writeFile(file.listFiles());
      }
    }
  }

}
