package be.ugent.mmlab.rml.main;

import be.ugent.mmlab.rml.core.RMLEngine;
import be.ugent.mmlab.rml.core.RMLMappingFactory;
import be.ugent.mmlab.rml.model.RMLMapping;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.antidot.semantic.rdf.rdb2rdf.r2rml.exception.InvalidR2RMLStructureException;
import net.antidot.semantic.rdf.rdb2rdf.r2rml.exception.InvalidR2RMLSyntaxException;
import net.antidot.semantic.rdf.rdb2rdf.r2rml.exception.R2RMLDataError;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;

/**
 *
 * @author mielvandersande, andimou
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String graphName = "";
            // create Options object
            Options options = new Options();            
            // add options
            options.addOption("sp", true, "source properties file");
            options.addOption("g", true, "Graph name");
            //should be new DefaultParser() but requires cli 1.3 instead of clli 1.2
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse( options, args);
            
            RMLMappingFactory mappingFactory = new RMLMappingFactory();
            RMLMapping mapping = mappingFactory.extractRMLMapping(args[0]);
            //RMLMapping mapping = RMLMappingFactory.extractRMLMapping(args[0]);
            RMLEngine engine = new RMLEngine();
            System.out.println("mapping document " + args[0]);
            engine.runRMLMapping(mapping, graphName, args[1], true);
            if(cmd.hasOption("g")) 
                    graphName = cmd.getOptionValue("g");           
            
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("RML Processor");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("Usage: mvn exec:java -Dexec.args=\"<mapping_file> <output_file> [-g <graph>]\"");
            System.out.println("");
            System.out.println("With");
            System.out.println("    <mapping_file> = The RML mapping document conform with the RML specification (http://semweb.mmlab.be/rml/spec.html)");
            System.out.println("    <output_file> = The file where the output RDF triples are stored; default in Turtle (http://www.w3.org/TR/turtle/) syntax.");
            System.out.println("    <graph> (optional) = The named graph in which the output RDF triples are stored.");
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------");
            //}
        } catch (IOException | InvalidR2RMLStructureException | InvalidR2RMLSyntaxException | R2RMLDataError | RepositoryException | RDFParseException | SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
