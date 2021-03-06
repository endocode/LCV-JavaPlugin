-- This query should be run from the postgres localhost (e.g. pgAdmin):

INSERT INTO package_version (id, metadata)
VALUES('2','{
   "plugin_name":"QMSTR",
   "plugin_version":"0.0.1",
   "input":{
      "product":"org.cyclonedx:cyclonedx-maven-plugin",
      "version":"2.3.1-SNAPSHOT"
   },
   "created_at":"2021-03-24T04:35:04.382348949Z",
   "payload":{
      "package_metadata":{
        "InboundLicenses":[
         "MIT","Apache-2.0"
      ]
      },
      "fileMetadata":[
         {
            "filename":"project/src/main/java/org/cyclonedx/maven/CycloneDxAggregateMojo.java",
            "SHA-1":"0c2679e170b793fb572226b94f83095c1a110ca7",
            "licenses":[
               "Apache 2.0"
            ]
         },
         {
            "filename":"project/src/main/java/org/cyclonedx/maven/CycloneDxMojo.java",
            "SHA-1":"c29f8f0419f533fd1cdf91da359bbb57fc7a79fa",
            "licenses":[
               "Apache 2.0"
            ]
         },
         {
            "filename":"project/src/main/java/org/cyclonedx/maven/BaseCycloneDxMojo.java",
            "SHA-1":"fd0c3310a981bf17eb12bc6adeed842a31c60675",
            "licenses":[
               "Apache 2.0"
            ]
         }
      ]
   }
}
');
