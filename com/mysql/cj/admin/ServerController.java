
package com.mysql.cj.admin;

import com.mysql.cj.util.StringUtils;
import com.mysql.cj.Constants;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ServerController
{
    public static final String BASEDIR_KEY = "basedir";
    public static final String DATADIR_KEY = "datadir";
    public static final String DEFAULTS_FILE_KEY = "defaults-file";
    public static final String EXECUTABLE_NAME_KEY = "executable";
    public static final String EXECUTABLE_PATH_KEY = "executablePath";
    private Process serverProcess;
    private Properties serverProps;
    
    public ServerController(final String baseDir) {
        this.serverProcess = null;
        this.serverProps = null;
        this.setBaseDir(baseDir);
    }
    
    public ServerController(final String basedir, final String datadir) {
        this.serverProcess = null;
        this.serverProps = null;
    }
    
    public void setBaseDir(final String baseDir) {
        this.getServerProps().setProperty("basedir", baseDir);
    }
    
    public void setDataDir(final String dataDir) {
        this.getServerProps().setProperty("datadir", dataDir);
    }
    
    public Process start() throws IOException {
        if (this.serverProcess != null) {
            throw new IllegalArgumentException("Server already started");
        }
        return this.serverProcess = Runtime.getRuntime().exec(this.getCommandLine());
    }
    
    public void stop(final boolean forceIfNecessary) throws IOException {
        if (this.serverProcess != null) {
            final String basedir = this.getServerProps().getProperty("basedir");
            final StringBuilder pathBuf = new StringBuilder(basedir);
            if (!basedir.endsWith(File.separator)) {
                pathBuf.append(File.separator);
            }
            pathBuf.append("bin");
            pathBuf.append(File.separator);
            pathBuf.append("mysqladmin shutdown");
            System.out.println(pathBuf.toString());
            final Process mysqladmin = Runtime.getRuntime().exec(pathBuf.toString());
            int exitStatus = -1;
            try {
                exitStatus = mysqladmin.waitFor();
            }
            catch (InterruptedException ex) {}
            if (exitStatus != 0 && forceIfNecessary) {
                this.forceStop();
            }
        }
    }
    
    public void forceStop() {
        if (this.serverProcess != null) {
            this.serverProcess.destroy();
            this.serverProcess = null;
        }
    }
    
    public synchronized Properties getServerProps() {
        if (this.serverProps == null) {
            this.serverProps = new Properties();
        }
        return this.serverProps;
    }
    
    private String getCommandLine() {
        final StringBuilder commandLine = new StringBuilder(this.getFullExecutablePath());
        commandLine.append(this.buildOptionalCommandLine());
        return commandLine.toString();
    }
    
    private String getFullExecutablePath() {
        final StringBuilder pathBuf = new StringBuilder();
        final String optionalExecutablePath = this.getServerProps().getProperty("executablePath");
        if (optionalExecutablePath == null) {
            final String basedir = this.getServerProps().getProperty("basedir");
            pathBuf.append(basedir);
            if (!basedir.endsWith(File.separator)) {
                pathBuf.append(File.separatorChar);
            }
            if (this.runningOnWindows()) {
                pathBuf.append("bin");
            }
            else {
                pathBuf.append("libexec");
            }
            pathBuf.append(File.separatorChar);
        }
        else {
            pathBuf.append(optionalExecutablePath);
            if (!optionalExecutablePath.endsWith(File.separator)) {
                pathBuf.append(File.separatorChar);
            }
        }
        final String executableName = this.getServerProps().getProperty("executable", "mysqld");
        pathBuf.append(executableName);
        return pathBuf.toString();
    }
    
    private String buildOptionalCommandLine() {
        final StringBuilder commandLineBuf = new StringBuilder();
        if (this.serverProps != null) {
            for (final String key : this.serverProps.keySet()) {
                final String value = this.serverProps.getProperty(key);
                if (!this.isNonCommandLineArgument(key)) {
                    if (value != null && value.length() > 0) {
                        commandLineBuf.append(" \"");
                        commandLineBuf.append("--");
                        commandLineBuf.append(key);
                        commandLineBuf.append("=");
                        commandLineBuf.append(value);
                        commandLineBuf.append("\"");
                    }
                    else {
                        commandLineBuf.append(" --");
                        commandLineBuf.append(key);
                    }
                }
            }
        }
        return commandLineBuf.toString();
    }
    
    private boolean isNonCommandLineArgument(final String propName) {
        return propName.equals("executable") || propName.equals("executablePath");
    }
    
    private boolean runningOnWindows() {
        return StringUtils.indexOfIgnoreCase(Constants.OS_NAME, "WINDOWS") != -1;
    }
}
