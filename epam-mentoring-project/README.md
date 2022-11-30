cpm.aem.mentoring project
=========================================

Prerequesties
-------------
This artifact requires an unobfuscated uber jar from Adobe to be installed and available in local (or any other configured) Maven repository <br>
Unobfuscated version of uber jar can be downloaded directly from Adobe website, DayCare access required, or requested form
[EPAM Content Competency](mailto:OrgCompetencyContentServiceDesk@epam.com)<br>

Uber jar should be installed into maven repo using following command (exmample for 6.2)

```bash
    mvn install:install-file -Dfile=cq-quickstart-6.2.0-apis.jar -DgroupId=com.adobe.aem -DartifactId=uber-jar -Dversion=6.2.0 -Dclassifier=apis -Dpackaging=jar
```

[More about uber jar](https://docs.adobe.com/docs/en/aem/6-1/develop/dev-tools/ht-projects-maven.html)


Modules
-------
* cpm.aem.mentoring - Uber package<br>
*epam-mentoring-project.all*<br>
Uber package for aggregated deployment off all content packages and bundles for cpm.aem.mentoring. Doesn't contain any code or content.
* cpm.aem.mentoring - Apps Package<br>
*epam-mentoring-project.apps*<br>
Module contains apps folder for cpm.aem.mentoring
* cpm.aem.mentoring - Apps AEM<br>
*epam-mentoring-project.apps-aem*<br>
Extensions for AEM authoring interface for cpm.aem.mentoring
* cpm.aem.mentoring - OSGi bundles parent<br>
*epam-mentoring-project.apps-aem*<br>
Parent project for all OSGi bundles for cpm.aem.mentoring, all bundles have to be created as sub-modules of this one<br>
    * cpm.aem.mentoring - Services bundle<br>
    *epam-mentoring-project.services*<br>
    OSGI bundle for cpm.aem.mentoring services
    * cpm.aem.mentoring - View bundle<br>
    *epam-mentoring-project.veiw*<br>
    OSGI bundle for cpm.aem.mentoring Sling models and WCMUse classes
    * cpm.aem.mentoring - Workflow bundle<br>
    *epam-mentoring-project.workflow*<br>
    OSGI bundle for cpm.aem.mentoring for workflow extensions
* cpm.aem.mentoring - Config package<br>
*epam-mentoring-project.config*<br>
Package with configuration for different run modes
* cpm.aem.mentoring - Content package<br>
*epam-mentoring-project.content*<br>
Package with content, dam, tags

How to build
------------
There are 3 build-in deployment options triggered by corresponding maven profiles

1. **deploy-package** <br>
Deploys current module as content-package, using default host/port configuration and credentials.
```xml
  <aem.host>localhost</aem.host>
  <aem.port>4502</aem.port>
  <aem.server>http://${aem.host}:${aem.port}</aem.server>
  <sling.user>admin</sling.user>
  <sling.password>admin</sling.password>
```
Configuration can be changed in root pom file or overridden using -D flags
```bash
    mvn clean install -P deploy-package -Dsling.host=my-sling-host -Dsling.port=4503
```
If you execute this command on a root project level each content packaged module will be deployed separartly (except for epam-mentoring-project.all, see pt.3)

2. **deploy-bundle** <br>
Deploys current module as OSGi bundle, using default host/port configuration and credentials. By default, all bundles are installed here - /apps/mentoring/install, so this folder must exist
```bash
    mvn clean install -P deploy-bundle -Dsling.host=my-sling-host -Dsling.user=user
```
If you execute this command on a root project or bundles project level each bundle packaged module will be deployed separately
Effectively, this command on the root level deploys entire application as separate packages and bundles
```bash
    mvn clean install -P deploy-package,deploy-bundle
```

3. **deploy-all** <br>
Deploys entire application as one uber content-package build from epam-mentoring-project.all module<br>
All bundles will be embedded into this package<br>
All content packages will be included as sub-packages<br>
```bash
    mvn clean install -P deploy-all
```

Testing
-------
Work in Progress



