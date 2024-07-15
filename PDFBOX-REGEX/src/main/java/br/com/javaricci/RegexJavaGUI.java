package br.com.javaricci;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexJavaGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField regexField;
    private JTextArea resultArea;
    private File selectedFile;

    public RegexJavaGUI() {
        setTitle("PDF Regex Extrator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Componentes
        JLabel regexLabel = new JLabel("Regex:");
        regexField = new JTextField(20);
        regexField.setToolTipText("Informar SEM Aspas e com UMA barra em todo Regex");
        JButton selectFileButton = new JButton("Selecione PDF");
        JButton extractButton = new JButton("Extrair");
        JButton extractButton1 = new JButton("Texto Todo PDF");
        resultArea = new JTextArea();
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Layout
        JPanel topPanel = new JPanel();
        topPanel.add(regexLabel);
        topPanel.add(regexField);
        topPanel.add(selectFileButton);
        topPanel.add(extractButton);
        topPanel.add(extractButton1);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Ações
        selectFileButton.addActionListener(e -> selectFile());
        extractButton.addActionListener(e -> extractText());
        extractButton1.addActionListener(e -> textoPDFTotal());
    }



    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
    }

    /*
    Informar no Campo JTEXTFIELD SEM Aspas e com UMA barra em todo Regex - Exemplos Abaixo:
    //REGEX-Expressão Regular de CPF
    (\d{3}).(\d{3}).(\d{3})-(\d{2})

    //REGEX-Expressão Regular de CNPJ
    (\d{2}).(\d{3}).(\d{3})/(\d{4})-(\d{2})

    //REGEX-Expressão Regular de CEP (Exemplo: 04.937-190)
    (\d{2}).(\d{3})-(\d{3})

    //REGEX-Expressão Regular de valores monetários
    [R][$](\s{1})(\d{3}),(\d{2})
    			
    //REGEX-Expressão Regular pegar CNAE da Nota Fiscal
    [C][N][A][E][:](\s{1})(\d{5})

    //REGEX-Expressão Regular pegar Inscrição Municipal da Nota Fiscal
    [M][u][n][i][c][i][p][a][l][:](\s{1})(\d{5})

    //REGEX-Expressão Regular pegar EMAIL na Nota Fiscal
    \w+@\w+\.\w{2,3}

    */
    private void extractText() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Selecione um arquivo PDF primeiro.");
            return;
        }

        String regex = regexField.getText();
        if (regex.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor insira uma REGEX.");
            return;
        }

        try {
        	
            PDDocument document = PDDocument.load(selectedFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);
            document.close();

            // Depuração: Imprima o texto extraído
            //System.out.println("REGEX Extraído JTEXTFIELD: " + regex);
            //System.out.println("Extracted Text: " + pdfText);

            ArrayList<String> extractedData = extractData(regex, pdfText);

            // Depuração: Imprima os dados extraídos
            //System.out.println("Extracted Data: " + extractedData);

            // Atualizar resultArea com dados extraídos
            resultArea.setText(""); // Limpar resultados anteriores
            for (String result : extractedData) {
                resultArea.append(result + "\n");
            }

            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo PDF: " + e.getMessage());
        }
    }

    
    
    private void textoPDFTotal() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Selecione um arquivo PDF primeiro.");
            return;
        }

        try {
        	
            PDDocument document = PDDocument.load(selectedFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);
            document.close();

            // Depuração: Imprima o texto extraído
            //System.out.println("Extracted Text: " + pdfText);

            // Depuração: Imprima os dados extraídos
            //System.out.println("Extracted Data: " + extractedData);

            // Atualizar resultArea com dados extraídos
            resultArea.setText(""); // Limpar resultados anteriores
            resultArea.setText(pdfText + "\n");

            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo PDF: " + e.getMessage());
        }
    }



    private ArrayList<String> extractData(String regex, String text) {
        ArrayList<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if (matcher.group().length() != 0) {
                results.add(matcher.group().trim());
            }
        }
        return results;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegexJavaGUI gui = new RegexJavaGUI();
            gui.setVisible(true);
        });
    }
}
