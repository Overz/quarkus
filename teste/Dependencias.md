# Dependencys

- jdbc-postgres
- hibernate-orm
- resteasy-jsonb

Adicionando: `./mvnw quarkus:add-extension -Dextension="hibernate-orm, jdbc-postgres, resteasy-jsonb"`

Para mais dependencias: 
- `./mvnw quarkus:list-extensions`
- <https://code.quarkus.io/>

Docs: <https://access.redhat.com/documentation/en-us/red_hat_build_of_quarkus/1.3/html/developing_and_compiling_your_quarkus_applications_with_apache_maven/proc-installing-and-managing-java-extensions-with-quarkus-applications_quarkus-maven> w