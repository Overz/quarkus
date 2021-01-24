# demo project

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre a Quarkus, visite seu site: <https://quarkus.io/>.

## Running the application in dev mode

Você pode executar seu aplicativo no modo dev que permite a codificação ao vivo usando:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

O aplicativo pode ser empacotado usando:

```shell script
./mvnw package
```

Ele produz o arquivo `demo-1.0.0-SNAPSHOT-runner.jar` no diretório `/target`.
Esteja ciente de que não é um [**_über-jar_**](https://stackoverflow.com/questions/11947037/what-is-an-uber-jar), pois as dependências são copiadas para o diretório `target / lib`.

Se você deseja construir um [**_über-jar_**](https://stackoverflow.com/questions/11947037/what-is-an-uber-jar), execute o seguinte comando:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

O aplicativo agora pode ser executado usando `java -jar target / demo-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

Você pode criar um executável nativo usando:

```shell script
./mvnw package -Pnative
```

Ou, se você não tiver o GraalVM instalado, pode executar a compilação do executável nativo em um contêiner usando:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./target/demo-1.0.0-SNAPSHOT-runner`

Se você quiser saber mais sobre como construir executáveis nativos, consulte <https://quarkus.io/guides/maven-tooling.html>.

# RESTEasy JAX-RS

<p>A Hello World RESTEasy resource</p>

Guia: <https://quarkus.io/guides/rest-json>
