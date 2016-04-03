cls
@echo off 
setlocal enableDelayedExpansion 

set MYDIR=C:\Users\rafa\Documents\Artigos\Rede_da_presidente\DouDownloaded\pdf

dir /A:-D-S/S %MYDIR%
pause
echo comecou!


for /F %%x in ('dir /B/A:-D-S/S %MYDIR%') do (

 SET TEXT=%%x
 SET SUBSTRING=!TEXT:~-6!

 IF "!SUBSTRING!" == "-1.pdf" (
	echo processar : %%x
	java -jar C:\Users\rafa\Documents\Mestrado\CD\Software\DouDownloader\classes\artifacts\DouTextConverter_jar\DouDownloader.jar %%x
 )

)
echo terminou!