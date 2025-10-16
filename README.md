# ToDoApp

🎥 **Vídeo de Demonstração:** https://youtu.be/qqxy_EOfRPo

## Pipeline de CI/CD com GitHub Actions

Este projeto possui uma pipeline automatizada configurada com **GitHub Actions** para construir e publicar o ficheiro `.jar` da aplicação. A pipeline permite que o projeto seja compilado e que o artefacto gerado fique disponível para download, permitindo executar o programa fora do IDE sem complicações.

### Funcionalidades da pipeline

- Disparada automaticamente sempre que há um **push na branch principal (`main`)**.
- Configura o **Java JDK 21** no ambiente de execução.
- Executa o comando `mvn clean package` para gerar o ficheiro `.jar`.
- Copia o ficheiro `.jar` para a raiz do repositório (opcional).
- Publica o `.jar` como **artefacto do workflow**, permitindo download direto pelo GitHub Actions.

### Excerto do ficheiro `build.yml`

```yaml
name: Maven Build and Publish Jar

on:
  push:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 21

      - name: Build with Maven
        run: mvn -B clean package

      - name: Copy JAR to repository root
        run: cp target/*.jar .

      - name: Upload JAR artifact
        uses: actions/upload-artifact@v4
        with:
          name: app-jar
          path: target/*.jar
