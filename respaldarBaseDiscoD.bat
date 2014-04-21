@echo off

echo Este batch hara un respaldo de la base de datos casos
pause
set RutaPostgres="C:\Program Files\PostgreSQL\9.3\bin"

: Sets the proper date and time stamp with 24Hr Time for log file naming
: convention
set anio=%date:~-4,4%
set mes=%date:~-7,2%
set dia=%date:~-10,2%
set Milliseconds= %time:~-2,2%
set Seconds= %time:~-5,2%
set Minutes= %time:~-8,2%
set Hours= %time:~-11,2%

set final_archivo=%anio%%mes%%dia%%Hours%%Minutes%%Seconds%
ECHO %final_archivo%
c:
cd "D:\nominaUlvr"
dir *.dump

set /p nombre_archivo=Ingrese el nombre del respaldo: 
c:
cd "c:\Program Files\PostgreSQL\9.3\bin"
pause
pg_dump -U postgres -C -f D:\nominaUlvr\%nombre_archivo%.dump reporte_nomina
pause
echo Espero se haya creado el respaldo de la base de datos
pause
exit

