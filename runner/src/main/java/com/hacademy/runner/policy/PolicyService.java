package com.hacademy.runner.policy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacademy.runner.RunnerApplication;
import com.hacademy.runner.service.ProcessRunner;

/**
  				- All permission example
  				grant {
				  permission java.util.PropertyPermission "*", "read, write";
				  permission java.lang.RuntimePermission "getProtectionDomain";
				  permission java.io.FilePermission "<<ALL FILES>>", "read, write, delete, execute";
				  permission java.io.SerializablePermission "enableSubstitution";
				  permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
				  permission java.lang.RuntimePermission "accessClassInPackage.org.apache.*";
				  permission java.lang.RuntimePermission "accessClassInPackage.sun.*";
				  permission java.lang.RuntimePermission "accessDeclaredMembers";
				  permission java.lang.RuntimePermission "createClassLoader";
				  permission java.lang.RuntimePermission "getClassLoader";
				  permission java.lang.RuntimePermission "loadLibrary.libtcnative-1";
				  permission java.lang.RuntimePermission "loadLibrary.management";
				  permission java.lang.RuntimePermission "loadLibrary.net";
				  permission java.lang.RuntimePermission "loadLibrary.tcnative-1";
				  permission java.lang.RuntimePermission "modifyThread";
				  permission java.lang.RuntimePermission "modifyThreadGroup";
				  permission java.lang.RuntimePermission "org.jboss.security.SecurityAssociation.setServer";
				  permission java.lang.RuntimePermission "setContextClassLoader";
				  permission java.lang.RuntimePermission "createSecurityManager";
				  permission java.lang.RuntimePermission "setFactory";
				  permission java.lang.RuntimePermission "setIO";
				  permission java.lang.RuntimePermission "shutdownHooks";
				  permission java.net.NetPermission "specifyStreamHandler";
				  permission java.net.SocketPermission "*", "listen, resolve, connect, accept";
				  permission java.security.SecurityPermission "createAccessControlContext";
				  permission java.security.SecurityPermission "getDomainCombiner";
				  permission java.security.SecurityPermission "getPolicy";
				  permission java.security.SecurityPermission "getProperty.*";
				  permission java.security.SecurityPermission "insertProvider.SUN";
				  permission java.security.SecurityPermission "putProviderProperty.SUN";
				  permission java.security.SecurityPermission "setPolicy";
				  permission java.security.SecurityPermission "setProperty.package.access";
				  permission java.security.SecurityPermission "setProperty.package.definition";
				  permission net.jini.security.GrantPermission "java.security.AllPermission";
				  permission com.sun.jini.discovery.internal.EndpointInternalsPermission "set";
				  permission com.sun.jini.discovery.internal.EndpointInternalsPermission "get";
				  permission java.util.logging.LoggingPermission "control";
				  permission net.jini.discovery.DiscoveryPermission "*";
				  permission javax.security.auth.AuthPermission "refreshLoginConfiguration";
				  permission javax.security.auth.AuthPermission "setLoginConfiguration";
				  permission javax.management.MBeanServerPermission "createMBeanServer";
				  permission javax.management.MBeanServerPermission "findMBeanServer";
				  permission javax.management.MBeanServerPermission "newMBeanServer";
				  permission javax.management.MBeanTrustPermission "register";
				  permission javax.management.MBeanPermission "*", "*";
				  permission java.lang.RuntimePermission "org.jboss.security.SecurityAssociation.setRunAsRole";
				  permission javax.security.auth.AuthPermission "doAsPrivileged";
				  permission java.lang.RuntimePermission "org.jboss.security.SecurityAssociation.getPrincipalInfo";
				  permission javax.security.auth.AuthPermission "createLoginContext.HsqlDbRealm";
				  permission javax.security.auth.AuthPermission "getLoginConfiguration";
				  permission java.lang.RuntimePermission "org.jboss.security.SecurityAssociation.accessContextInfo";
				  permission javax.security.auth.PrivateCredentialPermission "javax.resource.spi.security.PasswordCredential * \"*\"", "read";
				  permission javax.security.auth.AuthPermission "*";
				  permission java.lang.RuntimePermission "org.jboss.security.SecurityAssociation.setPrincipalInfo";
				  permission net.jini.security.GrantPermission "java.security.AllPermission \"<all permissions>\", \"<all actions>\"";
				  permission net.jini.export.ExportPermission "exportRemoteInterface.com.sun.jini.reggie.Registrar";
				  permission com.sun.jini.thread.ThreadPoolPermission "getSystemThreadPool";
				  permission javax.management.MBeanServerPermission "releaseMBeanServer";
				  permission java.lang.RuntimePermission "exitVM";
				};
 * @author hwang
 *
 */
@Service
public class PolicyService {
	
	@Autowired
	private ProcessRunner runner;
	
	private File policy;
	
	@PostConstruct
	public void initialize() {
		byte[] buffer = new byte[1024];
		policy = new File(runner.getBaseDirectory(), "runner.policy");
		try(
			OutputStream out = new FileOutputStream(policy);
			InputStream in = PolicyService.class.getResourceAsStream("runner.policy");
		) {
			while(true) { 
				int size = in.read(buffer);
				if(size == -1) break;
				out.write(buffer, 0, size);
			}
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
}
