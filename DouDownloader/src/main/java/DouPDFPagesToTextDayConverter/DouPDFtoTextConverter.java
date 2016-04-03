package DouPDFPagesToTextDayConverter;

import Util.Util;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DouPDFtoTextConverter
{

	public static String convertFile(File file) throws MalformedURLException
	{
		try
		{

			String fileContent = Util.getFileContent2(file);

			String adjustedFileContent = adjustLineBreaks(fileContent);


			return adjustedFileContent;
		} catch (Throwable e)
		{

			e.printStackTrace();
			System.out.println("Erro processando: " + file.getAbsolutePath());
			return "";
		}

	}

	private static String adjustLineBreaks(String fileContent)
	{

		return fileContent.replace("-\n", " ");
	}


}
