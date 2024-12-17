# **Documentação do Projeto: PDF Regex Extrator**

## **1. Visão Geral**
O **PDF Regex Extrator** é um aplicativo Java GUI (Interface Gráfica com o Usuário) desenvolvido com **Swing** que permite:
- Selecionar arquivos PDF.
- Extrair texto total ou informações específicas do PDF utilizando expressões regulares (Regex).
- Exibir os resultados extraídos em uma área de texto.

O projeto usa a biblioteca **Apache PDFBox** para manipulação de arquivos PDF.

---

## **2. Estrutura do Código**
### **2.1. Pacote**
O código está organizado dentro do pacote:
```java
package br.com.javaricci;
```

---

### **2.2. Importações**
As bibliotecas e classes utilizadas incluem:
- **Java Swing**: Interface gráfica.
- **Apache PDFBox**: Leitura e extração de texto de arquivos PDF.
- **Regex (java.util.regex)**: Análise de padrões no texto.
- **JFileChooser**: Seleção de arquivos.

---

### **2.3. Classe Principal**
A classe principal é **`RegexJavaGUI`**, que estende **`JFrame`** para criar a interface gráfica.

---

## **3. Interface Gráfica**
### **Componentes da Interface**
- **JLabel**: Rótulo para o campo Regex.
- **JTextField**: Campo para inserir a expressão regular.
- **JButton**: Três botões:
   1. **Selecione PDF**: Para selecionar o arquivo PDF.
   2. **Extrair**: Para extrair texto conforme a expressão regular.
   3. **Texto Todo PDF**: Para exibir todo o texto do PDF.
- **JTextArea**: Área de texto para exibir os resultados extraídos.
- **JScrollPane**: Permite rolagem na área de texto.

### **Layout**
O layout é dividido em:
- **Top Panel**: Contém os componentes de entrada (JLabel, JTextField e botões).
- **Center Panel**: Contém a área de resultados com rolagem.

---

## **4. Funcionamento**

### **4.1. Seleção de Arquivo**
O método `selectFile()` exibe um **JFileChooser** configurado para aceitar apenas arquivos PDF:
```java
JFileChooser fileChooser = new JFileChooser();
fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
```
- O arquivo selecionado é armazenado na variável **`selectedFile`**.

---

### **4.2. Extração com Regex**
O método `extractText()` realiza as seguintes operações:
1. Verifica se um arquivo foi selecionado e se a Regex foi informada.
2. Carrega o arquivo PDF com a **Apache PDFBox**:
   ```java
   PDDocument document = PDDocument.load(selectedFile);
   PDFTextStripper pdfStripper = new PDFTextStripper();
   String pdfText = pdfStripper.getText(document);
   document.close();
   ```
3. Utiliza **Regex** para encontrar correspondências no texto extraído:
   ```java
   Pattern pattern = Pattern.compile(regex);
   Matcher matcher = pattern.matcher(text);
   while (matcher.find()) {
       results.add(matcher.group().trim());
   }
   ```
4. Exibe os resultados na **JTextArea**.

---

### **4.3. Extração Completa do PDF**
O método `textoPDFTotal()`:
1. Carrega o PDF usando **PDFBox**.
2. Obtém todo o texto do PDF:
   ```java
   String pdfText = pdfStripper.getText(document);
   ```
3. Exibe o texto integral na **JTextArea**.

---

## **5. Métodos Importantes**
| Método              | Descrição                                                              |
|----------------------|------------------------------------------------------------------------|
| **selectFile()**     | Abre o JFileChooser para selecionar um arquivo PDF.                   |
| **extractText()**    | Extrai texto do PDF conforme a Regex informada e exibe os resultados. |
| **textoPDFTotal()**  | Extrai todo o texto do PDF e o exibe na área de texto.                |
| **extractData()**    | Utiliza Regex para encontrar correspondências no texto extraído.      |

---

## **6. Execução**
A execução da aplicação é iniciada no método `main`:
```java
SwingUtilities.invokeLater(() -> {
    RegexJavaGUI gui = new RegexJavaGUI();
    gui.setVisible(true);
});
```
O **SwingUtilities** garante que a GUI seja criada na **Event Dispatch Thread**.

---

## **7. Exemplo de Uso**
1. **Executar o programa**.
2. **Selecionar um arquivo PDF** clicando no botão **"Selecione PDF"**.
3. **Inserir a expressão regular** no campo **Regex**.
   - Exemplo de Regex para capturar datas: `\\d{2}/\\d{2}/\\d{4}`
4. Clicar no botão **"Extrair"** para capturar os padrões correspondentes.
5. Clicar no botão **"Texto Todo PDF"** para visualizar todo o texto do PDF.

---

## **8. Requisitos**
### **Dependências**
- **Apache PDFBox**: Inclua as bibliotecas necessárias no classpath.
   - Maven:
     ```xml
     <dependency>
         <groupId>org.apache.pdfbox</groupId>
         <artifactId>pdfbox</artifactId>
         <version>2.0.27</version>
     </dependency>
     ```

### **Ambiente**
- Java 8 ou superior.
- IDE compatível (Eclipse, IntelliJ, NetBeans).

---

## **9. Observações**
- **Regex**: A expressão deve ser inserida **sem aspas** e com uma **barra** para símbolos especiais.
- O arquivo PDF deve ser legível como texto (não funciona com PDFs contendo apenas imagens).

---

## **10. Melhorias Futuras**
- Adicionar suporte para PDFs baseados em imagem com **OCR**.
- Implementar exportação dos resultados extraídos para arquivos **TXT** ou **CSV**.
- Melhorar a interface gráfica utilizando bibliotecas como **JavaFX**.

---

## **11. Conclusão**
O **PDF Regex Extrator** é uma ferramenta prática e funcional para extrair dados de arquivos PDF usando expressões regulares. A interface é simples e intuitiva, facilitando seu uso para usuários com ou sem conhecimento técnico avançado.
