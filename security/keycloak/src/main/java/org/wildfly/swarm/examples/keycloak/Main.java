package org.wildfly.swarm.examples.keycloak;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.keycloak.Secured;

public class Main {


    public static void main(String... args) throws Exception {
        final Swarm swarm = new Swarm();

        // Create one or more deployments
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.as(Secured.class)
            .protect( "/secured" )
                .withMethod( "GET" )
                .withRole( "admin" );

        // Add resources to deployment
        deployment.addClass(SecuredResource.class);
        deployment.addClass(NonSecuredResource.class);
        deployment.addAllDependencies();

        swarm.start();
        swarm.deploy(deployment);

    }
}