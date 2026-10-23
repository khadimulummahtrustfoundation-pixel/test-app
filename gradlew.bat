@Erl off
if "%DEBUG%" == "" @echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

if [%JAVA_HOME%] == [] goto findJavaFromPath

if exist "%JAVA_HOME%\bin\java.exe" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo Please set the the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto fail

:findJavaFromPath
set JAVACMD=java.exe
%JAVACMD% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo Please set the the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto fail

:execute
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
"%JAVACMD%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %%*

:end
@endlocal
exit /b %ERRORLEVEL%

:fail
exit /b 1
