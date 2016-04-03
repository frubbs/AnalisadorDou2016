cls
@echo off 
setlocal enableDelayedExpansion 

set DIA=6
set MES=1
set ANO=2011

FOR /D %%p IN ("C:\Users\rafa\Documents\Artigos\Rede_da_presidente\DouDownloaded\pdf\*.*") DO rmdir "%%p" /s /q

:loop

	set DATA=%DIA%/%MES%/%ANO%
	echo data: !DATA!


    FOR /D %%p IN ("C:\Users\rafa\Documents\Artigos\Rede_da_presidente\DouDownloaded\temp\*.*") DO rmdir "%%p" /s /q


	java  -jar C:\Users\rafa\Documents\Mestrado\CD\Software\DouDownloader\classes\artifacts\DouDownloader_jar\DouDownloader.jar !DATA! !DATA! C:\Users\rafa\Documents\Artigos\Rede_da_presidente\DouDownloaded\
	
	if %DIA% LSS 31 (
		set /A DIA=%DIA%+1
		goto loop
	) else (
		set DIA=1
	)
	
	if %MES% LSS 12 (
		set /A MES=%MES%+1
		goto loop
	) else (
		set MES=1
	)
		
	if %ANO% GTR 2009 (
		set /A ANO=%ANO%-1
		goto loop
	) else (
		echo ACABEI"""
	)

	
