package com.violin.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author guo.lin
 */
@Mojo(name = "codeCount")
public class Car extends AbstractMojo {
  @Parameter(property = "config.url", defaultValue = "someValue")
  private String directory;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    
  }

}
