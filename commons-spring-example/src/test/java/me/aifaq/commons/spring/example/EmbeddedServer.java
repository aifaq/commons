package me.aifaq.commons.spring.example;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

public class EmbeddedServer {
	public static void main(String[] args) throws Exception {
		final int port = 20080;

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setBaseDir("commons-spring-example/target/tomcat");

		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

		tomcat.setConnector(connector);
		connector.setPort(port);
		connector.setURIEncoding("UTF-8");

		tomcat.getService().addConnector(connector);

		Context ctx = tomcat.addWebapp("", new File("commons-spring-example/src/main/webapp").getAbsolutePath());

		// declare an alternate location for your "WEB-INF/classes" dir:
		File additionWebInfClasses = new File("commons-spring-example/target");
		
		StandardRoot standardRoot = new StandardRoot(ctx);

		standardRoot.createWebResourceSet(ResourceSetType.CLASSES_JAR, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "classes", "/");

		ctx.setResources(standardRoot);
		
		ctx.setLoader(new WebappLoader(EmbeddedServer.class.getClassLoader()));
		ctx.getLoader().setDelegate(true);

		tomcat.start();
		tomcat.getServer().await();
	}

}
