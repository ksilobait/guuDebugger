# guuDebugger
программа: дебаггер для языка Guu  

из корневой директории:  
gradle build - запустить тесты  
gradle assemble - создать jar  
запуск созданного jar с примером из директории:  
java -jar ./build/libs/guuDebugger-1.0-SNAPSHOT.jar example  

команды отладчика:  
i – step into  
o – step over  
trace – print stack trace  
var – print all global vars  

операторы языка:  
set <varname> <int_value>  
call <sub_name>  
print <var_name>  
