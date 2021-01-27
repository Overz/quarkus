# JWT - http://jwtenizr.sh/

Para gerar o token, acesse `src/main/resources/META-INF/jwt-generator`, e rode o comando `java -jar gerator-jwr.jar`.
Esse comando ira gerar configurações pré-definidas do JWT, com public e private key. Em seguida, copie as linhas do
arquivo `microprofile-config.properties`, para o arquivo `application.properties` do quarkus, pois será necessário para
validar o token.

Para modificar as permissões do JWT, altere a o arquivo `jwt-token.json`, na parte de 'groups', adicionando
qualquer role que desejar.

Para atualizar o token e as roles, rode novamente o gerador-jwt.jar, no mesmo diretório dos arquivos modificados(não
haverá override). Ele ira atualizar o Token(específicamente as roles), com base nas alterações feitas.

Note que toda vez que rodar o gerador, um comando de `curl` sera printado no console, ele ja é atualizado com as
modificações feitas, e servirá para teste.

---

# Quarkus

O quarkus auto valida os tokens passados, sem precisar realizar nenhuma configuração, apenas passando o token no header.
