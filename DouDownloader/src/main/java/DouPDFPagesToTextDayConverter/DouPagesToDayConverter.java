package DouPDFPagesToTextDayConverter;



import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.io.FileUtils;

public class DouPagesToDayConverter
{

	public static void main(String[] args) throws Exception
	{

		File input = new File(args[0]);

		convertBasedateFiles(input);
	}

	/**
	 *
	 * @throws IOException
	 */
	public static void convertBasedateFiles(final File firstPage) throws IOException
	{
		String path = firstPage.getParent();

		File dir = new File(path);


		File[] currentfiles = dir.listFiles(new FilenameFilter()
		{
			// arquivos da mesma data e jornal
			public boolean accept(File dir, String name)
			{
				try {
					boolean result = name.endsWith(".pdf")
							&& name.substring(0, Util.Util.FILENAME_DATE_JOR_SIZE).equals(
							firstPage.getName().substring(0, Util.Util.FILENAME_DATE_JOR_SIZE));
//					System.out.println("name:" + name + "   firstpage:" + firstPage.getName() + " b:"+ result);

					return result;
				} catch (Throwable e){
					e.printStackTrace();
					System.out.println("name:" + name + "   firstpage:" + firstPage);
					return false;
				}


			}
		});

		if (currentfiles.length > 0)
		{
			try
			{
				// ordena os arquivos de acordo com a pagina
				Arrays.sort(currentfiles, new Comparator<File>()
				{
					public int compare(File o1, File o2)
					{
						int ext1 = o1.getName().indexOf(".pdf");
						int dash1 = o1.getName().lastIndexOf("-");
						int ext2 = o2.getName().indexOf(".pdf");
						int dash2 = o2.getName().lastIndexOf("-");

						String p1 = o1.getName().substring(dash1 + 1, ext1);
						String p2 = o2.getName().substring(dash2 + 1, ext2);

						return new Integer(Integer.parseInt(p1)).compareTo(new Integer(Integer.parseInt(p2)));
					}
				});
			} catch (Exception e)
			{
				System.out
						.println("*ERRO ****** Exce��o ao ordenar lista de arquivos. Processamento continuar� normalmente");
				e.printStackTrace();
			}
			// joga o conteudo de todos os pdf em um unico txt
			StringBuilder sbFinalContent = new StringBuilder();
			for (File currentFileChild : currentfiles)
			{
				System.out.print("*");
				sbFinalContent.append(DouPDFtoTextConverter.convertFile(currentFileChild));
			}

			String novoNome = dir.getAbsolutePath().replace("pdf", "txt") + "\\"
					+ firstPage.getName().substring(0, Util.Util.FILENAME_DATE_JOR_SIZE) + ".txt";

			File novo = new File(novoNome);

			//System.out.println("gravar: " + novo.getAbsolutePath());

			FileUtils.writeStringToFile(novo, sbFinalContent.toString());
		}
	}
}