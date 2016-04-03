package DouPagMinisterioSeparator;


import java.io.File;
import java.net.MalformedURLException;

import DouPDFPagesToTextDayConverter.DouPDFtoTextConverter;

public class testeGateConvert
{

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException
	{
		String filename = "Dou-22032016-1-1.pdf";
		File file = new File(filename);

		System.out.println(DouPDFtoTextConverter.convertFile(file));

	}

}
