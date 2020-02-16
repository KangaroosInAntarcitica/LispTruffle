JAVAHOME = ../openjdk1.8.0_232-jvmci-19.3-b06
MX = ../mx/mx

build:
	env JAVA_HOME=$(JAVAHOME) $(MX) --dy /compiler  --jdk jvmci build

repl:
	env JAVA_HOME=$(JAVAHOME) $(MX) --dy /compiler  --jdk jvmci lispInterpreter

script:
	env JAVA_HOME=$(JAVAHOME) $(MX) --dy /compiler  --jdk jvmci lispScript

clean:
	env JAVA_HOME=$(JAVAHOME) $(MX) --dy /compiler  --jdk jvmci clean
