package com.sumit.ormliteexample1.db.dbutil;

/**
 * Created by sumit on 5/4/2016.
 */


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.sumit.ormliteexample1.db.dbmodel.Person;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * OrmliteDatabaseConfigUtil is a separate program from the actual android app,
 * that is used to generate the database structure configuration before runtime.
 * It uses the models provided via a list of class objects,
 * and also the annotations (e.g. @DatabaseField) on the models to generate the configuration accordingly.
 */
public class OrmliteDatabaseConfigUtil extends OrmLiteConfigUtil {

    /**
     * classes represents the models to use for generating the ormlite_config.txt file
     */
    private static final Class<?>[] classes = new Class[] {Person.class};

    /**
     * Given that this is a separate program from the android app, we have to use
     * a static main java method to create the configuration file.
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {

        String currDirectory = "user.dir";

        // app directory is set as working directory in run config.
        String configPath = "\\src\\main\\res\\raw\\ormlite_config.txt";

        /**
         * Gets the project root directory
         */
        String projectRoot = System.getProperty(currDirectory);

        /**
         * Full configuration path includes the project root path, and the location
         * of the ormlite_config.txt file appended to it
         */
        String fullConfigPath = projectRoot + configPath;

        File configFile = new File(fullConfigPath);

        /**
         * In the a scenario where we run this program serveral times, it will recreate the
         * configuration file each time with the updated configurations.
         */
        if(configFile.exists()) {
            configFile.delete();
            configFile = new File(fullConfigPath);
        }

        /**
         * writeConfigFile is a util method used to write the necessary configurations
         * to the ormlite_config.txt file.
         */
        try {
            writeConfigFile(configFile, classes);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}