@echo off

setlocal

:: REPLACE TJ WITH YOUR USERNAME
set DB_FILE="C:\Users\TJ\spring-boot-h2-db.mv.db"
set TRACE_FILE="C:\Users\TJ\spring-boot-h2-db.trace.db"
:: Check if the file exists
if exist %DB_FILE% (
    echo Deleting file: %DB_FILE%
    del %DB_FILE%
    echo Deleting file: %TRACE_FILE%
    del %TRACE_FILE%
) else (
    echo File not found: %DB_FILE%
)

endlocal