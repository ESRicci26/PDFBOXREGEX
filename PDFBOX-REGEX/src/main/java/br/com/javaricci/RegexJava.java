package br.com.javaricci;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class RegexJava {

	public static void main(String args[]) throws IOException, InterruptedException, ParseException {

		// Carregando um documento existente no caminho do projeto
		File file = new File("NOTA_166185.pdf");
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdf1 = new PDFTextStripper();

		try {
			String pdfTexts = pdf1.getText(document);
			// salve o código no ArrayList courseCode
			ArrayList<String> courseCode = courseCode("([A-Z]{2,4}[0-9]{2,4})", pdfTexts);

			//REGEX-Expressão Regular de CPF
			//ArrayList<String> courseDetails = courseDetails("(\\d{3}).(\\d{3}).(\\d{3})-(\\d{2})", pdfTexts);

			//REGEX-Expressão Regular de CNPJ
			//ArrayList<String> courseDetails = courseDetails("(\\d{2}).(\\d{3}).(\\d{3})/(\\d{4})-(\\d{2})", pdfTexts);

			//REGEX-Expressão Regular de CEP (Exemplo: 04.937-190)
			//ArrayList<String> courseDetails = courseDetails("(\\d{2}).(\\d{3})-(\\d{3})", pdfTexts);

			//REGEX-Expressão Regular de valores monetários
			//ArrayList<String> courseDetails = courseDetails("[R][$](\\s{1})(\\d{3}),(\\d{2})", pdfTexts);
			
			//REGEX-Expressão Regular pegar CNAE da Nota Fiscal
			//ArrayList<String> courseDetails = courseDetails("[C][N][A][E][:](\\s{1})(\\d{5})", pdfTexts);

			//REGEX-Expressão Regular pegar Inscrição Municipal da Nota Fiscal
			//ArrayList<String> courseDetails = courseDetails("[M][u][n][i][c][i][p][a][l][:](\\s{1})(\\d{5})", pdfTexts);

			//REGEX-Expressão Regular pegar EMAIL na Nota Fiscal
			ArrayList<String> courseDetails = courseDetails("\\w+@\\w+\\.\\w{2,3}", pdfTexts);

			// Thread1
			int courseCodeRicci = courseCode("([A-Z]{2,4}[0-9]{2,4})", pdfTexts).size();
			System.out.println("Número de todos os cursos listados");
			System.out.println("==================================");
			System.out.println("Número total de cursos listados: " + courseCodeRicci);

			//Resultados dos CPF informados no arquivo PDF
			System.out.println("\nLista/Resultados de CPF");
			System.out.println("=======================");
			int socCourse = 0;
			for (int x = 0; x < courseDetails.size(); x++) {
					socCourse++;
					System.out.println("CNPJ/CPF: "+courseDetails.get(x));
			}


		} catch (FileNotFoundException ex) {
			System.out.println("Impossível abrir o arquivo '" + file + "'");
		} catch (IOException ex) {
			System.out.println("Erro ao ler o arquivo '" + file + "'");
		}
		document.close();

	}



	public static ArrayList<String> courseCode(String theRegex, String string2Check) {
		ArrayList<String> courseCode = new ArrayList<String>();

		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(string2Check);

		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				courseCode.add(regexMatcher.group().trim());
			}
		}
		return (courseCode);
	}



	public static ArrayList<String> courseDetails(String theRegex, String string2Check) {
		ArrayList<String> courseDetails = new ArrayList<String>();

		Pattern checkRegex = Pattern.compile(theRegex);
		Matcher regexMatcher = checkRegex.matcher(string2Check);

		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				courseDetails.add(regexMatcher.group().trim());
			}
		}
		return (courseDetails);
	}

}
