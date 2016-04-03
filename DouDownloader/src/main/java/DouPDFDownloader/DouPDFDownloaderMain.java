package DouPDFDownloader;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import DouPDFPagesToTextDayConverter.DouPagesToDayConverter;
import Util.Util;
import org.apache.commons.io.FileUtils;

public class DouPDFDownloaderMain
{

	private static final int MAX_PAG = 500;

	private static String URL = "http://pesquisa.in.gov.br/imprensa/servlet/INPDFViewer?jornal=@JOR@&pagina=@PAG@&data=@DATA@&captchafield=firistAccess";

	private static String FILENAME = "@PATH@\\temp\\@DATA@\\Dou-@DATA@-@JOR@-@PAG@.pdf";
	private static String DIRNAME = "@PATH@\\temp\\@DATA@";



	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{

		if (args.length != 3)
			throw new Exception("Argumentos invalidos.");

		String iniDate = args[0];
		String endDate = args[1];
		String path = args[2];

		String data = endDate.equals(iniDate) ? Util.getDate(endDate) : Util.getnextDate(endDate, iniDate);


		do
		{
			long startTime = System.currentTimeMillis();

			String url = URL.replace("@DATA@", data);
			String file = FILENAME.replace("@DATA@", data.replaceAll("/", "")).replace("@PATH@", path); // TODO colocar cada dia

			String dir = DIRNAME.replace("@DATA@", data.replaceAll("/", "")).replace("@PATH@", path);


			File theDir = new File(dir);
			if (!theDir.exists())
				if (!theDir.mkdir())
					return;																							// numa pasta

			for (int i = 1; i <= 3; i++)
			{

				String urlj = url.replace("@JOR@", String.valueOf(i));
				String filej = file.replace("@JOR@", String.valueOf(i));

				deleteFileIfExists(filej);


				//System.out.println("Download paginas:" + filej);
				for (int j = 1; j < MAX_PAG; j++)
				{

					System.out.print("+");
					String urljp = urlj.replace("@PAG@", String.valueOf(j));
					String filejp = filej.replace("@PAG@", String.valueOf(j));

					if (!PDFDownloader.DownloadPDF(urljp, filejp))
						break;

				}
				// cheguei ao final das paginas. bora pro proximo jornal
			//	System.out.println("cheguei ao final das paginas.");
			//	System.out.println("bora pro proximo jornal");
			}

			File pdf = new File(dir.replace("temp", "pdf"));
			try {
				FileUtils.copyDirectory(new File(dir), pdf);
			}
			catch (Throwable e) {
				e.printStackTrace();
			}

			for(int i=1; i<=3; i++) {
				String f = file.replace("@JOR@", String.valueOf(i)).replace("@PAG@","1").replace("temp", "pdf");
			//	System.out.println("convertendo para texto...:" + f);
				DouPagesToDayConverter.convertBasedateFiles(new File(f));
			}

			//System.out.println("bora pro proximo dia");



			data = Util.getnextDate(data, iniDate);


			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("");

			Path logpath = Paths.get("C:\\Users\\rafa\\Documents\\Artigos\\Rede_da_presidente\\DouDownloaded\\logProcessado.txt");
			Files.write(logpath, (elapsedTime+";" + file + ";\r\n").getBytes(), StandardOpenOption.APPEND);

			System.out.println("Processado["+elapsedTime+"] : (" + file +")");



		} while (data != "fim");
		//System.out.println("Fim do processamento!");
	}

	private static void deleteFileIfExists(String tempFile)
	{
		// Delete if tempFile exists
		File fileTemp = new File(tempFile);
		if (fileTemp.exists())
		{
			fileTemp.delete();
		}
		fileTemp = null;
	}
}
