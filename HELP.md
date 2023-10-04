# Getting Started



to generate test data use: 
mockaroo project

script to generate gift_certificate tag links:

<pre>
@echo off
setlocal EnableDelayedExpansion
set valval=VALUES

set /a count=6

:loop
if %count% gtr 10006 goto end
set /a repeats=!RANDOM! %% 11
for /l %%i in (1,1,!repeats!) do (
set /a value=!RANDOM! %% 1000 + 5
echo insert into cert_tag ^(cert_id,tag_id^) %valval% ^(%count%,!value!^);
)
set /a count+=1
goto loop

:end
</pre>
after use utility sort /unique to get rid of duplicated values
